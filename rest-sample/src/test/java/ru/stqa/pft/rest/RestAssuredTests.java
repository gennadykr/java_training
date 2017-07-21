package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestAssuredTests {

    @BeforeClass
    public void init() {
        RestAssured.authentication = RestAssured.basic("LSGjeU4yP1X493ud1hNniA==", "");
    }

    @Test(enabled = false)
    public void testCreateIssue() throws IOException {
        Set<Issue> oldIssues = getIssues();
        Issue newIssue = new Issue().withSubject("Test issue").withDescription("New issue");
        int issueId = createIssue(newIssue);
        Set<Issue> newIssues = getIssues();
        System.out.println(newIssues);
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssues);
    }

    @Test
    public void testValidateIssue(){
        skipIfNotFixed(10);
        System.out.println("testing...");
    }

    public void skipIfNotFixed(int issueId) {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    private boolean isIssueOpen(int issueId) {
        String issueState = getIssueState(issueId);
        System.out.println(issueState);
        return issueState.equals("Open") || issueState.equals("In Progress");
    }

    private String getIssueState(int issueId) {
        String json = RestAssured.get(String.format("http://demo.bugify.com/api/issues/%s.json",issueId)).asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        Set<Issue> myIssues = new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
        return myIssues.stream().findFirst().get().getState_name();
    }

    private Set<Issue> getIssues() throws IOException {
        String json = RestAssured.get("http://demo.bugify.com/api/issues.json").asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());


    }

    private int createIssue(Issue newIssue) throws IOException {
        String json = RestAssured.given()
                .param("subject", newIssue.getSubject())
                .param("description", newIssue.getDescription())
                .post("http://demo.bugify.com/api/issues.json")
                .asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }
}

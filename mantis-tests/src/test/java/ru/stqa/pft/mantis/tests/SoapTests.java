package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class SoapTests extends TestBase {

    @Test(enabled = false)
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().gitProjects();
        System.out.println(projects.size());
        for (Project project : projects) {
            System.out.println(project);
        }
    }

    @Test(enabled = false)
    public void testCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
        Set<Project> projects = app.soap().gitProjects();
        Issue issue = new Issue().withSummary("Test issue")
                .withDescription("Test issue description")
                .withProject(projects.iterator().next());
        Issue created = app.soap().addIssue(issue);
        System.out.println(created);
        assertEquals(issue.getSummary(), created.getSummary());
    }

    @Test
    public void testValidateIssue() throws RemoteException, ServiceException, MalformedURLException {
        skipIfNotFixed(4);
        System.out.println("testing...");
    }
}

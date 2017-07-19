package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

    @BeforeMethod
    public void startMailServer(){
        app.mail().start();
    }

    @Test
    public void  testRegistration() throws IOException {
        long now = System.currentTimeMillis();
        String user = "user" + now;
        String password = "password";
        String email = user + "@example.com";
        app.registration().start(user, email);
        List<MailMessage> mailMessages = app.mail().waitForMail(2, 1000);
        String confermationLink = findConfermationLink(mailMessages, email);
        app.registration().finish(confermationLink, password);
        assertTrue(app.newSession().login(user,password));
    }

    private String findConfermationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        System.out.println(regex);
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
        app.mail().stop();
    }
}

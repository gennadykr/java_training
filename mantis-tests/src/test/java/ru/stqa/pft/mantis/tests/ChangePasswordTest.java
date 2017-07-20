package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTest extends TestBase {

    @Test
    public void  testChangePassword() throws MessagingException, IOException {
        // Expect that there is at least one user in DB
        Users users = app.db().users();
        System.out.println(users);
        assertTrue(users.size()>0);

        // Given user, one non-admin with proper domain
        VerbalExpression regex = VerbalExpression.regex().find("user").nonSpace().oneOrMore().find("127.0.0.1").build();
        System.out.println(regex);
        UserData user = users.stream().filter((u) -> regex.getText(u.getEmail()).equals(u.getEmail())).findAny().get();
        System.out.println(user);
        System.out.println(regex.getText(user.getEmail()));

        // Expect that user is registerd in james, erase all mail
        String emailPassword = "password";
        assertTrue(app.james().doesUserExist(user.getName()));
        app.james().drainEmail(user.getName(),emailPassword);

        // Initiate password reset
        app.users().loginAdmin();
        app.users().initiatePasswordReset(user);

        // Check mails
        List<MailMessage> mailMessages = app.james().waitForMail(user.getName(), emailPassword, 60000);
        String confermationLink = findConfermationLink(mailMessages, user.getEmail());

        // Complete password reset
        String newPassword = "newpass";
        app.users().completePasswordReset(confermationLink, newPassword);

        // Check new password set
        HttpSession session = app.newSession();
        assertTrue(session.login(user.getName(), newPassword));
        assertTrue(session.isLoggedInAs(user.getName()));
    }
}

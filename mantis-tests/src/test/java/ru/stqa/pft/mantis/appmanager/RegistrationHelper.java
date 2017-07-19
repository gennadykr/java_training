package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase {

    public RegistrationHelper(ApplicationManager app) {
        super(app);
    }

    public void start(String username, String email) {
        wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
        type(By.cssSelector("#username"), username);
        type(By.cssSelector("#email-field"), email);
        click(By.cssSelector("[value='Signup']"));
    }

    public void finish(String confermationLink, String password) {
        wd.get(confermationLink);
        type(By.name("realname"),"Real Name");
        type(By.name("password"),password);
        type(By.name("password_confirm"),password);
        click(By.cssSelector(".submit-button button"));
    }
}

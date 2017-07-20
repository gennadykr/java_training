package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.model.UserData;

public class UsersHelper extends HelperBase {

    public UsersHelper(ApplicationManager app) {
        super(app);
    }

    // ToDo: move to session helper
    public void loginAdmin() {
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.name("username"),app.getProperty("web.adminLogin"));
        click(By.cssSelector("input[value='Login']"));
        type(By.name("password"),app.getProperty("web.adminPassword"));
        click(By.cssSelector("input[value='Login']"));
    }

    public void initiatePasswordReset(UserData user) {
        wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
        wd.findElement(By.linkText(user.getName())).click();
        click(By.cssSelector("input[value='Reset Password']"));
    }

    public void completePasswordReset(String confermationLink, String newPassword) {
        wd.get(confermationLink);
        type(By.name("password"),newPassword);
        type(By.name("password_confirm"),newPassword);
        click(By.cssSelector(".submit-button button"));
    }
}

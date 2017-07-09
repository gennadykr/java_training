package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void groupPage() {
        if (isElementPresent(By.tagName("h1"))
                && wd.findElement(By.tagName("h1")).getTagName().equals("Groups")
                && isElementPresent(By.tagName("new"))) {
            System.out.println("Already on the Groups page!");
            return;
        }
        System.out.println("Navigate to Groups page");
        click(By.linkText("groups"));
    }

    public void contactPage() {
        if (isElementPresent(By.id("maintable"))) {
            System.out.println("Already on the Contacts page!");
            return;
        }
        System.out.println("Navigate to Contact Page");
        click(By.id("logo"));
    }
}

package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void gotoGroupPage() {
        if (isElementPresent(By.tagName("h1"))
                && wd.findElement(By.tagName("h1")).getTagName().equals("Groups")
                && isElementPresent(By.tagName("new"))) {
            return;
        }
        click(By.linkText("groups"));
    }

    public void gotoContactPage() {
        if (isElementPresent(By.id("maintable"))) {
            return;
        }
        click(By.id("logo"));
    }
}

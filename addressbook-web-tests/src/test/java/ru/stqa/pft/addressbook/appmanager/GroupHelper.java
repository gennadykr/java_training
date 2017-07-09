package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

public class GroupHelper extends HelperBase {

    public GroupHelper(WebDriver wd) {
        super(wd);
    }

    //ToDo use from Navigateion! wd -> this?
    public void returnToGroupPage() {
        System.out.println("Return to the Groups page");
        click(By.linkText("group page"));
    }

    public void submitGroupCreation() {
        click(By.name("submit"));
    }

    public void fillGroupForm(GroupData groupData) {
        type(By.name("group_name"), groupData.getName());
        type(By.name("group_header"), groupData.getHeader());
        type(By.name("group_footer"), groupData.getFooter());
    }

    public void initGroupCreation() {
        click(By.name("new"));
    }

    public void selectGroup(GroupData group) {
        wd.findElement(By.cssSelector("input[value='" + group.getId() + "']")).click();
    }

    public void deleteSelectedGrouops() {
        click(By.name("delete"));
    }

    public void initGroupModification() {
        click(By.name("edit"));
    }

    public void submitGroupModification() {
        click(By.name("update"));
    }

    public void create(GroupData group) {
        initGroupCreation();
        fillGroupForm(group);
        submitGroupCreation();
        returnToGroupPage();
        groupCache = null;
    }

    public void modify(GroupData group) {
        selectGroup(group);
        initGroupModification();
        fillGroupForm(group);
        submitGroupModification();
        returnToGroupPage();
        groupCache = null;
    }

    public void delete(GroupData group) {
        selectGroup(group);
        deleteSelectedGrouops();
        returnToGroupPage();
        groupCache = null;
    }

    private Groups groupCache = null;

    public Groups all() {
        if (groupCache != null){
            return new Groups(groupCache);
        }
        groupCache = new Groups();
        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
        for (WebElement element: elements) {
            int id = Integer.parseInt(element.findElement(By.name("selected[]")).getAttribute("value"));
            String name = element.getText();
            GroupData group = new GroupData().withId(id).withName(name);
            groupCache.add(group);
        }
        return new Groups(groupCache);
    }

}

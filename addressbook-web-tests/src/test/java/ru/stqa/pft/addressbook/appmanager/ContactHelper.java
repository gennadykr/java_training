package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"),contactData.getFirstname());
        type(By.name("lastname"),contactData.getLastname());
        type(By.name("address"),contactData.getAddress());
        type(By.name("home"),contactData.getHome());
        type(By.name("email"),contactData.getEmail());
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModification(contact);
        ContactData info = new ContactData();
        info.withId(contact.getId()).
                withFirstname(readTextFromEditField(By.name("firstname"))).
                withLastname(readTextFromEditField(By.name("lastname"))).
                withAddress(readTextFromEditField(By.name("address"))).
                withHome(readTextFromEditField(By.name("home"))).
                withMobile(readTextFromEditField(By.name("mobile"))).
                withWork(readTextFromEditField(By.name("work"))).
                withEmail(readTextFromEditField(By.name("email"))).
                withEmail2(readTextFromEditField(By.name("email2"))).
                withEmail3(readTextFromEditField(By.name("email3")));
        returnToContactPage();
        return info;
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
        wd.switchTo().alert().accept();
    }

    public void selectContact(ContactData contact) {
        wd.findElement(By.cssSelector("input[value='" + contact.getId() + "']")).click();
    }

    public void initContactModification(ContactData contact) {
        WebElement rowWithId = wd.findElement(By.xpath("//tr[.//input[@value='" + contact.getId() + "']]"));
        rowWithId.findElement(By.xpath(".//a[./*[@title='Edit']]")).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void create(ContactData contact) {
        initContactCreation();
        fillContactForm(contact);
        submitContactCreation();
        returnToContactPage();
        contactCache = null;
    }

    public void modify(ContactData contact) {
        initContactModification(contact);
        fillContactForm(contact);
        submitContactModification();
        returnToContactPage();
        contactCache = null;
    }

    public void delete(ContactData contact) {
        selectContact(contact);
        deleteSelectedContacts();
        returnToContactPage();
        contactCache = null;
    }

    //ToDo use from Navigateion! wd -> this?
    private void returnToContactPage() {
        System.out.println("Return to Contacts page");
        click(By.id("logo"));
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null){
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.cssSelector("tr[name=entry]"));
        for (WebElement element: elements) {
            int id = Integer.parseInt(element.findElement(By.name("selected[]")).getAttribute("value"));
            List<WebElement> tds = element.findElements(By.cssSelector("td"));
            String lastName = tds.get(1).getText();
            String firstName = tds.get(2).getText();
            String address = tds.get(3).getText();
            String allEmails = tds.get(4).getText();
            String allPhones = tds.get(5).getText();
            ContactData contact = new ContactData().withId(id).withFirstname(firstName).withLastname(lastName).
                    withAddress(address).withAllEmails(allEmails).withAllPhones(allPhones);
            contactCache.add(contact);
        }
        return new Contacts(contactCache);
    }

    public void addContactToGroup(ContactData contact, GroupData targetGroup) {
        returnToContactPage();
        selectContact(contact);

        WebElement addToGroupSelector = wd.findElement(By.cssSelector("select[name='to_group']"));
        Select select = new Select(addToGroupSelector);
        select.selectByValue(Integer.toString(targetGroup.getId()));

        wd.findElement(By.cssSelector("input[name='add']")).click();

        returnToContactPage();
        contactCache = null;
    }

    public void removeContactFromGroup(ContactData contact, GroupData group) {
        returnToContactPage();
        WebElement groupSelector = wd.findElement(By.cssSelector("select[name='group']"));
        Select selector = new Select(groupSelector);
        selector.selectByValue(Integer.toString(group.getId()));
        selectContact(contact);
        wd.findElement(By.cssSelector("input[name='remove']")).click();
        returnToContactPage();
        contactCache = null;
    }
}

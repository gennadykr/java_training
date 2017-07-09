package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

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
    }

    public void modify(ContactData contact) {
        initContactModification(contact);
        fillContactForm(contact);
        submitContactModification();
        returnToContactPage();
    }

    public void delete(ContactData contact) {
        selectContact(contact);
        deleteSelectedContacts();
        returnToContactPage();
    }

    //ToDo use from Navigateion! wd -> this?
    private void returnToContactPage() {
        System.out.println("Return to Contacts page");
        click(By.id("logo"));
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> elements = wd.findElements(By.cssSelector("tr[name=entry]"));
        for (WebElement element: elements) {
            int id = Integer.parseInt(element.findElement(By.name("selected[]")).getAttribute("value"));
            List<WebElement> tds = element.findElements(By.cssSelector("td"));
            String lastName = tds.get(1).getText();
            String firstName = tds.get(2).getText();
            String address = tds.get(3).getText();
            String all_emails = tds.get(4).getText();
            String all_phones = tds.get(5).getText();
            ContactData contact = new ContactData().withId(id).withFirstname(firstName).withLastname(lastName);
            contacts.add(contact);
        }
        return contacts;
    }

}

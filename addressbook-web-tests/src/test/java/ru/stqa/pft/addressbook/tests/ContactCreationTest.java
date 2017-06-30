package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        app.gotoContactPage();
        app.initContactCreation();
        app.fillContactForm(new ContactData("teste1", "test2", "test3", "test4", "test5"));
        app.submitContactCreation();
        app.gotoContactPage();
    }

}
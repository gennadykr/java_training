package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification(){
        app.getNavigationHelper().gotoContactPage();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("teste1", "test2", "test3", "test4", null));
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().gotoContactPage();
    }
}

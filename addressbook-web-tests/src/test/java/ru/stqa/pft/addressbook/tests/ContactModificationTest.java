package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification(){
        app.getNavigationHelper().gotoContactPage();
        if (!app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("test7", null, null, null, null));
        }
        app.getContactHelper().initContactModification(0);
        app.getContactHelper().fillContactForm(new ContactData("teste1", "test2", "test3", "test4", null));
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().gotoContactPage();
    }
}

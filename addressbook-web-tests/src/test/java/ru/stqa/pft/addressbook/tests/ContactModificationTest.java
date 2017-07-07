package ru.stqa.pft.addressbook.tests;

import java.util.List;
import java.util.Comparator;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification(){
        app.getNavigationHelper().gotoContactPage();
        if (!app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("test7", null, null, null, null));
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        ContactData contact = new ContactData(before.get(0).getId(),"teste1", "test2", "test3", "test4", null);
        app.getContactHelper().initContactModification(0);
        app.getContactHelper().fillContactForm(contact);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().gotoContactPage();
        List<ContactData> after = app.getContactHelper().getContactList();

        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);

    }
}

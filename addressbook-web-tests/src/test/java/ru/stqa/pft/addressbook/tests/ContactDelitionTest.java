package ru.stqa.pft.addressbook.tests;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDelitionTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        app.getNavigationHelper().gotoContactPage();
        if (!app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("test7", null, null, null, null));
        }
    }

    @Test
    public void testContactDelition() {
        List<ContactData> before = app.getContactHelper().getContactList();
        int index = 0;
        System.out.println(before.get(index));
        app.getContactHelper().selectContact(index);
        app.getContactHelper().deleteSelectedContacts();
        app.getNavigationHelper().gotoContactPage();
        List<ContactData> after = app.getContactHelper().getContactList();
        before.remove(index);
        Assert.assertEquals(before, after);
    }

}

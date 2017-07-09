package ru.stqa.pft.addressbook.tests;

import java.util.Comparator;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;


public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        app.goTo().contactPage();
        List<ContactData> before = app.contact().list();
        ContactData contact = new ContactData("teste1", "test2", "test3", "test4", "test5");
        app.contact().create(contact);
        List<ContactData> after = app.contact().list();

        before.add(contact);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
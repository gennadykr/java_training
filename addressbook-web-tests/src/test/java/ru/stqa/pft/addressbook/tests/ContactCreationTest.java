package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;
import java.util.Set;


public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        app.goTo().contactPage();
        Set<ContactData> before = app.contact().all();
        ContactData contact = new ContactData().withFirstname("teste1").withLastname("test2")
                .withAddress("test3").withEmail("test4").withHome("test5");
        app.contact().create(contact);
        Set<ContactData> after = app.contact().all();

        contact.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt());
        before.add(contact);
        Assert.assertEquals(before, after);
    }
}
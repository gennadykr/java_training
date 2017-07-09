package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().contactPage();
        if (app.contact().all().size() == 0){
            app.contact().create(new ContactData().withFirstname("test7"));
        }
    }

    @Test
    public void testContactModification(){
        int index = 0;
        Set<ContactData> before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact =
                new ContactData().withId(modifiedContact.getId())
                        .withFirstname("teste1").withLastname("test2").withAddress("test3").withEmail("test4");
        app.contact().modify(contact);
        Set<ContactData> after = app.contact().all();

        before.remove(modifiedContact);
        before.add(contact);
        Assert.assertEquals(before, after);
    }

}

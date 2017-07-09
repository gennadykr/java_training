package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactDelitionTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().contactPage();
        if (app.contact().all().size() == 0){
            app.contact().create(new ContactData().withFirstname("test7"));
        }
    }

    @Test
    public void testContactDelition() {
        Set<ContactData> before = app.contact().all();
        ContactData deletedContact= before.iterator().next();
        System.out.println(deletedContact);
        app.contact().delete(deletedContact);
        Set<ContactData> after = app.contact().all();
        before.remove(deletedContact);
        Assert.assertEquals(before, after);
    }


}

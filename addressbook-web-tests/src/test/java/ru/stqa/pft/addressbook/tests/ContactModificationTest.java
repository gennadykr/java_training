package ru.stqa.pft.addressbook.tests;

import java.util.List;
import java.util.Comparator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().contactPage();
        if (app.contact().list().size() == 0){
            app.contact().create(new ContactData().withFirstname("test7"));
        }
    }

    @Test
    public void testContactModification(){
        int index = 0;
        List<ContactData> before = app.contact().list();
        ContactData contact =
                new ContactData().withId(before.get(index).getId())
                        .withFirstname("teste1").withLastname("test2").withAddress("test3").withEmail("test4");
        app.contact().modify(index, contact);
        List<ContactData> after = app.contact().list();

        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }

}

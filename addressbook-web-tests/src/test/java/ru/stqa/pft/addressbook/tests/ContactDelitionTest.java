package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

public class ContactDelitionTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().contactPage();
        if (app.db().contacts().size() == 0){
            app.contact().create(new ContactData().withFirstname("test7"));
        }
    }

    @Test
    public void testContactDelition() {
        Contacts before = app.db().contacts();
        ContactData deletedContact= before.iterator().next();
        System.out.println(deletedContact);
        app.contact().delete(deletedContact);
        Contacts after = app.db().contacts();

        MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.withRemoved(deletedContact)));
    }


}

package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactFieldsTest extends TestBase{

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().contactPage();
        if (app.contact().all().size() == 0){
            app.contact().create(new ContactData().withFirstname("test7"));
        }
    }

    @Test
    public void testContactField() {
        app.goTo().contactPage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact, equalTo(contactInfoFromEditForm));

        assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));

        String combinedEmails =  combineEmails(contactInfoFromEditForm);
        assertThat(contact.getAllEmails(), equalTo(combinedEmails));

        String combinedPhones = combinePhones(contactInfoFromEditForm);
        assertThat(contact.getAllPhones(), equalTo(combinedPhones));
    }

}

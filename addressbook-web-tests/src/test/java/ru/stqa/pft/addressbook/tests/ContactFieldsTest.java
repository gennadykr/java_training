package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    private String combinePhones(ContactData contact){
        return Arrays.asList(contact.getHome(),contact.getMobile(),contact.getWork())
                .stream().filter((s)->! s.equals(""))
                .map(ContactFieldsTest::phoneCleaner)
                .collect(Collectors.joining("\n"));
    }

    public static String phoneCleaner(String phone){
        return phone.replaceAll("\\s", "").replaceAll("[-()]","");
    }

    private String combineEmails(ContactData contact){
        return Arrays.asList(contact.getEmail(),contact.getEmail2(),contact.getEmail3())
                .stream().filter((s)->! s.equals(""))
                .collect(Collectors.joining("\n"));
    }
}

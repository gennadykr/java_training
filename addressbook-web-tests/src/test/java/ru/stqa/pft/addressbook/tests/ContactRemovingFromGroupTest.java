package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactRemovingFromGroupTest extends TestBase {

    @Test
    public void testContactRemovingFromGroup(){
        if (app.db().groups().size() == 0){
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("group2"));
        }
        GroupData group = app.db().groups().iterator().next();

        if (app.db().contacts().size() == 0) {
            app.contact().create(new ContactData().withFirstname("contact2"));
        }
        ContactData contact = app.db().contacts().iterator().next();

        if (! group.getContacts().contains(contact)){
            app.contact().addContactToGroup(contact,group);
        }
        GroupData renewedGroup = app.db().groups().stream()
                .filter((g)-> (g.getId() == group.getId()))
                .collect(Collectors.toSet()).iterator().next();
        Contacts contactsInGroup = renewedGroup.getContacts();

        System.out.println("NB Group: " + group);
        System.out.println("NB contacts: " + contactsInGroup);
        System.out.println("NB contact: " + contact);

        app.contact().removeContactFromGroup(contact,group);
        GroupData renewedGroup2 = app.db().groups().stream()
                .filter((g)-> (g.getId() == group.getId()))
                .collect(Collectors.toSet()).iterator().next();
        System.out.println("NB new contacts: " + renewedGroup2.getContacts());

        assertThat(renewedGroup2.getContacts(), equalTo(contactsInGroup.withRemoved(contact)));
    }
}

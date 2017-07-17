package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddingToGroupTest extends TestBase {

    @Test
    public void testContactAddingToGroup(){
        if (app.db().groups().size() == 0){
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("group1"));
        }
        GroupData targetGroup = app.db().groups().iterator().next();

        if (contactsOutOfGroup(targetGroup).size() == 0) {
            app.contact().create(new ContactData().withFirstname("contact1"));
        }
        ContactData contact = contactsOutOfGroup(targetGroup).iterator().next();

        Contacts oldContactsInGroup = targetGroup.getContacts();
        app.contact().addContactToGroup(contact,targetGroup);

        GroupData renewedTargetGroup = app.db().groups().stream()
                .filter((g)-> (g.getId() == targetGroup.getId()))
                .collect(Collectors.toSet()).iterator().next();

        System.out.println("NB 1: " + renewedTargetGroup.getContacts() );
        System.out.println("NB 2: " + oldContactsInGroup.withAdded(contact) );

        assertThat(renewedTargetGroup.getContacts(), equalTo(oldContactsInGroup.withAdded(contact)));
    }

    public Contacts contactsOutOfGroup(GroupData targetGroup) {
        Contacts contactsInGroup = targetGroup.getContacts();
        Contacts contactsOutOfGroup = app.db().contacts();
        contactsOutOfGroup.removeAll(contactsInGroup);
        return contactsOutOfGroup;
    }
}


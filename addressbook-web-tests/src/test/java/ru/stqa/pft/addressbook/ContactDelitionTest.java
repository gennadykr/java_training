package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactDelitionTest extends TestBase {

    @Test
    public void ContactDelitionTest() {
        gotoContactPage();
        selectContact();
        deleteSelectedContacts();
        gotoContactPage();
    }

}

package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDelitionTest extends TestBase {

    @Test
    public void ContactDelitionTest() {
        app.gotoContactPage();
        app.selectContact();
        app.deleteSelectedContacts();
        app.gotoContactPage();
    }

}

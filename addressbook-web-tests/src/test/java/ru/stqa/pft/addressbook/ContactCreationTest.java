package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() {
        gotoContactPage();
        initContactCreation();
        fillContactForm(new ContactData("teste1", "test2", "test3", "test4", "test5"));
        submitContactCreation();
        gotoContactPage();
    }

}
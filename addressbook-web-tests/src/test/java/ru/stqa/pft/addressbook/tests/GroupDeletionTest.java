package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class GroupDeletionTest extends TestBase {

    @Test
    public void GroupDeletionTest() {
        app.gotoGroupPage();
        app.selectGroup();
        app.deleteSelectedGrouops();
        app.returnToGroupPage();
    }

}

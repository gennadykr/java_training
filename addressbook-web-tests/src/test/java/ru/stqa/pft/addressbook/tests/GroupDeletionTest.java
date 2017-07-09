package ru.stqa.pft.addressbook.tests;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupDeletionTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().groupPage();
        if (app.group().list().size() == 0){
            app.group().create(new GroupData("test1", null, null));
        }
    }

    @Test
    public void testGroupDeletion() {
        int index = 0;
        List<GroupData> before = app.group().list();
        System.out.println(before.get(index));
        app.group().delete(index);
        List <GroupData> after = app.group().list();
        before.remove(index);
        Assert.assertEquals(before, after);
    }

}

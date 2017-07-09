package ru.stqa.pft.addressbook.tests;

import java.util.List;
import java.util.Comparator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().groupPage();
        if (app.group().list().size() == 0){
            app.group().create(new GroupData("test1", null, null));
        }
    }

    @Test
    public void testGroupModification(){
        List<GroupData> before = app.group().list();
        int index = 0;
        GroupData group = new GroupData(before.get(index).getId(),"test1", "test2", null);
        app.group().modify(index, group);
        List <GroupData> after = app.group().list();

        Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }


}

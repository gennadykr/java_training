package ru.stqa.pft.addressbook.tests;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDelitionTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().contactPage();
        if (app.contact().list().size() == 0){
            app.contact().create(new ContactData("test7", null, null, null, null));
        }
    }

    @Test
    public void testContactDelition() {
        List<ContactData> before = app.contact().list();
        int index = 0;
        System.out.println(before.get(index));
        app.contact().delete(index);
        List<ContactData> after = app.contact().list();
        before.remove(index);
        Assert.assertEquals(before, after);
    }


}

package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);

    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser",BrowserType.CHROME));

    public static String phoneCleaner(String phone){
        return phone.replaceAll("\\s", "").replaceAll("[-()]","");
    }

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p) {
        logger.info(String.format("Start test %s with parameters %s", m.getName(), Arrays.asList(p)));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m) {
        logger.info(String.format("Stop test %s", m.getName()));
    }

    public void verifyGroupListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Groups dbGroups = app.db().groups();
            Groups uiGroups = app.group().all();
            assertThat(uiGroups, equalTo(
                    dbGroups.stream()
                            .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
                            .collect(Collectors.toSet())
            ));
        }
    }

    public void verifyContactListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Contacts dbContacts = app.db().contacts();
            Contacts uiContacts = app.contact().all();
            assertThat(uiContacts, equalTo(
                    dbContacts.stream()
                            .map((c) -> new ContactData().withId(c.getId()).withFirstname(c.getFirstname())
                                    .withLastname(c.getLastname()).withAddress(c.getAddress())
                                    .withAllEmails(combineEmails(c)).withAllPhones(combinePhones(c)))
                            .collect(Collectors.toSet())
            ));
        }
    }

    public String combinePhones(ContactData contact){
        return Arrays.asList(contact.getHome(),contact.getMobile(),contact.getWork())
                .stream().filter((s)->! s.equals(""))
                .map(TestBase::phoneCleaner)
                .collect(Collectors.joining("\n"));
    }

    public String combineEmails(ContactData contact){
        return Arrays.asList(contact.getEmail(),contact.getEmail2(),contact.getEmail3())
                .stream().filter((s)->! s.equals(""))
                .collect(Collectors.joining("\n"));
    }
}

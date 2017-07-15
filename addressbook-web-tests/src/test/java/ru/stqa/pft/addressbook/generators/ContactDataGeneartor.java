package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGeneartor {

    @Parameter(names="-c",description="Contact count")
    public int count;

    @Parameter(names="-f", description="Target file")
    public String file;

    @Parameter(names="-d", description="Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGeneartor generator = new ContactDataGeneartor();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List contacts = generateContacts(count);
        if (format.equals("json")) {
            saveAsJson(contacts, new File(file));
        } else {
            System.out.println("Unrecognized format " + format);
        }
    }
    private void saveAsJson(List contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        try (Writer writer = new FileWriter(file)){
            writer.write(json);
        }
    }
    private List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++){
            contacts.add(new ContactData().withFirstname(String.format("test %s", i))
                    .withLastname(String.format("lastname %s", i))
                    .withAddress(String.format("address %s", i))
                    .withEmail(String.format("email%s@example.com", i))
                    .withHome(String.format("555-%s", i)));
        }
        return contacts;
    }

}

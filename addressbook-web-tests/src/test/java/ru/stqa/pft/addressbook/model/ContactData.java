package ru.stqa.pft.addressbook.model;

public class ContactData {
    private final String firstname;
    private final String lastname;
    private final String address;
    private final String home;
    private final String email;

    public ContactData(String firstname, String lastname, String address, String home, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.home = home;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }

    public String getHome() {
        return home;
    }

    public String getEmail() {
        return email;
    }
}

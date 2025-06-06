package com.example.phonebook.Model;

import java.util.List;

public class Contact {
    private String firstName;
    private String lastName;
    private String company;
    private List<Phone> listPhone;
    private List<Email> listEmail;
    private String url;
    private String dob;
    private String nickname;
    private List<Social> listSocial;
    private List<message> listMessage;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public class Phone {
        private String number;
        private String type;
    }

    public class Email {
        private String address;
        private String type;
    }

    private class Social {
        private String name;
        private String url;
    }

    private class message {
        private String subject;
        private String body;
    }
}

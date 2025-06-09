package com.example.phonebook.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ContactFull {
    @Embedded
    public Contact contact;

    @Relation(parentColumn = "id", entityColumn = "contactId")
    public List<PhoneNumber> phones;

    @Relation(parentColumn = "id", entityColumn = "contactId")
    public List<DOB> dobs;

    @Relation(parentColumn = "id", entityColumn = "contactId")
    public List<Email> emails;

    @Relation(parentColumn = "id", entityColumn = "contactId")
    public List<Message> messages;

    @Relation(parentColumn = "id", entityColumn = "contactId")
    public List<NickName> nickNames;

    @Relation(parentColumn = "id", entityColumn = "contactId")
    public List<Social> socials;

    @Relation(parentColumn = "id", entityColumn = "contactId")
    public List<URL> urls;

    @Relation(parentColumn = "id", entityColumn = "contactId")
    public List<Address> addresses;
}

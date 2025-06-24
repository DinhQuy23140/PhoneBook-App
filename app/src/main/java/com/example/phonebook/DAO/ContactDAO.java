package com.example.phonebook.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.phonebook.Model.Contact;
import com.example.phonebook.Model.ContactFull;

import java.util.List;

@Dao
public interface ContactDAO {
    @Query("SELECT * FROM contacts")
    List<ContactFull> getAllContacts();

    @Insert
    long insertContact(Contact contact);
}

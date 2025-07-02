package com.example.phonebook.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.phonebook.Model.Contact;
import com.example.phonebook.Model.ContactFull;

import java.util.List;

@Dao
public interface ContactDAO {
    @Transaction
    @Query("SELECT * FROM contacts")
    List<ContactFull> getAllContacts();

    @Query("UPDATE contacts SET first_name = :firstName, last_name = :lastName, company = :company, note = :note WHERE id = :id")
    void updateContact(long id, String firstName, String lastName, String company, String note);

    @Insert
    long insertContact(Contact contact);
}

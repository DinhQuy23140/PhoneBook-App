package com.example.phonebook.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.phonebook.Model.Email;

import java.util.List;

@Dao
public interface EmailDAO {
    @Query("SELECT * FROM email")
    List<Email> getAllEmails();

    @Query("DELETE FROM email WHERE contactId = :id")
    void deleteEmail(long id);

    @Insert
    void insertEmail(List<Email> email);
}

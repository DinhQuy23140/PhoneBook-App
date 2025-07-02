package com.example.phonebook.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.phonebook.Model.URL;

import java.util.List;

@Dao
public interface URLDAO {

    @Query("SELECT * FROM URL")
    List<URL> getAllURLs();

    @Query("DELETE FROM URL WHERE contactId = :contactId")
    void deleteURL(long contactId);

    @Insert
    void insertURL(List<URL> url);
}

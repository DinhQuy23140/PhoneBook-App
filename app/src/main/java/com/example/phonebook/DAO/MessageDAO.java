package com.example.phonebook.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.phonebook.Model.Message;

import java.util.List;

@Dao
public interface MessageDAO {

    @Query("SELECT * FROM message")
    List<Message> getAllMessages();

    @Query("DELETE FROM message WHERE contactId = :id")
    void deleteMessage(long id);

    @Insert
    void insertMessage(List<Message> message);
}

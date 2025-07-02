package com.example.phonebook.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.phonebook.Model.PhoneNumber;

import java.util.List;

@Dao
public interface PhoneNumberDAO {
    @Query("SELECT * FROM phone_number")
    List<PhoneNumber> getAllPhoneNumbers();

    @Query("DELETE FROM phone_number WHERE contactId = :id")
    void deletePhoneNumber(long id);

    @Insert
    void insertPhoneNumber(List<PhoneNumber> phoneNumber);
}

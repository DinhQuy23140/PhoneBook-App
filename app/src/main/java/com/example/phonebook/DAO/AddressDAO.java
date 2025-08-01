package com.example.phonebook.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.phonebook.Model.Address;

import java.util.List;

@Dao
public interface AddressDAO {
    @Query("SELECT * FROM address")
    List<Address> getAllAddresses();

    @Query("DELETE FROM address WHERE contactId = :contactId")
    void deleteAddress(long contactId);

    @Insert
    void insertAddress(List<Address> address);
}

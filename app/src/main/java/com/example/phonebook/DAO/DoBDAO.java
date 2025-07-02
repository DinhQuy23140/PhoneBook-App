package com.example.phonebook.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.phonebook.Model.DOB;

import java.util.List;

@Dao
public interface DoBDAO {
    @Query("SELECT * FROM DoB")
    List<DOB> getAllDoB();

    @Query("SELECT * FROM DoB WHERE contactId = :id")
    DOB getDoBbyId(long id);

    @Query("DELETE FROM DoB WHERE contactId = :id")
    void deleteDoB(long id);

    @Insert
    void insertDoB(List<DOB> dob);
}

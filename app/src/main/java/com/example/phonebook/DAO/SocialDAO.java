package com.example.phonebook.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.phonebook.Model.Social;

import java.util.List;

@Dao
public interface SocialDAO {

    @Query("SELECT * FROM social")
    List<Social> getAllSocials();

    @Insert
    void insertSocial(List<Social> social);
}

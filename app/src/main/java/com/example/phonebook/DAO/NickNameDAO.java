package com.example.phonebook.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.phonebook.Model.NickName;

import java.util.List;

@Dao
public interface NickNameDAO {

    @Query("SELECT * FROM nick_name")
    List<NickName> getAllNickNames();

    @Insert
    void insertNickName(List<NickName> nickName);
}

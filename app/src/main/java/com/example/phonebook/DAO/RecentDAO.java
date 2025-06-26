package com.example.phonebook.DAO;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.phonebook.Model.Recent;

@Dao
public interface RecentDAO {
    @Insert
    public void insertRecent(Recent recent);
}

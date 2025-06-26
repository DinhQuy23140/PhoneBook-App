package com.example.phonebook.DAO;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.phonebook.Model.Favorite;

@Dao
public interface FavoriteDAO {
    @Insert
    void insertFavorite(Favorite favorite);
}

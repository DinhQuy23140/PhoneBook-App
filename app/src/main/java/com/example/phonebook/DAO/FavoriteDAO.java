package com.example.phonebook.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.phonebook.Model.Favorite;

@Dao
public interface FavoriteDAO {
    @Insert
    void insertFavorite(Favorite favorite);

    @Query("Update favorite SET isFavorite = :favorite WHERE contactId = :contactId")
    void updateFavorite(Boolean favorite, long contactId);
}

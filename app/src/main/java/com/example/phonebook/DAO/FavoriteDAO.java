package com.example.phonebook.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.phonebook.Model.ContactFull;
import com.example.phonebook.Model.Favorite;

import java.util.List;

@Dao
public interface FavoriteDAO {
    @Insert
    void insertFavorite(Favorite favorite);

    @Query("Update favorite SET isFavorite = :favorite WHERE contactId = :contactId")
    void updateFavorite(Boolean favorite, long contactId);

    @Transaction
    @Query("SELECT * FROM contacts WHERE id IN (SELECT contactId FROM Favorite WHERE isFavorite = 1)")
    List<ContactFull> getFavorite();
}

package com.example.phonebook.Model;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite",
        foreignKeys = @ForeignKey(entity = Contact.class,
        parentColumns = "id",
        childColumns = "contactId",
        onDelete = ForeignKey.CASCADE))
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    int id;
    long contactId;
    boolean isFavorite;

    public Favorite(long contactId, boolean isFavorite) {
        this.contactId = contactId;
        this.isFavorite = isFavorite;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}

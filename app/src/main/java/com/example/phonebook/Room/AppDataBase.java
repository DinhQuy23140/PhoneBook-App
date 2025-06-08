package com.example.phonebook.Room;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.phonebook.DAO.ContactDAO;
import com.example.phonebook.Model.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract ContactDAO contactDAO();
    @Override
    public void clearAllTables() {

    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(@NonNull DatabaseConfiguration databaseConfiguration) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}

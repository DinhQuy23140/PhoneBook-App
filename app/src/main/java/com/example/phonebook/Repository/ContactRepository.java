package com.example.phonebook.Repository;

import android.content.Context;

import androidx.room.Room;

import com.example.phonebook.DAO.ContactDAO;
import com.example.phonebook.Model.Contact;
import com.example.phonebook.Room.AppDataBase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ContactRepository {
    ContactDAO contactDAO;

    public interface CallBack {
        void onSuccess(List<Contact> result);
    }

    public ContactRepository(Context context) {
        AppDataBase appDataBase = Room.databaseBuilder(context, AppDataBase.class, "ContactDB").build();
        this.contactDAO = appDataBase.contactDAO();
    }

    public void insertContact(Contact contact) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> contactDAO.insertContact(contact));
    }

    public void getAllContacts( CallBack callBack) {
        List<Contact> result = new ArrayList<>();
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            callBack.onSuccess(contactDAO.getAllContacts());
        });
    }
}

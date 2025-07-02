package com.example.phonebook.Module.UpdateContact;

import android.content.Context;

import com.example.phonebook.Model.Contact;
import com.example.phonebook.Repository.ContactRepository;

import java.util.List;

import kotlin.Triple;

public class UpdateContactPresent implements UpdateContactContract.Present{
    UpdateContactContract.View view;
    ContactRepository contactRepository;

    public UpdateContactPresent(Context context, UpdateContactContract.View view) {
        this.view = view;
        this.contactRepository = new ContactRepository(context);
    }

    @Override
    public void updateContact(Contact contact, List<Triple<String, String, Long>> listPhone, List<Triple<String, String, Long>> listEmail, List<Triple<String, String, Long>> listNickname, List<Triple<String, String, Long>> listURL, List<Triple<String, String, Long>> listAddress, List<Triple<String, String, Long>> listDoB, List<Triple<String, String, Long>> listSocial, List<Triple<String, String, Long>> listMessage) {

    }
}

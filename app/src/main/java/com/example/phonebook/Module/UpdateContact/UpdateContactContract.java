package com.example.phonebook.Module.UpdateContact;

import com.example.phonebook.Model.Contact;

import java.util.List;

import kotlin.Triple;

public interface UpdateContactContract {
    interface View {
        void updateContactSuccess();
        void updateContactFail();
    }

    interface Present {
        void updateContact(Contact contact, List<Triple<String, String, Long>>listPhone,
                           List<Triple<String, String, Long>>listEmail, List<Triple<String, String, Long>>listNickname,
                           List<Triple<String, String, Long>>listURL, List<Triple<String, String, Long>>listAddress,
                           List<Triple<String, String, Long>>listDoB, List<Triple<String, String, Long>>listSocial,
                           List<Triple<String, String, Long>>listMessage);
    }
}

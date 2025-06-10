package com.example.phonebook.Repository;

import android.content.Context;

import androidx.room.Room;

import com.example.phonebook.DAO.AddressDAO;
import com.example.phonebook.DAO.ContactDAO;
import com.example.phonebook.DAO.DoBDAO;
import com.example.phonebook.DAO.EmailDAO;
import com.example.phonebook.DAO.MessageDAO;
import com.example.phonebook.DAO.NickNameDAO;
import com.example.phonebook.DAO.PhoneNumberDAO;
import com.example.phonebook.DAO.SocialDAO;
import com.example.phonebook.DAO.URLDAO;
import com.example.phonebook.Model.Address;
import com.example.phonebook.Model.Contact;
import com.example.phonebook.Model.DOB;
import com.example.phonebook.Model.Email;
import com.example.phonebook.Model.Message;
import com.example.phonebook.Model.NickName;
import com.example.phonebook.Model.PhoneNumber;
import com.example.phonebook.Model.Social;
import com.example.phonebook.Model.URL;
import com.example.phonebook.Room.AppDataBase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ContactRepository {
    ContactDAO contactDAO;
    PhoneNumberDAO phoneNumberDAO;
    EmailDAO emailDAO;
    NickNameDAO nickNameDAO;
    URLDAO urldao;
    AddressDAO addressDAO;
    DoBDAO DOBDAO;
    SocialDAO socialDAO;
    MessageDAO messageDAO;

    public interface CallBack {
        void onSuccess(List<Contact> result);
    }

    public interface CallBackInsert {
        void onSuccess(long id);
    }

    public ContactRepository(Context context) {
        AppDataBase appDataBase = Room.databaseBuilder(context, AppDataBase.class, "ContactDB.db").build();
        this.contactDAO = appDataBase.contactDAO();
        this.phoneNumberDAO = appDataBase.phoneNumberDAO();
        this.emailDAO = appDataBase.emailDAO();
        this.nickNameDAO = appDataBase.nickNameDAO();
        this.urldao = appDataBase.urlDAO();
        this.addressDAO = appDataBase.addressDAO();
        this.DOBDAO = appDataBase.dOBDAO();
        this.socialDAO = appDataBase.socialDAO();
        this.messageDAO = appDataBase.messageDAO();
    }

    public void insertContact(Contact contact, CallBackInsert callBack) {
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            long id = contactDAO.insertContact(contact);
            callBack.onSuccess(id);
        });
    }

    public void insertPhoneNumber(List<PhoneNumber> phoneNumber) {
        phoneNumberDAO.insertPhoneNumber(phoneNumber);
    }

    public void insertEmail(List<Email> email) {
        emailDAO.insertEmail(email);
    }

    public void insertNickName(List<NickName> nickName) {
        nickNameDAO.insertNickName(nickName);
    }

    public void insertURL(List<URL> url) {
        urldao.insertURL(url);
    }

    public void insertAddress(List<Address> address) {
        addressDAO.insertAddress(address);
    }

    public void insertDoB(List<DOB> DoB) {
        DOBDAO.insertDoB(DoB);
    }

    public void insertSocial(List<Social> social) {
        socialDAO.insertSocial(social);
    }

    public void insertMessage(List<Message> message) {
        messageDAO.insertMessage(message);
    }

    public void getAllContacts( CallBack callBack) {
        List<Contact> result = new ArrayList<>();
        Executors.newSingleThreadScheduledExecutor().execute(() -> {
            callBack.onSuccess(contactDAO.getAllContacts());
        });
    }
}

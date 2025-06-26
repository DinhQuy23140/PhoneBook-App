package com.example.phonebook.Module.AddContact;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.phonebook.Model.Address;
import com.example.phonebook.Model.Contact;
import com.example.phonebook.Model.DOB;
import com.example.phonebook.Model.Email;
import com.example.phonebook.Model.Favorite;
import com.example.phonebook.Model.Message;
import com.example.phonebook.Model.NickName;
import com.example.phonebook.Model.PhoneNumber;
import com.example.phonebook.Model.Social;
import com.example.phonebook.Model.URL;
import com.example.phonebook.Repository.ContactRepository;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import kotlin.Triple;

public class AddContactPresent implements AddContactContract.Presenter{
    ContactRepository contactRepository;
    AddContactContract.View view;

    public AddContactPresent(AddContactContract.View view, Context context) {
        this.view = view;
        contactRepository = new ContactRepository(context);
    }

    @Override
    public void insertContact(Contact contact, ContactRepository.CallBackInsert callBackInsert) {
        contactRepository.insertContact(contact, id -> {
            if (id > 0) {
                callBackInsert.onSuccess(id);
                view.addContactSuccess();
            } else {
                view.addContactFail("Insert Fail");
            }
        });
    }

    @Override
    public void insertPhoneNumber(List<PhoneNumber> phoneNumber) {
        contactRepository.insertPhoneNumber(phoneNumber);
    }

    @Override
    public void insertEmail(List<Email> email) {
        contactRepository.insertEmail(email);
    }

    @Override
    public void insertNickName(List<NickName> nickName) {
        contactRepository.insertNickName(nickName);
    }

    @Override
    public void insertURL(List<URL> url) {
        contactRepository.insertURL(url);
    }

    @Override
    public void insertAddress(List<Address> address) {
        contactRepository.insertAddress(address);
    }

    @Override
    public void insertDoB(List<DOB> DoB) {
        contactRepository.insertDoB(DoB);
    }

    @Override
    public void insertSocial(List<Social> social) {
        contactRepository.insertSocial(social);
    }

    @Override
    public void insertMessage(List<Message> message) {
        contactRepository.insertMessage(message);
    }


    public void setFavorite(Favorite favorite) {
        contactRepository.setFavorite(favorite);
    }

    @Override
    public void inserContact(String firstName, String lastName, String company, String note, ContactRepository.CallBackInsert callBackInsert) {
        Contact contact = new Contact(company, firstName, lastName, note);
        contactRepository.insertContact(contact, idContact -> {
            if (idContact > 0) {
                callBackInsert.onSuccess(idContact);
                view.addContactSuccess();
            } else {
                view.addContactFail("Insert Fail");
            }
        });
    }

    @Override
    public void insertAttr(List<Triple<String, String, Long>> listPhone, List<Triple<String, String, Long>> listEmail, List<Triple<String, String, Long>> listNickname, List<Triple<String, String, Long>> listURL, List<Triple<String, String, Long>> listAddress, List<Triple<String, String, Long>> listDoB, List<Triple<String, String, Long>> listSocial, List<Triple<String, String, Long>> listMessage, Favorite favorite) {
        List<PhoneNumber>phoneNumbers = createModel(listPhone, PhoneNumber.class);
        insertPhoneNumber(phoneNumbers);

        List<Email> emails = createModel(listEmail, Email.class);
        insertEmail(emails);

        List<NickName> nickNames = createModel(listNickname, NickName.class);
        insertNickName(nickNames);

        List<URL> urls = createModel(listURL, URL.class);
        insertURL(urls);

        List<Address> addresses = getAddresses(listAddress);
        insertAddress(addresses);

        List<DOB> DoBs = createModel(listDoB, DOB.class);
        insertDoB(DoBs);

        List<Social> socials = createModel(listSocial, Social.class);
        insertSocial(socials);

        List<Message> messages = createModel(listMessage, Message.class);
        insertMessage(messages);

        setFavorite(favorite);
    }

    @NonNull
    private static List<Address> getAddresses(List<Triple<String, String, Long>> listAddress) {
        List<Address> addresses = new ArrayList<>();
        for (Triple<String, String, Long> item : listAddress) {
            try {
                String [] address = item.getSecond().split("\n");
                String type = item.getFirst();
                long idContact = item.getThird();
                Address newAddress = new Address(idContact, address[0], address[2], address[3], type, address[1]);
                addresses.add(newAddress);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return addresses;
    }

    @Override
    public <T> List<T> createModel(List<Triple<String, String, Long>> listData, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (Triple<String, String, Long> item : listData) {
            try {
                Constructor<T> constructor = clazz.getConstructor(long.class, String.class, String.class);
                result.add(constructor.newInstance(item.getThird(), item.getFirst(), item.getSecond()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}

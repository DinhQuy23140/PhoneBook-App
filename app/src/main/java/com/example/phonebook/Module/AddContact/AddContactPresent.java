package com.example.phonebook.Module.AddContact;

import android.content.Context;

import com.example.phonebook.Model.Address;
import com.example.phonebook.Model.Contact;
import com.example.phonebook.Model.DOB;
import com.example.phonebook.Model.Email;
import com.example.phonebook.Model.Message;
import com.example.phonebook.Model.NickName;
import com.example.phonebook.Model.PhoneNumber;
import com.example.phonebook.Model.Social;
import com.example.phonebook.Model.URL;
import com.example.phonebook.Repository.ContactRepository;

import java.util.List;

public class AddContactPresent implements AddContactContract.Presenter{
    ContactRepository contactRepository;
    AddContactContract.View view;

    public AddContactPresent(AddContactContract.View view, Context context) {
        this.view = view;
        contactRepository = new ContactRepository(context);
    }

    @Override
    public void insertContact(Contact contact, ContactRepository.CallBackInsert callBackInsert) {
        contactRepository.insertContact(contact, new ContactRepository.CallBackInsert() {
            @Override
            public void onSuccess(long id) {
                if (id > 0) {
                    callBackInsert.onSuccess(id);
                    view.addContactSuccess();
                } else {
                    view.addContactFail("Insert Fail");
                }
            }
        });
    };

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


}

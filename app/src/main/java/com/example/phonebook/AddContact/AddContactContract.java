package com.example.phonebook.AddContact;

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

public interface AddContactContract {
    interface View {
        void addContactSuccess();

        void addContactFail(String error);
    }

    interface Presenter {
        void insertContact(Contact contact, ContactRepository.CallBackInsert callBackInsert);
        void insertPhoneNumber(List<PhoneNumber> phoneNumber);
        void insertEmail(List<Email> email);
        void insertNickName(List<NickName> nickName);
        void insertURL(List<URL> url);
        void insertAddress(List<Address> address);
        void insertDoB(List<DOB> DoB);
        void insertSocial(List<Social> social);
        void insertMessage(List<Message> message);

    }
}

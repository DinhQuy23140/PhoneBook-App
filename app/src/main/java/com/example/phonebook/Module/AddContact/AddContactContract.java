package com.example.phonebook.Module.AddContact;

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

import java.util.List;

import kotlin.Triple;

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
        <T> List<T> createModel(List<Triple<String, String, Long>> listData, Class<T> clazz);
        void inserContact(String firstName, String lastName, String company, String note, ContactRepository.CallBackInsert callBackInsert);
        void insertAttr(List<Triple<String, String, Long>> listPhone, List<Triple<String, String, Long>> listEmail, List<Triple<String, String, Long>> listNickname,
                        List<Triple<String, String, Long>> listURL, List<Triple<String, String, Long>> listAddress, List<Triple<String, String, Long>> listDoB,
                        List<Triple<String, String, Long>> listSocial, List<Triple<String, String, Long>> listMessage, Favorite favorite);
    }
}

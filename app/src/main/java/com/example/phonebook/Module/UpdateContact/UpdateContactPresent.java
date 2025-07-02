package com.example.phonebook.Module.UpdateContact;

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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
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
        contactRepository.updateContact(contact);

        List<PhoneNumber> listPhoneNumber = createModel(listPhone, PhoneNumber.class);
        contactRepository.updatePhoneNumber(listPhoneNumber);

        List<Email> listEmails = createModel(listEmail, Email.class);
        contactRepository.updateEmail(listEmails);

        List<NickName> listNickNames = createModel(listNickname, NickName.class);
        contactRepository.updateNickName(listNickNames);

        List<URL> listURLs = createModel(listURL, URL.class);
        contactRepository.updateURL(listURLs);

        List<Address> listAddresss = getAddresses(listAddress);
        contactRepository.updateAddress(listAddresss);

        List<DOB> listDoBs = createModel(listDoB, DOB.class);
        contactRepository.updateDoB(listDoBs);

        List<Social> listSocials = createModel(listSocial, Social.class);
        contactRepository.updateSocial(listSocials);

        List<Message> listMessages = createModel(listMessage, Message.class);
        contactRepository.updateMessage(listMessages);
        view.updateContactSuccess();
    }

    private <T> List<T> createModel(List<Triple<String, String, Long>> listData, Class<T> clazz) {
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

    private List<Address> getAddresses(List<Triple<String, String, Long>> listAddress) {
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
}

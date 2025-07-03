package com.example.phonebook.Module.ContactDetail;

import android.content.Context;

import com.example.phonebook.Model.Favorite;
import com.example.phonebook.Repository.ContactRepository;

public class ContactDetailPresent implements ContactDetailContract.Present {

    ContactRepository contactRepository;
    Context context;
    ContactDetailContract.View view;

    public ContactDetailPresent(Context context, ContactDetailContract.View view) {
        this.context = context;
        this.contactRepository = new ContactRepository(context);
        this.view = view;
    }

    @Override
    public void updateFavorite(Favorite favorite) {
        contactRepository.updateFavorite(favorite);
    }

    @Override
    public void callRequest(String phoneNumber) {
        if (!phoneNumber.isEmpty()) {
            view.requestCall(phoneNumber);
        } else {
            view.showErrorMessage("Please enter phone number");
        }
    }

    @Override
    public void sendMessage(String phoneNumber) {
        if (!phoneNumber.isEmpty()) {
            view.requestSendMessage(phoneNumber);
        } else {
            view.showErrorMessage("Please enter phone number");
        }
    }

    @Override
    public void callVideo(String phoneNumber) {

    }

    @Override
    public void sendEmail(String email) {
        if (!email.isEmpty()) {
            view.requestSendEmail(email);
        } else {
            view.showErrorMessage("Please enter email");
        }
    }
}

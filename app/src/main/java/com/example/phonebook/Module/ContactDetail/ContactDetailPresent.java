package com.example.phonebook.Module.ContactDetail;

import android.content.Context;

import com.example.phonebook.Model.Favorite;
import com.example.phonebook.Repository.ContactRepository;

public class ContactDetailPresent implements ContactDetailContract.Present {

    ContactRepository contactRepository;
    Context context;

    public ContactDetailPresent( Context context) {
        this.context = context;
        this.contactRepository = new ContactRepository(context);
    }

    @Override
    public void updateFavorite(Favorite favorite) {
        contactRepository.updateFavorite(favorite);
    }
}

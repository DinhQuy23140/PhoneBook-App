package com.example.phonebook.Module.ContactDetail;

import com.example.phonebook.Model.Favorite;
import com.example.phonebook.Model.PhoneNumber;

public interface ContactDetailContract {
    interface View {

    }
    interface Present {
        void updateFavorite(Favorite favorite);
    }
}

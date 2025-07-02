package com.example.phonebook.Module.ContactDetail;

import com.example.phonebook.Model.Favorite;
import com.example.phonebook.Model.PhoneNumber;

public interface ContactDetailContract {
    interface View {
        void requestCall(String phoneNumber);
        void requestVideoCall(String phoneNumber);
        void requestSendEmail(String email);
        void requestSendMessage(String phoneNumber);
        void showErrorMessage(String message);
    }
    interface Present {
        void updateFavorite(Favorite favorite);

        void callRequest (String phoneNumber);

        void sendMessage(String phoneNumber);

        void callVideo(String phoneNumber);

        void sendEmail(String email);
    }
}

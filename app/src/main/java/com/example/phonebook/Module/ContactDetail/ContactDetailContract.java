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
/* <<<<<<<<<<<<<<  ✨ Windsurf Command ⭐ >>>>>>>>>>>>>>>> */
        /**
         * G i s  nh vi n kh ng th c thi g i  di n tho i b ng s  di n tho i
         * @param phoneNumber s  di n tho i c n g i
         */
/* <<<<<<<<<<  11d58b81-d46e-4c9f-b183-b47ea60229d6  >>>>>>>>>>> */
        void callRequest (String phoneNumber);

        void sendMessage(String phoneNumber);

        void callVideo(String phoneNumber);

        void sendEmail(String email);
    }
}

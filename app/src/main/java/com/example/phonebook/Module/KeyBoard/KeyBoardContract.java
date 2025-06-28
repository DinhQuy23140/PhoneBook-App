package com.example.phonebook.Module.KeyBoard;

public interface KeyBoardContract {
    interface View {
        void requestCall(StringBuilder phoneNumber);
        void showErrorMessage(String message);
    }

    interface Present {
        void callRequest (StringBuilder phoneNumber);
    }
}

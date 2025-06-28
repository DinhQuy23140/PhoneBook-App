package com.example.phonebook.Module.KeyBoard;

public class KeyBoardPresent implements KeyBoardContract.Present{

    KeyBoardContract.View view;

    public KeyBoardPresent(KeyBoardContract.View view){
        this.view = view;
    }
    @Override
    public void callRequest (StringBuilder phoneNumber) {
        if (phoneNumber.length() != 0) {
            view.requestCall(phoneNumber);
        } else {
            view.showErrorMessage("Please enter phone number");
        }
    }
}

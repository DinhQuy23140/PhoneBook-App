package com.example.phonebook.Module.Notify;

import androidx.annotation.NonNull;

import com.example.phonebook.Repository.ContactRepository;
import com.example.phonebook.Untilities.Constants;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        ContactRepository contactRepository = new ContactRepository(this);
        String phoneNumber = contactRepository.getPhone();
        if (phoneNumber != null) {
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put(Constants.KEY_FCM_TOKEN, token);
            tokenMap.put("token", token);
            FirebaseDatabase.getInstance().getReference()
                    .child(phoneNumber)
                    .setValue(tokenMap);
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
    }
}

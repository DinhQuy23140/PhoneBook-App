package com.example.phonebook.Module.WebRTC.Remote;

import androidx.annotation.NonNull;

import com.example.phonebook.Module.WebRTC.Utils.DataModel;
import com.example.phonebook.Module.WebRTC.Utils.ErrorCallBack;
import com.example.phonebook.Module.WebRTC.Utils.NewEventCallBack;
import com.example.phonebook.Module.WebRTC.Utils.SuccessCallBack;
import com.example.phonebook.Untilities.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FirebaseClient {
    private final Gson gson = new Gson();
    private final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
//    private String currentUsername;
    private static final String LATEST_EVENT_FIELD_NAME = "latest_event";

    public void login(String username, String phoneNumber, SuccessCallBack callBack){
        Map<String, Object> userData = new HashMap<>();
        userData.put(Constants.KEY_FIELD_USERNAME, username);
        userData.put(Constants.KEY_FIELD_PHONE_NUMBER, phoneNumber);
        dbRef.child(phoneNumber).setValue(userData).addOnCompleteListener(task -> {
//            currentUsername = phoneNumber;
            callBack.onSuccess();
        });
    }

//    public void setCurrentUsername(String username){
//        currentUsername = username;
//    }

    public void sendMessageToOtherUser(DataModel dataModel, ErrorCallBack errorCallBack){
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(dataModel.getTarget()).exists()){
                    //send the signal to other user
                    dbRef.child(dataModel.getTarget()).child(LATEST_EVENT_FIELD_NAME)
                            .setValue(gson.toJson(dataModel));
                    errorCallBack.onError(false);
                }else {
                    errorCallBack.onError(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                errorCallBack.onError(true);
            }
        });
    }



    public void observeIncomingLatestEvent(String senderName, NewEventCallBack callBack){
        dbRef.child(senderName).child(LATEST_EVENT_FIELD_NAME).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try{
                            String data= Objects.requireNonNull(snapshot.getValue()).toString();
                            DataModel dataModel = gson.fromJson(data,DataModel.class);
                            callBack.onNewEventReceived(dataModel);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }
}

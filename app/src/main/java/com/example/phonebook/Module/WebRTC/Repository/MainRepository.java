package com.example.phonebook.Module.WebRTC.Repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.phonebook.Interface.FCMService;
import com.example.phonebook.Module.WebRTC.Remote.FirebaseClient;
import com.example.phonebook.Module.WebRTC.Utils.DataModel;
import com.example.phonebook.Module.WebRTC.Utils.DataModelType;
import com.example.phonebook.Module.WebRTC.Utils.ErrorCallBack;
import com.example.phonebook.Module.WebRTC.Utils.NewEventCallBack;
import com.example.phonebook.Module.WebRTC.Utils.SuccessCallBack;
import com.example.phonebook.Module.WebRTC.webrtcs.MyPeerConnectionObserver;
import com.example.phonebook.Module.WebRTC.webrtcs.WebRTCClient;
import com.example.phonebook.Repository.ContactRepository;
import com.example.phonebook.Untilities.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.IceCandidate;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.SessionDescription;
import org.webrtc.SurfaceViewRenderer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainRepository implements WebRTCClient.Listener {
    public Listener listener;
    private final Gson gson = new Gson();
    private final FirebaseClient firebaseClient;
    private final FirebaseMessaging firebaseMessaging;
    ContactRepository contactRepository;

    private WebRTCClient webRTCClient;

//    private String currentUsername;

    private SurfaceViewRenderer remoteView;

    private String target;
//    private void updateCurrentUsername(String username){
//        this.currentUsername = username;
//        firebaseClient.setCurrentUsername(username);
//    }

    private MainRepository(Context context){
        this.firebaseClient = new FirebaseClient();
        this.firebaseMessaging = FirebaseMessaging.getInstance();
        this.contactRepository = new ContactRepository(context);
    }

    private static MainRepository instance;
    public static MainRepository getInstance(Context context){
        if (instance == null){
            instance = new MainRepository(context);
        }
        return instance;
    }

    public void login(String username, String phoneNumber, Context context, SuccessCallBack callBack){
        contactRepository.saveUserName(username);
        contactRepository.savePhoneNumber(phoneNumber);
        contactRepository.setLogin(true);
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put(Constants.KEY_FIELD_USERNAME, username);
            userMap.put(Constants.KEY_FIELD_PHONE_NUMBER, phoneNumber);
            userMap.put(Constants.KEY_FCM_TOKEN, token);
            FirebaseDatabase.getInstance().getReference()
                    .child(phoneNumber)
                    .setValue(userMap)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            callBack.onSuccess();
                        } else {
                            Log.e("Login", "Lỗi lưu thông tin người dùng", task.getException());
                        }
                    });
            contactRepository.saveToken(token);
        });
    }

    public void initWebRTCClient(Context context, String senderPhoneNumber){
//        updateCurrentUsername(phoneNumber);
        this.webRTCClient = new WebRTCClient(context,new MyPeerConnectionObserver(){
            @Override
            public void onAddStream(MediaStream mediaStream) {
                super.onAddStream(mediaStream);
                try{
                    mediaStream.videoTracks.get(0).addSink(remoteView);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onConnectionChange(PeerConnection.PeerConnectionState newState) {
                Log.d("TAG", "onConnectionChange: "+newState);
                super.onConnectionChange(newState);
                if (newState == PeerConnection.PeerConnectionState.CONNECTED && listener!=null){
                    listener.webrtcConnected();
                }

                if (newState == PeerConnection.PeerConnectionState.CLOSED ||
                        newState == PeerConnection.PeerConnectionState.DISCONNECTED ){
                    if (listener!=null){
                        listener.webrtcClosed();
                    }
                }
            }

            @Override
            public void onIceCandidate(IceCandidate iceCandidate) {
                super.onIceCandidate(iceCandidate);
                webRTCClient.sendIceCandidate(iceCandidate,target);
            }
        },senderPhoneNumber);
        webRTCClient.listener = this;
    }

    public void initLocalView(SurfaceViewRenderer view){
        webRTCClient.initLocalSurfaceView(view);
    }

    public void initRemoteView(SurfaceViewRenderer view){
        webRTCClient.initRemoteSurfaceView(view);
        this.remoteView = view;
    }

    public void startCall(String target){
        webRTCClient.call(target);
    }

    public void switchCamera() {
        webRTCClient.switchCamera();
    }

    public void toggleAudio(Boolean shouldBeMuted){
        webRTCClient.toggleAudio(shouldBeMuted);
    }
    public void toggleVideo(Boolean shouldBeMuted){
        webRTCClient.toggleVideo(shouldBeMuted);
    }
    public void sendCallRequest(String senderName, String target, ErrorCallBack errorCallBack){
        firebaseClient.sendMessageToOtherUser(
                new DataModel(target,senderName,null, DataModelType.StartCall),errorCallBack
        );
        // 1. Truy xuất FCM token của người nhận từ Firebase Realtime DB
        DatabaseReference tokenRef = FirebaseDatabase.getInstance().getReference()
                .child(target)
                .child(Constants.KEY_FCM_TOKEN);

        tokenRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String receiverToken = snapshot.getValue(String.class);
                if (receiverToken != null) {
                    sendFCMNotification(receiverToken, senderName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FCM", "Không thể truy cập token người nhận");
            }
        });
    }

    private void sendFCMNotification(String receiverToken, String callerPhone) {
        try {
            JSONObject notification = new JSONObject();
            notification.put("title", "Cuộc gọi đến");
            notification.put("body", "Bạn có cuộc gọi từ " + callerPhone);

            JSONObject data = new JSONObject();
            data.put("to", receiverToken);
            data.put("notification", notification);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://fcm.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            FCMService service = retrofit.create(FCMService.class);
            Call<ResponseBody> call = service.sendNotification(data);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Log.d("FCM", "Sent successfully");
                    } else {
                        try {
                            Log.e("FCM", "Error sending: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("FCM", "Error parsing errorBody");
                        }
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("FCM", "Failed to send notification", t);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void endCall(){
        webRTCClient.closeConnection();
    }

    public void subscribeForLatestEvent(String senderName, NewEventCallBack callBack){
        firebaseClient.observeIncomingLatestEvent(senderName, model -> {
            switch (model.getType()){
                case Offer:
                    this.target = model.getSender();
                    webRTCClient.onRemoteSessionReceived(new SessionDescription(
                            SessionDescription.Type.OFFER,model.getData()
                    ));
                    webRTCClient.answer(model.getSender());
                    break;
                case Answer:
                    this.target = model.getSender();
                    webRTCClient.onRemoteSessionReceived(new SessionDescription(
                            SessionDescription.Type.ANSWER,model.getData()
                    ));
                    break;
                case IceCandidate:
                    try{
                        IceCandidate candidate = gson.fromJson(model.getData(),IceCandidate.class);
                        webRTCClient.addIceCandidate(candidate);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case StartCall:
                    this.target = model.getSender();
                    callBack.onNewEventReceived(model);
                    break;
            }
        });
    }


    @Override
    public void onTransferDataToOtherPeer(DataModel model) {
        firebaseClient.sendMessageToOtherUser(model, error -> {

        });
    }

    public interface Listener{
        void webrtcConnected();
        void webrtcClosed();
    }
}

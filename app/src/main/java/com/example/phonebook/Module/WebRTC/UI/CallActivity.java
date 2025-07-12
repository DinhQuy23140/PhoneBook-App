package com.example.phonebook.Module.WebRTC.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.phonebook.MainActivity;
import com.example.phonebook.Model.Contact;
import com.example.phonebook.Module.WebRTC.Repository.MainRepository;
import com.example.phonebook.Module.WebRTC.Utils.DataModelType;
import com.example.phonebook.R;
import com.example.phonebook.Repository.ContactRepository;
import com.example.phonebook.Untilities.Constants;
import com.example.phonebook.databinding.ActivityCallBinding;

public class CallActivity extends AppCompatActivity implements MainRepository.Listener{

    private ActivityCallBinding views;
    private MainRepository mainRepository;
    private Boolean isCameraMuted = false;
    private Boolean isMicrophoneMuted = false;
    ContactRepository contactRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        views = ActivityCallBinding.inflate(getLayoutInflater());
        setContentView(views.getRoot());
        init();
    }

    private void init(){
        contactRepository = new ContactRepository(this);
        mainRepository = MainRepository.getInstance();
        mainRepository.initWebRTCClient(this, contactRepository.getPhone());
//        Intent intent = getIntent();
//        String targetPhoneNumber = intent.getStringExtra(Constants.KEY_FIELD_PHONE_NUMBER);
        //mainRepository.initWebRTCClient(this, targetPhoneNumber);
//        mainRepository.sendCallRequest(targetPhoneNumber,()->{
//            Toast.makeText(this, "couldnt find the target", Toast.LENGTH_SHORT).show();
//        });
        views.callBtn.setOnClickListener(v->{
            //start a call request here
            mainRepository.sendCallRequest(views.targetUserNameEt.getText().toString(),()->{
                Toast.makeText(this, "couldnt find the target", Toast.LENGTH_SHORT).show();
            });
        });
        mainRepository.initLocalView(views.localView);
        mainRepository.initRemoteView(views.remoteView);
        mainRepository.listener = this;

        mainRepository.subscribeForLatestEvent(data->{
            if (data.getType()== DataModelType.StartCall){
                runOnUiThread(()->{
                    views.incomingNameTV.setText(data.getSender()+" is Calling you");
                    views.incomingCallLayout.setVisibility(View.VISIBLE);
                    views.acceptButton.setOnClickListener(v->{
                        //star the call here
                        mainRepository.startCall(data.getSender());
                        views.incomingCallLayout.setVisibility(View.GONE);
                    });
                    views.rejectButton.setOnClickListener(v->{
                        views.incomingCallLayout.setVisibility(View.GONE);
                    });
                });
            }
        });

        views.switchCameraButton.setOnClickListener(v->{
            mainRepository.switchCamera();
        });

        views.micButton.setOnClickListener(v->{
            if (isMicrophoneMuted){
                views.micButton.setImageResource(R.drawable.ic_baseline_mic_off_24);
            }else {
                views.micButton.setImageResource(R.drawable.ic_baseline_mic_24);
            }
            isMicrophoneMuted=!isMicrophoneMuted;
            mainRepository.toggleAudio(isMicrophoneMuted);
        });

        views.videoButton.setOnClickListener(v->{
            if (isCameraMuted){
                views.videoButton.setImageResource(R.drawable.ic_baseline_videocam_off_24);
            }else {
                views.videoButton.setImageResource(R.drawable.ic_baseline_videocam_24);
            }
            isCameraMuted=!isCameraMuted;
            mainRepository.toggleVideo(isCameraMuted);
        });

        views.endCallButton.setOnClickListener(v->{
            mainRepository.endCall();
            finish();
            Intent intent = new Intent(CallActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(CallActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void webrtcConnected() {
        runOnUiThread(()->{
            views.incomingCallLayout.setVisibility(View.GONE);
            views.whoToCallLayout.setVisibility(View.GONE);
            views.callLayout.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void webrtcClosed() {
        runOnUiThread(this::finish);
    }
}
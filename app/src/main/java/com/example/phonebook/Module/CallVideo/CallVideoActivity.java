package com.example.phonebook.Module.CallVideo;

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
import com.example.phonebook.Module.WebRTC.Repository.MainRepository;
import com.example.phonebook.Module.WebRTC.UI.CallActivity;
import com.example.phonebook.Module.WebRTC.Utils.DataModelType;
import com.example.phonebook.R;
import com.example.phonebook.Repository.ContactRepository;
import com.example.phonebook.Untilities.Constants;
import com.example.phonebook.databinding.ActivityCallVideoBinding;

public class CallVideoActivity extends AppCompatActivity implements MainRepository.Listener{

    private ActivityCallVideoBinding activityCallVideoBinding;
    private MainRepository mainRepository;
    private Boolean isCameraMuted = false;
    private Boolean isMicrophoneMuted = false;
    ContactRepository contactRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        activityCallVideoBinding = ActivityCallVideoBinding.inflate(getLayoutInflater());
        setContentView(activityCallVideoBinding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    void init(){
        contactRepository = new ContactRepository(this);
        mainRepository = MainRepository.getInstance();
        mainRepository.initWebRTCClient(this, contactRepository.getPhone());
        mainRepository.listener = this;
//        Bundle bundle = getIntent().getExtras();
//        String typeCallVideo = bundle.getString(Constants.KEY_CALL_VIDEO_TYPE);
//        switch (typeCallVideo) {
//            case Constants.KEY_CALL_VIDEO_OUTGOING:{
//                Toast.makeText(this, Constants.KEY_CALL_VIDEO_OUTGOING, Toast.LENGTH_SHORT).show();
//                initOutGoing();
//                break;
//            }
//            case Constants.KEY_CALL_VIDEO_INCOMING: {
//                initInComing();
//                break;
//            }
//        };
        mainRepository.initLocalView(activityCallVideoBinding.localView);
        mainRepository.initRemoteView(activityCallVideoBinding.remoteView);
        mainRepository.subscribeForLatestEvent(contactRepository.getPhone(), data->{
            mainRepository.startCall(data.getSender());
        });

        activityCallVideoBinding.switchCameraButton.setOnClickListener(v->{
            mainRepository.switchCamera();
        });

        // on/off audio
        activityCallVideoBinding.micButton.setOnClickListener(v->{
            if (isMicrophoneMuted){
                activityCallVideoBinding.micButton.setImageResource(R.drawable.ic_baseline_mic_off_24);
            }else {
                activityCallVideoBinding.micButton.setImageResource(R.drawable.ic_baseline_mic_24);
            }
            isMicrophoneMuted=!isMicrophoneMuted;
            mainRepository.toggleAudio(isMicrophoneMuted);
        });

        // on/off video
        activityCallVideoBinding.videoButton.setOnClickListener(v->{
            if (isCameraMuted){
                activityCallVideoBinding.videoButton.setImageResource(R.drawable.ic_baseline_videocam_off_24);
            }else {
                activityCallVideoBinding.videoButton.setImageResource(R.drawable.ic_baseline_videocam_24);
            }
            isCameraMuted=!isCameraMuted;
            mainRepository.toggleVideo(isCameraMuted);
        });

        activityCallVideoBinding.endCallButton.setOnClickListener(v->{
            mainRepository.endCall();
            finish();
        });
    }

    void initInComing(){
        mainRepository.initLocalView(activityCallVideoBinding.localView);
        mainRepository.initRemoteView(activityCallVideoBinding.remoteView);
        mainRepository.subscribeForLatestEvent(contactRepository.getPhone(), data->{
            mainRepository.startCall(data.getSender());
        });
    }

    void initOutGoing(){
        mainRepository.initLocalView(activityCallVideoBinding.localView);
        mainRepository.initRemoteView(activityCallVideoBinding.remoteView);
        mainRepository.subscribeForLatestEvent(contactRepository.getPhone(), data->{
            mainRepository.startCall(data.getSender());
        });
    }

    @Override
    public void webrtcConnected() {

    }

    @Override
    public void webrtcClosed() {
        runOnUiThread(this::finish);
    }
}
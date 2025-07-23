package com.example.phonebook.Module.WebRTC.UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.phonebook.MainActivity;
import com.example.phonebook.Module.WebRTC.Repository.MainRepository;
import com.example.phonebook.Module.WebRTC.Test.TestNotifi.MyActionReceiver;
import com.example.phonebook.Module.WebRTC.Utils.DataModelType;
import com.example.phonebook.R;
import com.example.phonebook.Repository.ContactRepository;
import com.example.phonebook.Untilities.Constants;
import com.example.phonebook.databinding.ActivityCallBinding;

public class CallActivity extends AppCompatActivity implements MainRepository.Listener{

    private static final String CHANNEL_ID = "popup_channel";
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
        createNotificationChannel(); // Tạo channel 1 lần duy nhất
        contactRepository = new ContactRepository(this);
        mainRepository = MainRepository.getInstance(this);
        mainRepository.initWebRTCClient(this, contactRepository.getPhone());

        Intent getPhoneIntent = getIntent();
        String targetPhoneNumber = getPhoneIntent.getStringExtra(Constants.KEY_FIELD_PHONE_NUMBER);
        String senderName = contactRepository.getPhone();
        Toast.makeText(this, targetPhoneNumber, Toast.LENGTH_SHORT).show();
        mainRepository.sendCallRequest(senderName, targetPhoneNumber, error->{
            if (error) {
                Toast.makeText(this, "couldnt find the target", Toast.LENGTH_SHORT).show();
            }
        });

        views.callBtn.setOnClickListener(v->{
            //start a call request here
            mainRepository.sendCallRequest(senderName, views.targetUserNameEt.getText().toString(), error->{
                if (error) {
                    Toast.makeText(this, "couldnt find the target", Toast.LENGTH_SHORT).show();
                }
            });
        });
        mainRepository.initLocalView(views.localView);
        mainRepository.initRemoteView(views.remoteView);
        mainRepository.listener = this;

        mainRepository.subscribeForLatestEvent(contactRepository.getPhone(), data->{
            if (data.getType()== DataModelType.StartCall){
                runOnUiThread(()->{
//                    views.incomingNameTV.setText(data.getSender()+" is Calling you");
//                    views.incomingCallLayout.setVisibility(View.VISIBLE);
                    sendNotification();
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

        // switch camera
        views.switchCameraButton.setOnClickListener(v->{
            mainRepository.switchCamera();
        });

        // on/off audio
        views.micButton.setOnClickListener(v->{
            if (isMicrophoneMuted){
                views.micButton.setImageResource(R.drawable.ic_baseline_mic_off_24);
            }else {
                views.micButton.setImageResource(R.drawable.ic_baseline_mic_24);
            }
            isMicrophoneMuted=!isMicrophoneMuted;
            mainRepository.toggleAudio(isMicrophoneMuted);
        });

        // on/off video
        views.videoButton.setOnClickListener(v->{
            if (isCameraMuted){
                views.videoButton.setImageResource(R.drawable.ic_baseline_videocam_off_24);
            }else {
                views.videoButton.setImageResource(R.drawable.ic_baseline_videocam_24);
            }
            isCameraMuted=!isCameraMuted;
            mainRepository.toggleVideo(isCameraMuted);
        });

        // end call
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


    //notifi
    private void sendNotification() {
        // Kiểm tra quyền POST_NOTIFICATIONS trên Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        100);
                return; // Không tiếp tục nếu chưa có quyền
            }
        }

        @SuppressLint("RemoteViewLayout") RemoteViews customView = new RemoteViews(getPackageName(), R.layout.notification_incoming_call);

        Intent intent = new Intent(this, CallActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

// Intent cho nút "Trả lời"
        Intent acceptIntent = new Intent(this, MyActionReceiver.class);
        acceptIntent.setAction("ACTION_ACCEPT");
        PendingIntent acceptPendingIntent = PendingIntent.getBroadcast(this, 0, acceptIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        customView.setOnClickPendingIntent(R.id.btn_accept, acceptPendingIntent);

// Intent cho nút "Từ chối"
        Intent declineIntent = new Intent(this, MyActionReceiver.class);
        declineIntent.setAction("ACTION_DECLINE");
        PendingIntent declinePendingIntent = PendingIntent.getBroadcast(this, 1, declineIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        customView.setOnClickPendingIntent(R.id.btn_decline, declinePendingIntent);

// Notification với layout tùy chỉnh
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_call_incoming)
                .setCustomContentView(customView)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int notificationId = (int) System.currentTimeMillis(); // ID ngẫu nhiên theo thời gian
        notificationManager.notify(notificationId, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Thông báo nổi";
            String description = "Hiển thị thông báo popup có hành động";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
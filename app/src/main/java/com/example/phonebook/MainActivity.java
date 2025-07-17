package com.example.phonebook;

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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.phonebook.Module.CallVideo.CallVideoActivity;
import com.example.phonebook.Module.Favorite.FavoriteFragment;
import com.example.phonebook.Module.KeyBoard.KeyBoardFragment;
import com.example.phonebook.Module.Person.PersonFragment;
import com.example.phonebook.Module.Recent.RecentFragment;
import com.example.phonebook.Module.WebRTC.Repository.MainRepository;
import com.example.phonebook.Module.WebRTC.Test.TestNotifi.MyActionReceiver;
import com.example.phonebook.Module.WebRTC.UI.CallActivity;
import com.example.phonebook.Module.WebRTC.Utils.DataModelType;
import com.example.phonebook.Repository.ContactRepository;
import com.example.phonebook.Untilities.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "popup_channel";
    BottomNavigationView bottomNav;
    FavoriteFragment favoriteFragment = new FavoriteFragment();
    RecentFragment recentFragment = new RecentFragment();
    KeyBoardFragment keyBoardFragment = new KeyBoardFragment();
    PersonFragment personFragment = new PersonFragment();
    MainRepository mainRepository;
    ContactRepository contactRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        View layout = findViewById(R.id.frame_container);
        ViewCompat.setOnApplyWindowInsetsListener(layout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        serialize();

        bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.bottom_star) {
                replaceFragment(favoriteFragment);
                return true;
            } else if (id == R.id.bottom_recent) {
                replaceFragment(recentFragment);
                return true;
            } else if (id == R.id.bottom_keyboard) {
                replaceFragment(keyBoardFragment);
                return true;
            } else if (id == R.id.bottom_person) {
                replaceFragment(personFragment);
                return true;
            }
            return false;
        });

        createNotificationChannel();
        replaceFragment(keyBoardFragment);
        mainRepository.subscribeForLatestEvent(contactRepository.getPhone(),data->{
            if (data.getType()== DataModelType.StartCall){
                runOnUiThread(()->{
                    sendNotification();
                });
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }

    private void serialize() {
        mainRepository = MainRepository.getInstance();
        contactRepository = new ContactRepository(this);
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

        Intent intent = new Intent(this, CallVideoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_CALL_VIDEO_TYPE,Constants.KEY_CALL_VIDEO_INCOMING);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

// Intent cho nút "Trả lời"
        Intent acceptIntent = new Intent(this, CallVideoActivity.class);
        bundle.putString(Constants.KEY_CALL_VIDEO_TYPE,Constants.KEY_CALL_VIDEO_INCOMING);
        intent.putExtras(bundle);
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
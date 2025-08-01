package com.example.phonebook.Module.WebRTC.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.phonebook.MainActivity;
import com.example.phonebook.Model.Contact;
import com.example.phonebook.Module.WebRTC.Repository.MainRepository;
import com.example.phonebook.R;
import com.example.phonebook.Repository.ContactRepository;
import com.example.phonebook.Untilities.Constants;
import com.example.phonebook.databinding.ActivityLoginBinding;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.permissionx.guolindev.PermissionX;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding views;
    private MainRepository mainRepository;
    ContactRepository contactRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        views = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(views.getRoot());
        init();
        isLogin();
    }

    private void init() {
        contactRepository = new ContactRepository(this);
        mainRepository = MainRepository.getInstance(this);
        views.enterBtn.setOnClickListener(v -> {
            PermissionX.init(this)
                    .permissions(android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO)
                    .request((allGranted, grantedList, deniedList) -> {
                        if (allGranted) {
                            //login to firebase here
                            mainRepository.login(
                                    views.tvUsername.getText().toString(), views.tvPhoneNumber.getText().toString(), getApplicationContext(), () -> {
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    }
                            );
                        }
                    });
        });
    }

    private void isLogin() {
        boolean isLogin = contactRepository.isLogin();
        if (isLogin) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
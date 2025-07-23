package com.example.phonebook.Module.CallVideo;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.phonebook.R;
import com.example.phonebook.Untilities.Constants;
import com.example.phonebook.databinding.ActivityCallVideoDetailBinding;

public class CallVideoDetailActivity extends AppCompatActivity {

    ActivityCallVideoDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCallVideoDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnAccept.setOnClickListener(acept -> {
            Bundle bundle = getIntent().getExtras();
            int idNotification = bundle.getInt(Constants.KEY_ID_NOTIFICATION);
            if (idNotification != -1) {
                NotificationManagerCompat.from(this).cancel(idNotification);
            }
            Intent intent = new Intent(CallVideoDetailActivity.this, CallVideoActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        binding.btnDecline.setOnClickListener(decline -> {
            finish();
        });
    }
}
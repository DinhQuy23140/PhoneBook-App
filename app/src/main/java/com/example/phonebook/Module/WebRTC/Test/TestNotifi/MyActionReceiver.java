package com.example.phonebook.Module.WebRTC.Test.TestNotifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("ACTION_ACCEPT".equals(action)) {
            Toast.makeText(context, "Người dùng đã trả lời", Toast.LENGTH_SHORT).show();
        } else if ("ACTION_DECLINE".equals(action)) {
            Toast.makeText(context, "Người dùng đã từ chối", Toast.LENGTH_SHORT).show();
        }
    }
}

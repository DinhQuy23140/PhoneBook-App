package com.example.phonebook.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phonebook.R;
import com.google.firebase.database.FirebaseDatabase;

public class VideoCallFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;

    public VideoCallFragment() {}

    public static VideoCallFragment newInstance(String param1, String param2) {
        VideoCallFragment fragment = new VideoCallFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_call, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("masoud").setValue("HelloWorld");
    }
}

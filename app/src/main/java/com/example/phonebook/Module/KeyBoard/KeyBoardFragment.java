package com.example.phonebook.Module.KeyBoard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonebook.Adapter.NumberAdapter;
import com.example.phonebook.Interface.OnclickListener;
import com.example.phonebook.R;
import com.example.phonebook.Untilities.GridDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KeyBoardFragment extends Fragment implements KeyBoardContract.View{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView tvPhoneNumber, tvAdd;
    ImageView ivDelete;
    ImageButton callButton;
    RecyclerView rvKeyboard;
    NumberAdapter numberAdapter;
    List<String> listNumber = new ArrayList<>();
    StringBuilder number = new StringBuilder();
    KeyBoardPresent keyBoardPresent;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public KeyBoardFragment() {
        // Required empty public constructor
    }

    public static KeyBoardFragment newInstance(String param1, String param2) {
        KeyBoardFragment fragment = new KeyBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (listNumber.isEmpty()) {
            Collections.addAll(listNumber, "1", "2", "3", "4", "5", "6", "7", "8", "9", "*", "0", "#");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_key_board, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        serialize();

        listNumber.clear();
        Collections.addAll(listNumber, "1", "2", "3", "4", "5", "6", "7", "8", "9", "*", "0", "#");
        numberAdapter = new NumberAdapter(getContext(), listNumber, new OnclickListener() {
            @Override
            public void onClick(int position) {
                number.append(listNumber.get(position));
                tvPhoneNumber.setText(number);
                tvAdd.setVisibility(View.VISIBLE);
                ivDelete.setVisibility(View.VISIBLE);
            }
        });
        rvKeyboard.setAdapter(numberAdapter);

        ivDelete.setOnClickListener(delete -> {
            if (number.length() > 0) {
                number.deleteCharAt(number.length() - 1);
                tvPhoneNumber.setText(number.toString());
                tvAdd.setVisibility(View.VISIBLE);
                if (number.length() == 0) {
                    ivDelete.setVisibility(View.GONE);
                    tvAdd.setVisibility(View.GONE);
                }
            }

        });

        Handler handler = new Handler();
        Runnable delete = new Runnable() {
            @Override
            public void run() {
                if (number.length() > 0) {
                    number.deleteCharAt(number.length() - 1);
                    tvPhoneNumber.setText(number.toString());
                    if (number.length() == 0) {
                        ivDelete.setVisibility(View.GONE);
                        tvAdd.setVisibility(View.GONE);
                    }
                    handler.postDelayed(this, 100);
                }
            }
        };

        ivDelete.setOnLongClickListener(v -> {
            handler.post(delete);
            return false;
        });

        ivDelete.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP ||
                    event.getAction() == MotionEvent.ACTION_CANCEL ||
                    event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    handler.removeCallbacks(delete);
            }
            return false;
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyBoardPresent.callRequest(number);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall(number); // Được cấp quyền
            } else {
                Toast.makeText(getContext(), "Bạn cần cấp quyền gọi điện để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initUI(View view) {
        callButton = view.findViewById(R.id.call_button);
        tvAdd = view.findViewById(R.id.tv_add_phone);
        tvPhoneNumber = view.findViewById(R.id.tv_phonenumber_display);
        rvKeyboard = view.findViewById(R.id.rv_keyboard);
        int numCol = 3;
        rvKeyboard.setLayoutManager(new GridLayoutManager(getContext(), numCol));
        rvKeyboard.addItemDecoration(new GridDecoration(numCol, 30));
        ivDelete = view.findViewById(R.id.iv_delete);
    }

    private void serialize() {
        keyBoardPresent = new KeyBoardPresent(this);
    }

    private void makeCall(StringBuilder phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

    @Override
    public void requestCall(StringBuilder phoneNumber) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CALL_PHONE}, 1001);
        } else {
            makeCall(number); // Gọi nếu đã có quyền
        }
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
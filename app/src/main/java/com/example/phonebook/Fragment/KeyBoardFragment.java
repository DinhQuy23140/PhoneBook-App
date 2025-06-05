package com.example.phonebook.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phonebook.Adapter.NumberAdapter;
import com.example.phonebook.Interface.OnclickListener;
import com.example.phonebook.R;
import com.example.phonebook.Untilities.GridDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KeyBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KeyBoardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView tvPhoneNumber, tvAdd;
    ImageView ivDelete;
    RecyclerView rvKeyboard;
    NumberAdapter numberAdapter;
    List<String> listNumber = new ArrayList<>();
    String number = "";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public KeyBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KeyBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        tvAdd = view.findViewById(R.id.tv_add_phone);
        tvPhoneNumber = view.findViewById(R.id.tv_phonenumber_display);
        rvKeyboard = view.findViewById(R.id.rv_keyboard);
        int numCol = 3;
        rvKeyboard.setLayoutManager(new GridLayoutManager(getContext(), numCol));
        rvKeyboard.addItemDecoration(new GridDecoration(numCol, 30));
        listNumber.addAll(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "*", "0", "#"));
        numberAdapter = new NumberAdapter(getContext(), listNumber, new OnclickListener() {
            @Override
            public void onClick(int position) {
                number += listNumber.get(position);
                tvPhoneNumber.setText(number);
                tvAdd.setVisibility(View.VISIBLE);
                ivDelete.setVisibility(View.VISIBLE);
            }
        });
        rvKeyboard.setAdapter(numberAdapter);
        ivDelete = view.findViewById(R.id.iv_delete);
        ivDelete.setOnClickListener(delete -> {
            if (!number.isEmpty()) {
                number = number.substring(0, number.length() - 1);
                tvPhoneNumber.setText(number);
                tvAdd.setVisibility(View.VISIBLE);
                if (number.isEmpty()) {
                    ivDelete.setVisibility(View.GONE);
                    tvAdd.setVisibility(View.GONE);
                }
            }
        });

        Handler handler = new Handler();
        Runnable delete = new Runnable() {
            @Override
            public void run() {
                if (!number.isEmpty()) {
                    number = number.substring(0, number.length() - 1);
                    tvPhoneNumber.setText(number);
                    if (number.isEmpty()) {
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
    }
}
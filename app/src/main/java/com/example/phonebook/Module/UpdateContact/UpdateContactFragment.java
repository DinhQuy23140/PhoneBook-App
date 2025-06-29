package com.example.phonebook.Module.UpdateContact;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phonebook.Model.ContactFull;
import com.example.phonebook.R;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateContactFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Gson gson;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateContactFragment newInstance(String param1, String param2) {
        UpdateContactFragment fragment = new UpdateContactFragment();
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
        return inflater.inflate(R.layout.fragment_update_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
//        String strContac = bundle.getString("contact");
//        ContactFull contactFull = gson.fromJson(strContac, ContactFull.class);
    }

    private void serilize() {
        gson = new Gson();
    }
}
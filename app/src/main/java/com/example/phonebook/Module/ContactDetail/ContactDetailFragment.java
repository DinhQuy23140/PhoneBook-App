package com.example.phonebook.Module.ContactDetail;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonebook.Adapter.AttrAdapter;
import com.example.phonebook.Adapter.GenericAdapter;
import com.example.phonebook.Model.ContactFull;
import com.example.phonebook.Model.Email;
import com.example.phonebook.Model.PhoneNumber;
import com.example.phonebook.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Gson gson;
    TextView tvContactFullName, tvContactCompany;
    RecyclerView rvContactEmail, rvContactNumber;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactDetailFragment newInstance(String param1, String param2) {
        ContactDetailFragment fragment = new ContactDetailFragment();
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
        return inflater.inflate(R.layout.fragment_contact_detail, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        serilize();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String strContac = bundle.getString("contact");
            ContactFull contactFull = gson.fromJson(strContac, ContactFull.class);
            String fullName = contactFull.contact.getFirstName() + " " + contactFull.contact.getLastName();
            tvContactFullName.setText(fullName);
            if (contactFull.contact.getCompany() != null) {
                tvContactCompany.setText(contactFull.contact.getCompany());
            }
            Toast.makeText(getContext(), "Contact: " + fullName, Toast.LENGTH_SHORT).show();

            List<PhoneNumber> listPhoneNumber = contactFull.phones;
            GenericAdapter<PhoneNumber> phoneAdapter = new GenericAdapter<PhoneNumber>(R.layout.item_attr, listPhoneNumber, ((itemView, phoneNumber,  position) -> {
                TextView tvTitle = itemView.findViewById(R.id.item_attr_title);
                tvTitle.setText(phoneNumber.getType());
                TextView tvValue = itemView.findViewById(R.id.item_attr_value);
                tvValue.setText(phoneNumber.getNumber());
            }));
            rvContactNumber.setAdapter(phoneAdapter);

            List<Email> listEmail = contactFull.emails;
            GenericAdapter<Email> emailAdapter = new GenericAdapter<Email>(R.layout.item_attr, listEmail, ((itemView, email,  position) -> {
                TextView tvTitle = itemView.findViewById(R.id.item_attr_title);
                tvTitle.setText(email.getType());
                TextView tvValue = itemView.findViewById(R.id.item_attr_value);
                tvValue.setText(email.getNumber());
            }));
            rvContactEmail.setAdapter(emailAdapter);
        }
    }

    private void initUI(View view) {
        tvContactFullName = view.findViewById(R.id.tv_contact_fullname);
        tvContactCompany = view.findViewById(R.id.tv_contact_company);
        rvContactNumber = view.findViewById(R.id.rv_att_phone);
        rvContactNumber.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvContactEmail = view.findViewById(R.id.rv_att_email);
        rvContactEmail.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void serilize() {
        gson = new Gson();
    }
}
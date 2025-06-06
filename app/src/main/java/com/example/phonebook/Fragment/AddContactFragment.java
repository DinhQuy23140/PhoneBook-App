package com.example.phonebook.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.phonebook.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddContactFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LinearLayout containerPhone, containerEmail, containerNickname, containerURL, containerAddress, containerDoB, containerSocial, containerMessage;
    ImageView ivAddPhone, ivAddEmail, ivAddNickname, ivAddURL, ivAddAddress, ivAddDoB, ivAddSocial, ivAddMessage;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddContactFragment newInstance(String param1, String param2) {
        AddContactFragment fragment = new AddContactFragment();
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
        return inflater.inflate(R.layout.fragment_add_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        containerPhone = view.findViewById(R.id.layout_phone);
        ivAddPhone = view.findViewById(R.id.add_btn_addphone);
        ivAddPhone.setOnClickListener(addPhone -> {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemPhone = inflater.inflate(R.layout.child_item,null);
            TextView tvInput = itemPhone.findViewById(R.id.et_input_att);
            tvInput.setHint(getString(R.string.contact_phone));
            ImageView ivDelete = itemPhone.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> {
                containerPhone.removeView(itemPhone);
            });
            containerPhone.addView(itemPhone);
        });

        containerEmail = view.findViewById(R.id.layout_email);
        ivAddEmail = view.findViewById(R.id.add_btn_addemail);
        ivAddEmail.setOnClickListener(addEmail -> {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemEmail = inflater.inflate(R.layout.child_item, null);
            TextView tvInput = itemEmail.findViewById(R.id.et_input_att);
            tvInput.setHint(getString(R.string.contact_email));
            ImageView ivDelete = itemEmail.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> {
                containerEmail.removeView(itemEmail);
            });
            containerEmail.addView(itemEmail);
        });

        containerNickname = view.findViewById(R.id.layout_nickname);
        ivAddNickname = view.findViewById(R.id.add_btn_addnickname);
        ivAddNickname.setOnClickListener(addNickname -> {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemNickname = inflater.inflate(R.layout.child_item, null);
            TextView tvInput = itemNickname.findViewById(R.id.et_input_att);
            tvInput.setHint(getString(R.string.contact_nickname));
            ImageView ivDelete = itemNickname.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> {
                containerNickname.removeView(itemNickname);
            });
            containerNickname.addView(itemNickname);
        });

        containerURL = view.findViewById(R.id.layout_url);
        ivAddURL = view.findViewById(R.id.add_btn_addurl);
        ivAddURL.setOnClickListener(addURL -> {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemURL = inflater.inflate(R.layout.child_item, null);
            TextView tvInput = itemURL.findViewById(R.id.et_input_att);
            tvInput.setHint(getString(R.string.contact_url));
            ImageView ivDelete = itemURL.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> {
                containerURL.removeView(itemURL);
            });
            containerURL.addView(itemURL);
        });

        containerAddress = view.findViewById(R.id.layout_address);
        ivAddAddress = view.findViewById(R.id.add_btn_addaddress);
        ivAddAddress.setOnClickListener(addAddress -> {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemAddress = inflater.inflate(R.layout.child_item, null);
            TextView tvInput = itemAddress.findViewById(R.id.et_input_att);
            tvInput.setHint(getString(R.string.contact_address));
            ImageView ivDelete = itemAddress.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> {
                containerAddress.removeView(itemAddress);
            });
            containerAddress.addView(itemAddress);
        });

        containerDoB = view.findViewById(R.id.layout_dob);
        ivAddDoB = view.findViewById(R.id.add_btn_adddob);
        ivAddDoB.setOnClickListener(addDoB -> {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemDoB = inflater.inflate(R.layout.child_item, null);
            TextView tvInput = itemDoB.findViewById(R.id.et_input_att);
            tvInput.setHint(getString(R.string.contact_dob));
            ImageView ivDelete = itemDoB.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> {
                containerDoB.removeView(itemDoB);
            });
            containerDoB.addView(itemDoB);
        });

        containerSocial = view.findViewById(R.id.layout_social);
        ivAddSocial = view.findViewById(R.id.add_btn_addsocial);
        ivAddSocial.setOnClickListener(addSocial -> {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemSocial = inflater.inflate(R.layout.child_item, null);
            TextView tvInput = itemSocial.findViewById(R.id.et_input_att);
            tvInput.setHint(getString(R.string.contact_social));
            ImageView ivDelete = itemSocial.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> {
                containerSocial.removeView(itemSocial);
            });
            containerSocial.addView(itemSocial);
        });

        containerMessage = view.findViewById(R.id.layout_message);
        ivAddMessage = view.findViewById(R.id.add_btn_addmessage);
        ivAddMessage.setOnClickListener(addMessage -> {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemMessage = inflater.inflate(R.layout.child_item, null);
            TextView tvInput = itemMessage.findViewById(R.id.et_input_att);
            tvInput.setHint(getString(R.string.contact_message));
            ImageView ivDelete = itemMessage.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> {
                containerMessage.removeView(itemMessage);
            });
            containerMessage.addView(itemMessage);
        });
    }
}
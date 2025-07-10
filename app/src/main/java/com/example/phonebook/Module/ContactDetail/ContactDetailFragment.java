package com.example.phonebook.Module.ContactDetail;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonebook.Adapter.GenericAdapter;
import com.example.phonebook.Model.Address;
import com.example.phonebook.Model.ContactFull;
import com.example.phonebook.Model.DOB;
import com.example.phonebook.Model.Email;
import com.example.phonebook.Model.Favorite;
import com.example.phonebook.Model.Message;
import com.example.phonebook.Model.NickName;
import com.example.phonebook.Model.PhoneNumber;
import com.example.phonebook.Model.Social;
import com.example.phonebook.Model.URL;
import com.example.phonebook.Module.UpdateContact.UpdateContactFragment;
import com.example.phonebook.Module.WebRTC.UI.CallActivity;
import com.example.phonebook.Module.WebRTC.UI.LoginActivity;
import com.example.phonebook.R;
import com.example.phonebook.Untilities.Constants;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class ContactDetailFragment extends Fragment implements ContactDetailContract.View{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Gson gson;
    Bundle bundle;
    ContactFull contactFull;
    ImageView ivBack, ivSendMessage, ivCall, ivCallVideo, ivSentEmail;
    TextView tvEditContact;
    TextView tvContactFullName, tvContactCompany, tvNote, tvDetailSendMessage, tvDetaiFavourite, tvDetailAddToGroup;
    RecyclerView rvContactEmail, rvContactNumber, rvNickName, rvURL, rvAddress, rvDoB, rvSocial, rvMessage;
    LinearLayout llAddEmail, llAddNumber, llAddNickName, llAddURL, llAddAddress, llAddDoB, llAddSocial, llAddMessage;
    ContactDetailPresent contactDetailPresent;
    private static final int REQUEST_PERMISSIONS_CODE = 123, REQUEST_CALL = 1001;

    private static final String[] REQUIRED_PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactDetailFragment() {
        // Required empty public constructor
    }

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
        serilize(requireContext());
        bundle = getArguments();
        String strContac = bundle.getString("contact");
        contactFull = gson.fromJson(strContac, ContactFull.class);
        loadContactDetail(contactFull);

        ivBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        tvEditContact.setOnClickListener(v -> {
            if (bundle != null) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                UpdateContactFragment updateFragment = new UpdateContactFragment();
                updateFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame_container, updateFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        tvDetaiFavourite.setOnClickListener(favo -> contactDetailPresent.updateFavorite(new Favorite(contactFull.contact.getId(), true)));

        ivCall.setOnClickListener(call -> {
            contactDetailPresent.callRequest(contactFull.phones.get(0).getNumber());
        });

        ivSendMessage.setOnClickListener(sendMessage -> {
            contactDetailPresent.sendMessage(contactFull.phones.get(0).getNumber());
        });

        ivSentEmail.setOnClickListener(sendEmail -> {
            contactDetailPresent.sendEmail(contactFull.emails.get(0).getValue());
        });

        ivCallVideo.setOnClickListener(callVideo -> {
            Intent intent = new Intent(requireActivity(), CallActivity.class);
            intent.putExtra(Constants.KEY_FIELD_PHONE_NUMBER, contactFull.phones.get(0).getNumber());
            startActivity(intent);
        });

    }

    private void loadContactDetail(ContactFull contactFull) {
        String fullName = contactFull.contact.getFirstName() + " " + contactFull.contact.getLastName();
        tvContactFullName.setText(fullName);
        if (contactFull.contact.getCompany() != null) {
            tvContactCompany.setText(contactFull.contact.getCompany());
        }
        Toast.makeText(getContext(), "Contact: " + fullName, Toast.LENGTH_SHORT).show();

        List<PhoneNumber> listPhoneNumber = contactFull.phones;
        llAddNumber.setVisibility(!listPhoneNumber.isEmpty() ? View.VISIBLE : View.GONE);
        GenericAdapter<PhoneNumber> phoneAdapter = new GenericAdapter<PhoneNumber>(R.layout.item_attr, listPhoneNumber, ((itemView, phoneNumber,  position) -> {
            TextView tvTitle = itemView.findViewById(R.id.item_attr_title);
            tvTitle.setText(phoneNumber.getType());
            TextView tvValue = itemView.findViewById(R.id.item_attr_value);
            tvValue.setText(phoneNumber.getNumber());
        }));
        rvContactNumber.setAdapter(phoneAdapter);

        List<Email> listEmail = contactFull.emails;
        llAddEmail.setVisibility(!listEmail.isEmpty() ? View.VISIBLE : View.GONE);
        GenericAdapter<Email> emailAdapter = new GenericAdapter<Email>(R.layout.item_attr, listEmail, ((itemView, email,  position) -> {
            TextView tvTitle = itemView.findViewById(R.id.item_attr_title);
            tvTitle.setText(email.getType());
            TextView tvValue = itemView.findViewById(R.id.item_attr_value);
            tvValue.setText(email.getValue());
        }));
        rvContactEmail.setAdapter(emailAdapter);

        List<NickName> listNickName = contactFull.nickNames;
        llAddNickName.setVisibility(!listNickName.isEmpty() ? View.VISIBLE : View.GONE);
        GenericAdapter<NickName> nickNameAdapter = new GenericAdapter<NickName>(R.layout.item_attr, listNickName, ((itemView, nickName,  position) -> {
            TextView tvTitle = itemView.findViewById(R.id.item_attr_title);
            tvTitle.setText(nickName.getType());
            TextView tvValue = itemView.findViewById(R.id.item_attr_value);
            tvValue.setText(nickName.getValue());
        }));
        rvNickName.setAdapter(nickNameAdapter);

        List<URL> listURL = contactFull.urls;
        llAddURL.setVisibility(!listURL.isEmpty() ? View.VISIBLE : View.GONE);
        GenericAdapter<URL> urlAdapter = new GenericAdapter<URL>(R.layout.item_attr, listURL, ((itemView, url,  position) -> {
            TextView tvTitle = itemView.findViewById(R.id.item_attr_title);
            tvTitle.setText(url.getType());
            TextView tvValue = itemView.findViewById(R.id.item_attr_value);
            tvValue.setText(url.getValue());
        }));
        rvURL.setAdapter(urlAdapter);

        List<Address> listAddress = contactFull.addresses;
        llAddAddress.setVisibility(!listAddress.isEmpty() ? View.VISIBLE : View.GONE);
        GenericAdapter<Address> addressAdapter = new GenericAdapter<Address>(R.layout.item_attr, listAddress, ((itemView, address,  position) -> {
            TextView tvTitle = itemView.findViewById(R.id.item_attr_title);
            tvTitle.setText(address.getType());
            TextView tvValue = itemView.findViewById(R.id.item_attr_value);
            tvValue.setText(address.toString());
        }));
        rvAddress.setAdapter(addressAdapter);

        List<DOB> listDoB = contactFull.dobs;
        llAddDoB.setVisibility(!listDoB.isEmpty() ? View.VISIBLE : View.GONE);
        GenericAdapter<DOB> doBAdapter = new GenericAdapter<DOB>(R.layout.item_attr, listDoB, ((itemView, doB,  position) -> {
            TextView tvTitle = itemView.findViewById(R.id.item_attr_title);
            tvTitle.setText(doB.getType());
            TextView tvValue = itemView.findViewById(R.id.item_attr_value);
            tvValue.setText(doB.getValue());
        }));
        rvDoB.setAdapter(doBAdapter);

        List<Social> listSocial = contactFull.socials;
        llAddSocial.setVisibility(!listSocial.isEmpty() ? View.VISIBLE : View.GONE);
        GenericAdapter<Social> socialAdapter = new GenericAdapter<Social>(R.layout.item_attr, listSocial, ((itemView, social,  position) -> {
            TextView tvTitle = itemView.findViewById(R.id.item_attr_title);
            tvTitle.setText(social.getType());
            TextView tvValue = itemView.findViewById(R.id.item_attr_value);
            tvValue.setText(social.getValue());
        }));
        rvSocial.setAdapter(socialAdapter);

        List<Message> listMessage = contactFull.messages;
        llAddMessage.setVisibility(!listMessage.isEmpty() ? View.VISIBLE : View.GONE);
        GenericAdapter<Message> messageAdapter = new GenericAdapter<Message>(R.layout.item_attr, listMessage, ((itemView, message,  position) -> {
            TextView tvTitle = itemView.findViewById(R.id.item_attr_title);
            tvTitle.setText(message.getType());
            TextView tvValue = itemView.findViewById(R.id.item_attr_value);
            tvValue.setText(message.getValue());
        }));
        rvMessage.setAdapter(messageAdapter);

        tvNote.setText(contactFull.contact.getNote());
    }

    private void initUI(View view) {
        ivBack = view.findViewById(R.id.iv_detail_back);
        ivSendMessage = view.findViewById(R.id.iv_sent_message);
        ivCall = view.findViewById(R.id.iv_detail_call);
        ivCallVideo = view.findViewById(R.id.iv_detail_call_video);
        ivSentEmail = view.findViewById(R.id.iv_detail_sent_email);
        tvEditContact = view.findViewById(R.id.tv_detail_edit);
        tvContactFullName = view.findViewById(R.id.tv_contact_fullname);
        tvContactCompany = view.findViewById(R.id.tv_contact_company);
        rvContactNumber = view.findViewById(R.id.rv_att_phone);
        rvContactNumber.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvContactEmail = view.findViewById(R.id.rv_att_email);
        rvContactEmail.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvNickName = view.findViewById(R.id.rv_att_nickname);
        rvNickName.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvURL = view.findViewById(R.id.rv_att_url);
        rvURL.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvAddress = view.findViewById(R.id.rv_att_address);
        rvAddress.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvDoB = view.findViewById(R.id.rv_att_dob);
        rvDoB.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvSocial = view.findViewById(R.id.rv_att_social);
        rvSocial.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvMessage = view.findViewById(R.id.rv_att_message);
        rvMessage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        tvNote = view.findViewById(R.id.tv_attr_note);

        llAddNumber = view.findViewById(R.id.layout_phone);
        llAddEmail = view.findViewById(R.id.layout_email);
        llAddNickName = view.findViewById(R.id.layout_nickname);
        llAddURL = view.findViewById(R.id.layout_url);
        llAddAddress = view.findViewById(R.id.layout_address);
        llAddDoB = view.findViewById(R.id.layout_dob);
        llAddSocial = view.findViewById(R.id.layout_social);
        llAddMessage = view.findViewById(R.id.layout_message);

        tvDetailSendMessage = view.findViewById(R.id.contact_detail_sent_message);
        tvDetaiFavourite = view.findViewById(R.id.contact_detail_add_favo);
        tvDetailAddToGroup = view.findViewById(R.id.contact_detail_add_list);
    }



    private void serilize(Context context) {
        gson = new Gson();
        contactDetailPresent = new ContactDetailPresent(context, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhoneNumber phoneNumber = contactFull.phones.get(0);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall(phoneNumber.getNumber());
            }
        }
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                makeCallVideo(phoneNumber.getNumber(), "1");
            }
        }
    }

    @Override
    public void requestCall(String phoneNumber) {
        String number = "tel:" + phoneNumber.toString();
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            makeCall(number);
        }
    }

    public void requestCallVideo(String phoneNumber) {
    }
    
    private void checkAndRequestPermissions() {
        List<String> permissionsToRequest = new ArrayList<>();
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(requireActivity(),
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_CODE);
        } else {
            makeCallVideo(contactFull.phones.get(0).getNumber(), "1");
        }
    }

    @Override
    public void requestVideoCall(String phoneNumber) {

    }

    @Override
    public void requestSendEmail(String email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        try {
            startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void requestSendMessage(String phoneNumber) {
        String number = "smsto:" + phoneNumber;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(number));
        startActivity(intent);
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    void makeCall(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(phoneNumber));
        startActivity(callIntent);
    }


    public void makeCallVideo(String callerPhoneNumber, String calleePhoneNumber) {
    }


}
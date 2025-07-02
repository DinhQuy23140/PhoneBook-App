package com.example.phonebook.Module.UpdateContact;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.phonebook.Fragment.AddAttDialogFragment;
import com.example.phonebook.Model.Address;
import com.example.phonebook.Model.ContactFull;
import com.example.phonebook.Model.DOB;
import com.example.phonebook.Model.Email;
import com.example.phonebook.Model.Message;
import com.example.phonebook.Model.NickName;
import com.example.phonebook.Model.PhoneNumber;
import com.example.phonebook.Model.Social;
import com.example.phonebook.Model.URL;
import com.example.phonebook.Module.AddContact.AddContactFragment;
import com.example.phonebook.R;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

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
    TextView tvCancel;
    EditText edtFirstName, edtLastName, edtCompany, edtNote;
    LinearLayout lnAddPhone, lnAddEmail, lnAddNickname, lnAddURL, lnAddAddress, lnAddDoB, lnAddSocial, lnAddMessage;
    ImageView ivAddPhone, ivAddEmail, ivAddNickname, ivAddURL, ivAddAddress, ivAddDoB, ivAddSocial, ivAddMessage;
    UpdateContactPresent updateContactPresent;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateContactFragment() {
        // Required empty public constructor
    }
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
        serilize();
        initUI(view);

        tvCancel.setOnClickListener(cancel -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        Bundle bundle = getArguments();
        assert bundle != null;
        String strContac = bundle.getString("contact");
        ContactFull contactFull = gson.fromJson(strContac, ContactFull.class);
        loadContact(contactFull);
        setOnClick();
    }

    private void serilize() {
        gson = new Gson();
        updateContactPresent = new UpdateContactPresent();
    }

    private void initUI(View view) {
        tvCancel = view.findViewById(R.id.tv_updatecontact_cancel);

        edtFirstName = view.findViewById(R.id.et_update_first_name);
        edtLastName = view.findViewById(R.id.et_update_last_name);
        edtCompany = view.findViewById(R.id.et_update_company);
        edtNote = view.findViewById(R.id.edt_update_note);

        lnAddPhone = view.findViewById(R.id.layout_phone);
        lnAddEmail = view.findViewById(R.id.layout_email);
        lnAddNickname = view.findViewById(R.id.layout_nickname);
        lnAddURL = view.findViewById(R.id.layout_url);
        lnAddAddress = view.findViewById(R.id.layout_address);
        lnAddDoB = view.findViewById(R.id.layout_dob);
        lnAddSocial = view.findViewById(R.id.layout_social);
        lnAddMessage = view.findViewById(R.id.layout_message);

        ivAddPhone = view.findViewById(R.id.add_btn_addphone);
        ivAddEmail = view.findViewById(R.id.add_btn_addemail);
        ivAddNickname = view.findViewById(R.id.add_btn_addnickname);
        ivAddURL = view.findViewById(R.id.add_btn_addurl);
        ivAddAddress = view.findViewById(R.id.add_btn_addaddress);
        ivAddDoB = view.findViewById(R.id.add_btn_adddob);
        ivAddSocial = view.findViewById(R.id.add_btn_addsocial);
        ivAddMessage = view.findViewById(R.id.add_btn_addmessage);
    }

    private void loadContact(ContactFull contactFull) {
        edtFirstName.setText(contactFull.contact.getFirstName());
        edtLastName.setText(contactFull.contact.getLastName());
        edtCompany.setText(contactFull.contact.getCompany());
        edtNote.setText(contactFull.contact.getNote());

        loadPhoneNumber(lnAddPhone, contactFull.phones);
        loadEmail(lnAddEmail, contactFull.emails);
        loadNickName(lnAddNickname, contactFull.nickNames);
        loadURL(lnAddURL, contactFull.urls);
        loadAddress(lnAddAddress, contactFull.addresses);
        loadDoB(lnAddDoB, contactFull.dobs);
        loadSocial(lnAddSocial, contactFull.socials);
        loadMessage(lnAddMessage, contactFull.messages);
    }

    private void setOnClick() {
        ivAddPhone.setOnClickListener(addPhone -> {
            List<String> typesPhone = Arrays.asList("di động", "nhà", "công ty", "trường học", "iPhone", "Apple Watch", "chính", "fax nhà riêng", "fax công ty", "máy nhắn tin", "khác");
            addAttribute(lnAddPhone, R.string.contact_phone, typesPhone);
        });

        ivAddEmail.setOnClickListener(addEmail -> {
            List<String> typesEmail = Arrays.asList("di động", "nhà", "công ty", "trường học", "iPhone", "Apple Watch", "chính", "fax nhà riêng", "fax công ty", "máy nhắn tin", "khác");
            addAttribute(lnAddEmail, R.string.contact_email, typesEmail);
        });
    }

    private void loadPhoneNumber(LinearLayout container, List<PhoneNumber> listPhone) {
        for (PhoneNumber phoneNumber : listPhone) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View childItem = layoutInflater.inflate(R.layout.child_item, null);
            TextView tvType = childItem.findViewById(R.id.child_type);
            tvType.setText(phoneNumber.getType());
            EditText edtInput = childItem.findViewById(R.id.et_input_att);
            edtInput.setText(phoneNumber.getNumber());
            ImageView ivDelete = childItem.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> container.removeView(childItem));
            container.addView(childItem);
        }
    }

    private void loadEmail(LinearLayout container, List<Email> listEmail) {
        for (Email email : listEmail) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View childItem = layoutInflater.inflate(R.layout.child_item, null);
            TextView tvType = childItem.findViewById(R.id.child_type);
            tvType.setText(email.getType());
            EditText edtInput = childItem.findViewById(R.id.et_input_att);
            edtInput.setText(email.getValue());
            ImageView ivDelete = childItem.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> container.removeView(childItem));
            container.addView(childItem);
        }
    }

    private void loadNickName(LinearLayout container, List<NickName> listNickName) {
        for (NickName nickName : listNickName) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View childItem = layoutInflater.inflate(R.layout.child_item, null);
            TextView tvType = childItem.findViewById(R.id.child_type);
            tvType.setText(nickName.getType());
            EditText edtInput = childItem.findViewById(R.id.et_input_att);
            edtInput.setText(nickName.getValue());
            ImageView ivDelete = childItem.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> container.removeView(childItem));
            container.addView(childItem);
        }
    }

    private void loadURL(LinearLayout container, List<URL> listURL) {
        for (URL url : listURL) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View childItem = layoutInflater.inflate(R.layout.child_item, null);
            TextView tvType = childItem.findViewById(R.id.child_type);
            tvType.setText(url.getType());
            EditText edtInput = childItem.findViewById(R.id.et_input_att);
            edtInput.setText(url.getValue());
            ImageView ivDelete = childItem.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> container.removeView(childItem));
            container.addView(childItem);
        }
    }

    private void loadAddress(LinearLayout container, List<Address> listAddress) {
        for (Address address : listAddress) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View childItem = layoutInflater.inflate(R.layout.address, null);
            TextView tvType = childItem.findViewById(R.id.child_type);
            tvType.setText(address.getType());
            EditText edtProvince = childItem.findViewById(R.id.et_input_province);
            edtProvince.setText(address.getProvince());
            EditText edtDistrict = childItem.findViewById(R.id.et_input_district);
            edtDistrict.setText(address.getDistrict());
            EditText edtWard = childItem.findViewById(R.id.et_input_ward);
            edtWard.setText(address.getWard());
            EditText edtDetail = childItem.findViewById(R.id.et_input_detail);
            edtDetail.setText(address.getDetail());
            ImageView ivDelete = childItem.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> container.removeView(childItem));
            container.addView(childItem);
        }
    }

    private void loadDoB(LinearLayout container, List<DOB> listDoB) {
        for (DOB doB : listDoB) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View childItem = layoutInflater.inflate(R.layout.child_item, null);
            TextView tvType = childItem.findViewById(R.id.child_type);
            tvType.setText(doB.getType());
            EditText edtInput = childItem.findViewById(R.id.et_input_att);
            edtInput.setText(doB.getValue());
            ImageView ivDelete = childItem.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> container.removeView(childItem));
            container.addView(childItem);
        }
    }

    private void loadSocial(LinearLayout container, List<Social> listSocial) {
        for (Social social : listSocial) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View childItem = layoutInflater.inflate(R.layout.child_item, null);
            TextView tvType = childItem.findViewById(R.id.child_type);
            tvType.setText(social.getType());
            EditText edtInput = childItem.findViewById(R.id.et_input_att);
            edtInput.setText(social.getValue());
            ImageView ivDelete = childItem.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> container.removeView(childItem));
            container.addView(childItem);
        }
    }

    private void loadMessage(LinearLayout container, List<Message> listMessage) {
        for (Message message : listMessage) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View childItem = layoutInflater.inflate(R.layout.child_item, null);
            TextView tvType = childItem.findViewById(R.id.child_type);
            tvType.setText(message.getType());
            EditText edtInput = childItem.findViewById(R.id.et_input_att);
            edtInput.setText(message.getValue());
            ImageView ivDelete = childItem.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> container.removeView(childItem));
            container.addView(childItem);
        }
    }

    private void addAttribute(LinearLayout container, int idHint, List<String> type) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View childItem = layoutInflater.inflate(R.layout.child_item, null);
        TextView tvType = childItem.findViewById(R.id.child_type);
        tvType.setText(type.get(0));
        EditText edtInput = childItem.findViewById(R.id.et_input_att);
        edtInput.setHint(getString(idHint));
        ImageView ivDelete = childItem.findViewById(R.id.add_btn_delete_phone);
        ivDelete.setOnClickListener(delete -> container.removeView(childItem));
        LinearLayout lnSelectTyp = childItem.findViewById(R.id.ln_select_type);
        lnSelectTyp.setOnClickListener(show -> showDialog(type, tvType::setText));
        container.addView(childItem);

        edtInput.requestFocus();
        InputMethodManager inputMethod = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethod.showSoftInput(edtInput, InputMethodManager.SHOW_IMPLICIT);
    }

    private void showDialog(List<String> types, AddContactFragment.TypeSelectionListener typeSelectionListener) {
        AddAttDialogFragment dialog = new AddAttDialogFragment(types, typeSelectionListener::onTypeSelected);
        dialog.show(getParentFragmentManager(), "AddAttDialog");
    }

}
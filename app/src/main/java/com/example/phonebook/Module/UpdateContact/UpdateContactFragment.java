package com.example.phonebook.Module.UpdateContact;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import com.example.phonebook.Model.Contact;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import kotlin.Triple;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateContactFragment extends Fragment implements UpdateContactContract.View{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Gson gson;
    TextView tvCancel, tvComplete;
    EditText edtFirstName, edtLastName, edtCompany, edtNote;
    LinearLayout lnAddPhone, lnAddEmail, lnAddNickname, lnAddURL, lnAddAddress, lnAddDoB, lnAddSocial, lnAddMessage;
    ImageView ivAddPhone, ivAddEmail, ivAddNickname, ivAddURL, ivAddAddress, ivAddDoB, ivAddSocial, ivAddMessage;
    UpdateContactPresent updateContactPresent;
    String strContac;
    ContactFull contactFull;


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
        serilize(getContext());
        initUI(view);

        tvCancel.setOnClickListener(cancel -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        Bundle bundle = getArguments();
        assert bundle != null;
        strContac = bundle.getString("contact");
        contactFull = gson.fromJson(strContac, ContactFull.class);
        loadContact(contactFull);
        setOnClick();
    }

    private void serilize(Context context) {
        gson = new Gson();
        updateContactPresent = new UpdateContactPresent(context, this);
    }

    private void initUI(View view) {
        tvCancel = view.findViewById(R.id.tv_updatecontact_cancel);
        tvComplete = view.findViewById(R.id.tv_updatecontact_complete);

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

        ivAddNickname.setOnClickListener(addNickName -> {
            List<String> typesNickname = Arrays.asList("mẹ", "cha", "cha/mẹ", "anh(em)", "con trai", "con gái", "con", "bạn bè", "chồng (vợ)", "bạn đời", "trợ lý", "người quản lý", "khác");
            addAttribute(lnAddNickname, R.string.contact_nickname, typesNickname);
        });

        ivAddURL.setOnClickListener(addURL -> {
            List<String> typesURL = Arrays.asList("trang chủ", "nhà", "công ty", "trường học", "khác");
            addAttribute(lnAddURL, R.string.contact_url, typesURL);
        });

        ivAddAddress.setOnClickListener(addAddresss -> {
            List<String> typesAddress = Arrays.asList("trang chủ", "nhà", "công ty", "trường học", "khác");
            addAddress(lnAddAddress, typesAddress);
        });

        ivAddDoB.setOnClickListener(addDoB -> {
            List<String> typesDoB = Arrays.asList("lịch mặc định", "lịch trung quốc", "lịch do thái", "lịch hồi giáo");
            addDoB(lnAddDoB, typesDoB);
        });

        ivAddSocial.setOnClickListener(addSocial -> {
            List<String> typesSocial = Arrays.asList("Meet", "Teams", "YouTobe", "MoMo", "X", "Shopee", "TikTok", "Messenger",
                    "Facebook", "Zalo", "Gmail", "Locket", "Duolingo", "Pinterest", "Outlook", "Threads", "Instagram", "Discord",
                    "Twitch", "Linkedln", "Myspace", "Sina Weibo");
            addAttribute(lnAddSocial, R.string.contact_social, typesSocial);
        });

        ivAddMessage.setOnClickListener(addMessage -> {
            List<String> typesMessage = Arrays.asList("Meet", "Teams", "YouTobe", "MoMo", "X", "Shopee", "TikTok", "Messenger",
                    "Facebook", "Zalo", "Gmail", "Locket", "Duolingo", "Pinterest", "Outlook", "Threads", "Instagram", "Discord", "Twitch", "Linkedln", "Myspace", "Sina Weibo");
            addAttribute(lnAddMessage, R.string.contact_message, typesMessage);
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

    private void addAddress(LinearLayout container, List<String> types) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        @SuppressLint("InflateParams") View childItem = inflater.inflate(R.layout.address, null);
        TextView tvType = childItem.findViewById(R.id.child_type);
        List<String> typesAddress = Arrays.asList("trang chủ", "nhà", "công ty", "trường học", "khác");
        tvType.setText(typesAddress.get(0));
        ImageView ivDelete = childItem.findViewById(R.id.add_btn_delete_phone);
        ivDelete.setOnClickListener(delete -> container.removeView(childItem));
        LinearLayout lnSelectTyp = childItem.findViewById(R.id.ln_select_type);
        lnSelectTyp.setOnClickListener(show -> showDialog(typesAddress, tvType::setText));
        container.addView(childItem);

    }

    private void addDoB(LinearLayout container, List<String> types) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        @SuppressLint("InflateParams") View childItem = inflater.inflate(R.layout.att_dob, null);
        TextView tvType = childItem.findViewById(R.id.child_type);
        tvType.setText(types.get(0));
        TextView edtInput = childItem.findViewById(R.id.et_input_att);
        edtInput.setHint(getString(R.string.contact_dob));
        ImageView ivDelete = childItem.findViewById(R.id.add_btn_delete_phone);
        ivDelete.setOnClickListener(delete -> container.removeView(childItem));
        LinearLayout lnSelectTyp = childItem.findViewById(R.id.ln_select_type);
        lnSelectTyp.setOnClickListener(show -> showDialog(types, tvType::setText));

        edtInput.setOnClickListener(selectDOB -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        edtInput.setText(date);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });
        container.addView(childItem);
    }

    private void showDialog(List<String> types, AddContactFragment.TypeSelectionListener typeSelectionListener) {
        AddAttDialogFragment dialog = new AddAttDialogFragment(types, typeSelectionListener::onTypeSelected);
        dialog.show(getParentFragmentManager(), "AddAttDialog");
    }

    private List<Triple<String, String, Long>> collectAttribute(LinearLayout container, long idContact) {
        List<Triple<String, String, Long>> listData = new ArrayList<>();
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            String value = ((EditText) child.findViewById(R.id.et_input_att)).getText().toString().trim();
            String type = ((TextView) child.findViewById(R.id.child_type)).getText().toString().trim();
            listData.add(new Triple<>(value, type, idContact));
        }
        return listData;
    }

    private List<Triple<String, String, Long>> collectAddressAttributes(long idContact) {
        List<Triple<String, String, Long>> listData = new ArrayList<>();
        for (int i = 0; i < lnAddAddress.getChildCount(); i++) {
            View child = lnAddAddress.getChildAt(i);
            String strProvince = ((EditText) child.findViewById(R.id.et_input_province)).getText().toString().trim();
            String strDistrict = ((EditText) child.findViewById(R.id.et_input_district)).getText().toString().trim();
            String strWard = ((EditText) child.findViewById(R.id.et_input_ward)).getText().toString().trim();
            String strDetail = ((EditText) child.findViewById(R.id.et_input_detail)).getText().toString().trim();
            String type = ((TextView) child.findViewById(R.id.child_type)).getText().toString().trim();

            String fullAddress = strDetail + "\n" + strWard + "\n" + strDistrict + "\n" + strProvince;
            listData.add(new Triple<>(type, fullAddress, idContact));
        }
        return listData;
    }

    private void updateContact() {
        //phone number
        List<Triple<String, String, Long>> listPhone = collectAttribute(lnAddPhone, contactFull.contact.getId());

        //email
        List<Triple<String, String, Long>> listEmail = collectAttribute(lnAddEmail, contactFull.contact.getId());

        //nickname
        List<Triple<String, String, Long>> listNickname = collectAttribute(lnAddNickname, contactFull.contact.getId());

        //url
        List<Triple<String, String, Long>> listURL = collectAttribute(lnAddURL, contactFull.contact.getId());

        //address
        List<Triple<String, String, Long>> listAddress = collectAddressAttributes(contactFull.contact.getId());

        //DoB
        List<Triple<String, String, Long>> listDoB = collectAttribute(lnAddDoB, contactFull.contact.getId());

        //social
        List<Triple<String, String, Long>> listSocial = collectAttribute(lnAddSocial, contactFull.contact.getId());

        //message
        List<Triple<String, String, Long>> listMessage = collectAttribute(lnAddMessage, contactFull.contact.getId());

        String firstName = edtFirstName.getText().toString().trim();
        String lastName = edtLastName.getText().toString().trim();
        String company = edtCompany.getText().toString().trim();
        String note = edtNote.getText().toString().trim();
        Contact contact = new Contact(company, firstName, lastName, note);
        contact.setId(contactFull.contact.getId());
    }

    @Override
    public void updateContactSuccess() {

    }

    @Override
    public void updateContactFail() {

    }
}
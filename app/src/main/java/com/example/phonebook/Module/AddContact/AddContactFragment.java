package com.example.phonebook.Module.AddContact;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phonebook.Fragment.AddAttDialogFragment;
import com.example.phonebook.Model.Address;
import com.example.phonebook.Model.Contact;
import com.example.phonebook.Model.DOB;
import com.example.phonebook.Model.Email;
import com.example.phonebook.Model.Message;
import com.example.phonebook.Model.NickName;
import com.example.phonebook.Model.PhoneNumber;
import com.example.phonebook.Model.Social;
import com.example.phonebook.Model.URL;
import com.example.phonebook.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddContactFragment extends Fragment implements AddContactContract.View {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LinearLayout containerPhone, containerEmail, containerNickname, containerURL, containerAddress, containerDoB, containerSocial, containerMessage;
    ImageView ivAddPhone, ivAddEmail, ivAddNickname, ivAddURL, ivAddAddress, ivAddDoB, ivAddSocial, ivAddMessage;
    TextView tvAddCanel, tvAddComplete;
    EditText edtFirstName, edtLastName, edtCompany, edtNote;
    AddContactPresent addContactPresent;
    String typeDefault;

    public interface TypeSelectionListener {
        void onTypeSelected(String type);
    }

    TypeSelectionListener typeSelectionListener;

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
        initView(view);
        register();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isEnable = !edtFirstName.getText().toString().isEmpty() || !edtLastName.getText().toString().isEmpty() || !edtCompany.getText().toString().isEmpty();
                tvAddComplete.setTextColor(isEnable ? getResources().getColor(R.color.complete) : getResources().getColor(R.color.empty));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        edtFirstName.addTextChangedListener(textWatcher);
        edtLastName.addTextChangedListener(textWatcher);
        edtCompany.addTextChangedListener(textWatcher);

        tvAddCanel.setOnClickListener(cancel -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
        });

        ivAddPhone.setOnClickListener(addPhone -> {
            List<String> typesPhone = Arrays.asList("di động", "nhà", "công ty", "trường học", "iPhone", "Apple Watch", "chính", "fax nhà riêng", "fax công ty", "máy nhắn tin", "khác");
            addAttribute(containerPhone, R.string.contact_phone, typesPhone);
        });

        ivAddEmail.setOnClickListener(addEmail -> {
            List<String> typesEmail = Arrays.asList("di động", "nhà", "công ty", "trường học", "iPhone", "Apple Watch", "chính", "fax nhà riêng", "fax công ty", "máy nhắn tin", "khác");
            addAttribute(containerEmail, R.string.contact_email, typesEmail);
        });

        ivAddNickname.setOnClickListener(addNickname -> {
            List<String> typesNickname = Arrays.asList("mẹ", "cha", "cha/mẹ", "anh(em)", "con trai", "con gái", "con", "bạn bè", "chồng (vợ)", "bạn đời", "trợ lý", "người quản lý", "khác");
            addAttribute(containerNickname, R.string.contact_nickname, typesNickname);
        });

        ivAddURL.setOnClickListener(addURL -> {
            List<String> typesURL = Arrays.asList("trang chủ", "nhà", "công ty", "trường học", "khác");
            addAttribute(containerURL, R.string.contact_url, typesURL);
        });

        ivAddAddress.setOnClickListener(addAddress -> {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View childItem = inflater.inflate(R.layout.address, null);
            TextView tvType = childItem.findViewById(R.id.child_type);
            List<String> typesAddress = Arrays.asList("trang chủ", "nhà", "công ty", "trường học", "khác");
            typeDefault = typesAddress.get(0);
            tvType.setText(typeDefault);
            ImageView ivDelete = childItem.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> containerAddress.removeView(childItem));
            LinearLayout lnSelectTyp = childItem.findViewById(R.id.ln_select_type);
            lnSelectTyp.setOnClickListener(show -> showDialog(typesAddress, new TypeSelectionListener() {
                @Override
                public void onTypeSelected(String type) {
                    tvType.setText(type);
                }
            }));
            containerAddress.addView(childItem);
        });

        ivAddDoB.setOnClickListener(addDoB -> {
            List<String> typesDoB = Arrays.asList("lịch mặc định", "lịch trung quốc", "lịch do thái", "lịch hồi giáo");
            addAttribute(containerDoB, R.string.contact_dob, typesDoB);
        });

        ivAddSocial.setOnClickListener(addSocial -> {
            List<String> typesSocial = Arrays.asList("Meet", "Teams", "YouTobe", "MoMo", "X", "Shopee", "TikTok", "Messenger",
                    "Facebook", "Zalo", "Gmail", "Locket", "Duolingo", "Pinterest", "Outlook", "Threads", "Instagram", "Discord", "Twitch", "Linkedln", "Myspace", "Sina Weibo");
            addAttribute(containerSocial, R.string.contact_social, typesSocial);
        });

        ivAddMessage.setOnClickListener(addMessage -> {
            List<String> typesMessage = Arrays.asList("Meet", "Teams", "YouTobe", "MoMo", "X", "Shopee", "TikTok", "Messenger",
                    "Facebook", "Zalo", "Gmail", "Locket", "Duolingo", "Pinterest", "Outlook", "Threads", "Instagram", "Discord", "Twitch", "Linkedln", "Myspace", "Sina Weibo");
            addAttribute(containerMessage, R.string.contact_message, typesMessage);
        });

        tvAddComplete.setOnClickListener(complete -> {
            String firstName = edtFirstName.getText().toString();
            String lastName = edtLastName.getText().toString();
            String company = edtCompany.getText().toString();
            String note = edtNote.getText().toString();
            if (!firstName.isEmpty() || !lastName.isEmpty() || !company.isEmpty() || !note.isEmpty()) {
                Contact contact = new Contact(company, firstName, lastName, note);
                addContact(contact);
            }
        });
    }

    private void addContact(Contact contact) {
        addContactPresent.insertContact(contact, id -> Executors.newSingleThreadScheduledExecutor().execute(() -> {
            try {
                // Insert Phone Number
                List<PhoneNumber> listPhone = getListType(id, containerPhone, PhoneNumber.class);
                addContactPresent.insertPhoneNumber(listPhone);

                // Insert Email
                List<Email> listEmail = getListType(id, containerEmail, Email.class);
                addContactPresent.insertEmail(listEmail);

                // Insert NickName
                List<NickName> listNickname = getListType(id, containerNickname, NickName.class);
                addContactPresent.insertNickName(listNickname);

                // Insert URL
                List<URL> listURL = getListType(id, containerURL, URL.class);
                addContactPresent.insertURL(listURL);

                // Insert Address
                List<Address> listAddress = new ArrayList<>();
                for (int i = 0; i < containerAddress.getChildCount(); i++) {
                    View child = containerAddress.getChildAt(i);
                    String strProvince = ((EditText) child.findViewById(R.id.et_input_province)).getText().toString().trim();
                    String strDistrict = ((EditText) child.findViewById(R.id.et_input_district)).getText().toString().trim();
                    String strWard = ((EditText) child.findViewById(R.id.et_input_ward)).getText().toString().trim();
                    String strDetail = ((EditText) child.findViewById(R.id.et_input_detail)).getText().toString().trim();
                    String type = ((TextView) child.findViewById(R.id.child_type)).getText().toString().trim();
                    try {
                        Address address = new Address(id, strDetail, strDistrict, strProvince, strWard);
                        listAddress.add(address);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                addContactPresent.insertAddress(listAddress);

                // Insert DoB
                List<DOB> listDoB = getListType(id, containerDoB, DOB.class);
                addContactPresent.insertDoB(listDoB);

                // Insert Social
                List<Social> listSocial = getListType(id, containerSocial, Social.class);
                addContactPresent.insertSocial(listSocial);

                // Insert Message
                List<Message> listMessage = getListType(id, containerMessage, Message.class);
                addContactPresent.insertMessage(listMessage);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
    }

    private void initView(View view) {
        tvAddCanel = view.findViewById(R.id.tv_addcontact_cancel);
        edtFirstName = view.findViewById(R.id.et_add_first_name);
        edtLastName = view.findViewById(R.id.et_add_last_name);
        edtCompany = view.findViewById(R.id.et_add_company);
        edtNote = view.findViewById(R.id.tv_add_note);

        containerPhone = view.findViewById(R.id.layout_phone);
        ivAddPhone = view.findViewById(R.id.add_btn_addphone);
        containerEmail = view.findViewById(R.id.layout_email);
        ivAddEmail = view.findViewById(R.id.add_btn_addemail);
        containerNickname = view.findViewById(R.id.layout_nickname);
        ivAddNickname = view.findViewById(R.id.add_btn_addnickname);
        containerURL = view.findViewById(R.id.layout_url);
        ivAddURL = view.findViewById(R.id.add_btn_addurl);
        containerAddress = view.findViewById(R.id.layout_address);
        ivAddAddress = view.findViewById(R.id.add_btn_addaddress);
        containerDoB = view.findViewById(R.id.layout_dob);
        ivAddDoB = view.findViewById(R.id.add_btn_adddob);
        containerSocial = view.findViewById(R.id.layout_social);
        ivAddSocial = view.findViewById(R.id.add_btn_addsocial);
        containerMessage = view.findViewById(R.id.layout_message);
        ivAddMessage = view.findViewById(R.id.add_btn_addmessage);
        tvAddComplete = view.findViewById(R.id.tv_addcontact_complete);
    }

    private void register() {
        addContactPresent = new AddContactPresent(this, requireContext());
    }

    private void addAttribute(LinearLayout container, int idHint, List<String> types) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View childItem = inflater.inflate(R.layout.child_item, null);
        TextView tvType = childItem.findViewById(R.id.child_type);
        if (!types.isEmpty()) {
            typeDefault = types.get(0);
            tvType.setText(typeDefault);
        }
        EditText edtInput = childItem.findViewById(R.id.et_input_att);
        edtInput.setHint(getString(idHint));
        ImageView ivDelete = childItem.findViewById(R.id.add_btn_delete_phone);
        ivDelete.setOnClickListener(delete -> container.removeView(childItem));
        LinearLayout lnSelectTyp = childItem.findViewById(R.id.ln_select_type);
        lnSelectTyp.setOnClickListener(show -> {
            showDialog(types, new TypeSelectionListener() {
                @Override
                public void onTypeSelected(String type) {
                    tvType.setText(type);
                }
            });
        });
        container.addView(childItem);
    }

    private void showDialog(List<String> types, TypeSelectionListener  typeSelectionListener) {
        AddAttDialogFragment dialog = new AddAttDialogFragment(types, typeSelectionListener::onTypeSelected);
        dialog.show(getParentFragmentManager(), "AddAttDialog");
    }
    private <T> List<T> getListType(long id,LinearLayout container, Class<T> clazz) {
        List<T> listType = new ArrayList<>();
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            String value = ((EditText) child.findViewById(R.id.et_input_att)).getText().toString().trim();
            String type = ((TextView) child.findViewById(R.id.child_type)).getText().toString().trim();
            try {
                Constructor<T> constructor = clazz.getConstructor(long.class, String.class, String.class);
                listType.add(constructor.newInstance(id, value, type));
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException |
                     java.lang.InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
        return listType;
    }

    @Override
    public void addContactSuccess() {
        requireActivity().runOnUiThread(() -> {
            Toast.makeText(getContext(), getString(R.string.add_success), Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    @Override
    public void addContactFail(String error) {
        Toast.makeText(getContext(), getString(R.string.add_error), Toast.LENGTH_SHORT).show();
    }
}
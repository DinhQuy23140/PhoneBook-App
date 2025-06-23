package com.example.phonebook.AddContact;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.phonebook.Repository.ContactRepository;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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

        tvAddCanel.setOnClickListener(cancel -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
        });

        ivAddPhone.setOnClickListener(addPhone -> {
            addAttribute(containerPhone, R.string.contact_phone);
        });

        ivAddEmail.setOnClickListener(addEmail -> {
            addAttribute(containerEmail, R.string.contact_email);
        });

        ivAddNickname.setOnClickListener(addNickname -> {
            addAttribute(containerNickname, R.string.contact_nickname);
        });

        ivAddURL.setOnClickListener(addURL -> {
            addAttribute(containerURL, R.string.contact_url);
        });

        ivAddAddress.setOnClickListener(addAddress -> {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View childItem = inflater.inflate(R.layout.address, null);
            TextView tvType = childItem.findViewById(R.id.child_type);
            ImageView ivDelete = childItem.findViewById(R.id.add_btn_delete_phone);
            ivDelete.setOnClickListener(delete -> {
                containerAddress.removeView(childItem);
            });
            LinearLayout lnSelectTyp = childItem.findViewById(R.id.ln_select_type);
            lnSelectTyp.setOnClickListener(show -> showDialog());
            containerAddress.addView(childItem);
        });

        ivAddDoB.setOnClickListener(addDoB -> {
            addAttribute(containerDoB, R.string.contact_dob);
        });

        ivAddSocial.setOnClickListener(addSocial -> {
            addAttribute(containerSocial, R.string.contact_social);
        });

        containerMessage = view.findViewById(R.id.layout_message);
        ivAddMessage = view.findViewById(R.id.add_btn_addmessage);
        ivAddMessage.setOnClickListener(addMessage -> {
            addAttribute(containerMessage, R.string.contact_message);
        });

        tvAddComplete.setOnClickListener(complete -> {
            String firstName = edtFirstName.getText().toString();
            String lastName = edtLastName.getText().toString();
            String company = "";
            String note = edtNote.getText().toString();
            Contact contact = new Contact(note, firstName, lastName, note);
            addContactPresent.insertContact(contact, new ContactRepository.CallBackInsert() {
                @Override
                public void onSuccess(long id) {
                    Executors.newSingleThreadScheduledExecutor().execute(() -> {
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
                            List<Address> listAddress = getListType(id, containerAddress, Address.class);
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

//                            requireActivity().runOnUiThread(() -> {
//                                Toast.makeText(getContext(), getString(R.string.add_success), Toast.LENGTH_SHORT).show();
//                                requireActivity().getSupportFragmentManager().popBackStack();
//                            });
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            });
//            contactRepository.insertContact(contact, id -> {
//                Executors.newSingleThreadScheduledExecutor().execute(() -> {
//                    try {
//                        // Insert Phone Number
//                        List<PhoneNumber> listPhone = getListType(id, containerPhone, PhoneNumber.class);
//                        contactRepository.insertPhoneNumber(listPhone);
//
//                        // Insert Email
//                        List<Email> listEmail = getListType(id, containerEmail, Email.class);
//                        Log.d("Insert", "Email size: " + listEmail.size());
//                        contactRepository.insertEmail(listEmail);
//
//                        // Insert NickName
//                        List<NickName> listNickname = getListType(id, containerNickname, NickName.class);
//                        Log.d("Insert", "NickName size: " + listNickname.size());
//                        contactRepository.insertNickName(listNickname);
//
//                        // Insert URL
//                        List<URL> listURL = getListType(id, containerURL, URL.class);
//                        Log.d("Insert", "URL size: " + listURL.size());
//                        contactRepository.insertURL(listURL);
//
//                        // Insert Address
////                        List<Address> listAddress = getListType(id, containerAddress, Address.class);
////                        Log.d("Insert", "Address size: " + listAddress.size());
////                        contactRepository.insertAddress(listAddress);
//
//                        // Insert DOB
//                        List<DOB> listDoB = getListType(id, containerDoB, DOB.class);
//                        Log.d("Insert", "DOB size: " + listDoB.size());
//                        contactRepository.insertDoB(listDoB);
//
//                        // Insert Social
//                        List<Social> listSocial = getListType(id, containerSocial, Social.class);
//                        Log.d("Insert", "Social size: " + listSocial.size());
//                        contactRepository.insertSocial(listSocial);
//
//                        // Insert Message
//                        List<Message> listMessage = getListType(id, containerMessage, Message.class);
//                        Log.d("Insert", "Message size: " + listMessage.size());
//                        contactRepository.insertMessage(listMessage);
//
//                        // UI Thread
//                        requireActivity().runOnUiThread(() -> {
//                            Toast.makeText(getContext(), getString(R.string.add_success), Toast.LENGTH_SHORT).show();
//                            requireActivity().getSupportFragmentManager().popBackStack();
//                        });
//                    } catch (Exception e) {
//                        Log.e("InsertError", "Lỗi khi insert các dữ liệu phụ: ", e);
//                    }
//                });
//            });


        });
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
        tvAddComplete = view.findViewById(R.id.tv_addcontact_complete);
    }

    private void register() {
        addContactPresent = new AddContactPresent(this, requireContext());
    }

    private void addAttribute(LinearLayout container, int idHint) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View childItem = inflater.inflate(R.layout.child_item, null);
        TextView tvType = childItem.findViewById(R.id.child_type);
        EditText edtInput = childItem.findViewById(R.id.et_input_att);
        edtInput.setHint(getString(idHint));
        ImageView ivDelete = childItem.findViewById(R.id.add_btn_delete_phone);
        ivDelete.setOnClickListener(delete -> {
            container.removeView(childItem);
        });
        LinearLayout lnSelectTyp = childItem.findViewById(R.id.ln_select_type);
        lnSelectTyp.setOnClickListener(show -> showDialog());
        container.addView(childItem);
    }

    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialog = inflater.inflate(R.layout.diaglog_add_att, null );
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialog);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
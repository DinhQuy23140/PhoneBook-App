package com.example.phonebook.Module.Person;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phonebook.Adapter.ContactAdapter;
import com.example.phonebook.Model.ContactFull;
import com.example.phonebook.Module.AddContact.AddContactFragment;
import com.example.phonebook.Model.Contact;
import com.example.phonebook.Module.ContactDetail.ContactDetailFragment;
import com.example.phonebook.R;
import com.example.phonebook.Repository.ContactRepository;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView ivPerson;
    RecyclerView rvContact;
    ContactAdapter contactAdapter;
    ContactRepository contactRepository;
    TextView tvCountContact;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PersonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonFragment newInstance(String param1, String param2) {
        PersonFragment fragment = new PersonFragment();
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
        return inflater.inflate(R.layout.fragment_person, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvCountContact = view.findViewById(R.id.tv_count_contact);
        rvContact = view.findViewById(R.id.rv_contact);
        rvContact.setLayoutManager(new LinearLayoutManager(requireContext()));

        contactRepository = new ContactRepository(requireContext());

        contactRepository.getAllContacts(new ContactRepository.CallBack() {
            @Override
            public void onSuccess(List<ContactFull> result) {
                requireActivity().runOnUiThread(() -> {
//                List<ContactFull> safeResult = (result == null) ? new ArrayList<>() : result;
                    if (result != null) {
                        contactAdapter = new ContactAdapter(getContext(), result, position -> {
                            ContactFull contactFull = result.get(position);
                            ContactDetailFragment contactDetailFragment = new ContactDetailFragment();
                            Bundle bundle = new Bundle();
                            Gson gson = new Gson();
                            String strContactFull = gson.toJson(contactFull);
                            bundle.putString("contact", strContactFull);
                            contactDetailFragment.setArguments(bundle);
                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_container, contactDetailFragment);
                            fragmentTransaction.addToBackStack(null); // nên thêm dòng này để quay lại được
                            fragmentTransaction.commit();
                        });
                        rvContact.setAdapter(contactAdapter); // LUÔN set adapter NGAY tại đây
                        tvCountContact.setText(String.format("%s %s", result.size(), getString(R.string.contact_title)));
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        ivPerson = view.findViewById(R.id.iv_add_contact);
        ivPerson.setOnClickListener(addContact -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, new AddContactFragment());
            fragmentTransaction.addToBackStack(null); // nên thêm dòng này để quay lại được
            fragmentTransaction.commit();
        });
    }

}
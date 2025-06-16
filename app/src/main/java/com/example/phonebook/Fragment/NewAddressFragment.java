package com.example.phonebook.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.phonebook.Model.District;
import com.example.phonebook.Model.Province;
import com.example.phonebook.Model.Ward;
import com.example.phonebook.R;
import com.example.phonebook.Repository.ContactRepository;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewAddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewAddressFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<Province> provinces;
    List<District> districts;
    List<Ward> wards;
    AutoCompleteTextView atvProvince, atvDistrict, atvWard;
    ArrayAdapter adapterProvince, adapterDistrict, adapterWard;
    String nameProvince = "", nameDistrict = "", nameWard = "";
    ContactRepository contactRepository;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewAddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewAddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewAddressFragment newInstance(String param1, String param2) {
        NewAddressFragment fragment = new NewAddressFragment();
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
        return inflater.inflate(R.layout.fragment_new_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contactRepository = new ContactRepository(getContext());
        atvProvince = view.findViewById(R.id.av_province);
        contactRepository.getAddress();
        contactRepository.getListProvince().observe(getViewLifecycleOwner(), listProvince -> {
            Toast.makeText(getContext(), "List Province: " + listProvince.size(), Toast.LENGTH_SHORT).show();
            if (!listProvince.isEmpty()) {
                provinces = listProvince;
                adapterProvince = new ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, provinces);
                atvProvince.setAdapter(adapterProvince);
            }
        });

        atvProvince.setOnItemClickListener((parent, view1, position, id) -> {
            Province province = (Province) parent.getItemAtPosition(position);
            nameProvince = province.getName();
            districts = province.getDistricts();
            adapterDistrict = new ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, districts);
            atvDistrict.setAdapter(adapterDistrict);
        });

        atvDistrict = view.findViewById(R.id.av_district);
        atvDistrict.setOnItemClickListener((parent, view2, position, id) -> {
            District district = (District) parent.getItemAtPosition(position);
            nameDistrict = district.getName();
            wards = district.getWards();
            adapterWard = new ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, wards);
            atvWard.setAdapter(adapterWard);
        });

        atvWard = view.findViewById(R.id.av_ward);
        atvWard.setOnItemClickListener((parent, view3, position, id) -> {
            Ward ward = (Ward) parent.getItemAtPosition(position);
            nameWard = ward.getName();
        });
    }
}
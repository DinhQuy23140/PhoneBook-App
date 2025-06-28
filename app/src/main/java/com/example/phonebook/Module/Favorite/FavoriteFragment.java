package com.example.phonebook.Module.Favorite;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phonebook.Adapter.ContactAdapter;
import com.example.phonebook.Interface.OnclickListener;
import com.example.phonebook.Model.ContactFull;
import com.example.phonebook.Module.ContactDetail.ContactDetailFragment;
import com.example.phonebook.R;
import com.google.gson.Gson;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment implements FavoriteContract.View{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FavoritePresent favoritePresent;
    RecyclerView rvFavorite;
    ContactAdapter contactAdapter;
    List<ContactFull> listFavoriteContact;
    TextView tvEmptyFavorite;
    Gson gson;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
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
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view, getContext());
        serilize(getContext());
        favoritePresent.getFavorite();
    }

    private void initUI(View view, Context context) {
        tvEmptyFavorite = view.findViewById(R.id.tv_empty_favorite);
        rvFavorite = view.findViewById(R.id.rv_favorite);
        rvFavorite.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    private void serilize(Context context) {
        favoritePresent = new FavoritePresent(context, this);
        gson = new Gson();
    }

    @Override
    public void showFavorite(List<ContactFull> contactFulls, Context context) {
        requireActivity().runOnUiThread(() -> {
            Log.d("FAVORITE_SHOW", "showFavorite size = " + contactFulls.size());
            contactAdapter = new ContactAdapter(context, contactFulls, position -> {
                ContactFull contactFull = contactFulls.get(position);
                ContactDetailFragment contactDetailFragment = new ContactDetailFragment();
                Bundle bundle = new Bundle();
                String strContactFull = gson.toJson(contactFull);
                bundle.putString("contact", strContactFull);
                contactDetailFragment.setArguments(bundle);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, contactDetailFragment);
                fragmentTransaction.addToBackStack(null); // nên thêm dòng này để quay lại được
                fragmentTransaction.commit();
            });
            rvFavorite.setAdapter(contactAdapter);
        });
    }
}
package com.example.phonebook.Module.Favorite;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.phonebook.Model.ContactFull;
import com.example.phonebook.Repository.ContactRepository;

import java.util.List;

public class FavoritePresent implements FavoriteContract.Present{
    ContactRepository contactRepository;
    Context context;
    FavoriteContract.View view;

    public FavoritePresent(Context context, FavoriteContract.View view) {
        this.context = context;
        this.view = view;
        this.contactRepository = new ContactRepository(context);
    }

    @Override
    public void getFavorite() {
        Log.d("FAVORITE_DEBUG", "getFavorite() called");
        contactRepository.getFavoriteContacts(new ContactRepository.CallBack() {
            @Override
            public void onSuccess(List<ContactFull> result) {
                Log.d("FAVORITE_DATA", "Size: " + result.size()); // <- không thấy log này
                view.showFavorite(result, context);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("FAVORITE_ERROR", "Error: " + e.getMessage());
            }
        });
    }

}

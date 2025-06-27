package com.example.phonebook.Module.Favorite;

import android.content.Context;

import com.example.phonebook.Model.ContactFull;

import java.util.List;

public interface FavoriteContract {
    interface View {
        void showFavorite(List<ContactFull> contactFulls, Context context);
    }

    interface Present {
        void getFavorite();
    }
}

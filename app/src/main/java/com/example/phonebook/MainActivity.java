package com.example.phonebook;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.phonebook.Module.Favorite.FavoriteFragment;
import com.example.phonebook.Module.KeyBoard.KeyBoardFragment;
import com.example.phonebook.Module.Person.PersonFragment;
import com.example.phonebook.Module.Recent.RecentFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    FavoriteFragment favoriteFragment = new FavoriteFragment();
    RecentFragment recentFragment = new RecentFragment();
    KeyBoardFragment keyBoardFragment = new KeyBoardFragment();
    PersonFragment personFragment = new PersonFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        View layout = findViewById(R.id.frame_container);
        ViewCompat.setOnApplyWindowInsetsListener(layout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.bottom_star) {
                replaceFragment(favoriteFragment);
                return true;
            } else if (id == R.id.bottom_recent) {
                replaceFragment(recentFragment);
                return true;
            } else if (id == R.id.bottom_keyboard) {
                replaceFragment(keyBoardFragment);
                return true;
            } else if (id == R.id.bottom_person) {
                replaceFragment(personFragment);
                return true;
            }
            return false;
        });

        replaceFragment(keyBoardFragment);
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }
}
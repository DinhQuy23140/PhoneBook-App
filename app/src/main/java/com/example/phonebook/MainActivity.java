package com.example.phonebook;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.phonebook.Favorite.FavoriteFragment;
import com.example.phonebook.KeyBoard.KeyBoardFragment;
import com.example.phonebook.Person.PersonFragment;
import com.example.phonebook.Recent.RecentFragment;
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
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
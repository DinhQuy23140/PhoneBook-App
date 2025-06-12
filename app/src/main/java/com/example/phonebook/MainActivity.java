package com.example.phonebook;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.phonebook.Fragment.FavoriteFragment;
import com.example.phonebook.Fragment.KeyBoardFragment;
import com.example.phonebook.Fragment.PersonFragment;
import com.example.phonebook.Fragment.RecentFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.Key;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
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
            if (item.getItemId() == R.id.bottom_star) {
                replaceFragment(new FavoriteFragment());
                return true;
            } else if (item.getItemId() == R.id.bottom_recent) {
                replaceFragment(new RecentFragment());
                return true;
            } else if (item.getItemId() == R.id.bottom_keyboard) {
                replaceFragment(new KeyBoardFragment());
                return true;
            } else if (item.getItemId() == R.id.bottom_person) {
                replaceFragment(new PersonFragment());
                return true;
            }
            return false;
        });
        replaceFragment(new KeyBoardFragment());
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
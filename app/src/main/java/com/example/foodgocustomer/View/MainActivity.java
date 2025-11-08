package com.example.foodgocustomer.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.foodgocustomer.R;
import com.example.foodgocustomer.Util.TokenManager;
import com.example.foodgocustomer.View.Activity.LoginActivity;
import com.example.foodgocustomer.View.Fragment.FragmentIndex;
import com.example.foodgocustomer.View.Fragment.FragmentHistory;
import com.example.foodgocustomer.View.Fragment.FragmentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        TokenManager tokenManager = TokenManager.getInstance(this);
        String token = tokenManager.getToken();
        if (token == null || token.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        loadFragment(new FragmentIndex());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_food) {
                fragment = new FragmentIndex();
            } else if (id == R.id.nav_orders) {
                fragment = new FragmentHistory();
            } else if (id == R.id.nav_profile) {
                fragment = new FragmentProfile();
            }

            if (fragment != null) {
                loadFragment(fragment);
                return true;
            }

            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}

package com.example.foodgocustomer.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.foodgocustomer.R;
import com.example.foodgocustomer.View.Fragment.FragmentIndex;
import com.example.foodgocustomer.View.Fragment.FragmentHistory;
import com.example.foodgocustomer.View.Fragment.FragmentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Load fragment mặc định (Món ăn)
        loadFragment(new FragmentIndex());

        // Xử lý sự kiện khi chọn item
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

package com.example.foodgocustomer.View.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodgocustomer.R;
import com.example.foodgocustomer.databinding.ActivityLoginBinding;
import com.example.foodgocustomer.network.ApiClient;
import com.example.foodgocustomer.network.FoodApi;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FoodApi foodApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        foodApi = ApiClient.getClient().create(FoodApi.class);

        binding.tvRegisterNow.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
        });


    }
}
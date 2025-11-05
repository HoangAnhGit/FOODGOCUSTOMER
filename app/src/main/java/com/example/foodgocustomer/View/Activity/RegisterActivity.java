package com.example.foodgocustomer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodgocustomer.Util.InputEffectHelper;
import com.example.foodgocustomer.View.MainActivity;
import com.example.foodgocustomer.ViewModel.LoginViewModel;
import com.example.foodgocustomer.ViewModel.RegisterViewModel;
import com.example.foodgocustomer.network.DTO.RegisterRequest;
import com.example.foodgocustomer.network.DTO.RegisterResponse;
import com.example.foodgocustomer.R;
import com.example.foodgocustomer.databinding.ActivityRegisterBinding;
import com.example.foodgocustomer.network.ApiClient;
import com.example.foodgocustomer.network.API.AuthApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerViewModel =new ViewModelProvider(this).get(RegisterViewModel.class);

        binding.tvLoginNow.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        setupInputFocusEffect();
        binding.btnRegister.setOnClickListener(v -> registerUser());
        observeLogin();
    }

    private void setupInputFocusEffect() {
        InputEffectHelper.applyFocusEffect(binding.edtName);
        InputEffectHelper.applyFocusEffect(binding.edtPhone);
        InputEffectHelper.applyFocusEffect(binding.edtEmail);
        InputEffectHelper.applyFocusEffect(binding.edtPassword);
        InputEffectHelper.applyFocusEffect(binding.edtPasswordAgain);
        InputEffectHelper.clearErrorOnTextChange(binding.edtName,binding.edtPhone,binding.edtPassword,binding.edtPasswordAgain,binding.edtEmail);
    }

    private void registerUser() {

        clearFocusAndHideKeyboard();

        String fullName = binding.edtName.getText().toString().trim();
        String phone = binding.edtPhone.getText().toString().trim();
        String email = binding.edtEmail.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();
        String confirm = binding.edtPasswordAgain.getText().toString().trim();

        if (fullName.isEmpty() || phone.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }
        registerViewModel.register(phone,password,confirm,fullName,email);

    }

    private void observeLogin() {
        registerViewModel.getRegisterResult().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showLoading(true);
                    break;

                case SUCCESS:
                    showLoading(false);
                    Toast.makeText(this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    break;

                case ERROR:
                    showLoading(false);
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    private void showLoading(boolean isLoading) {
        binding.includeLoading.getRoot().setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }


    private void clearFocusAndHideKeyboard() {
        binding.edtPhone.clearFocus();
        binding.edtPassword.clearFocus();
        binding.edtName.clearFocus();
        binding.edtPasswordAgain.clearFocus();
        binding.edtEmail.clearFocus();

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

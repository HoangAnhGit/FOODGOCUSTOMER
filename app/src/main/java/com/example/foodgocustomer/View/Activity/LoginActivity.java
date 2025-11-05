package com.example.foodgocustomer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodgocustomer.Util.InputEffectHelper;
import com.example.foodgocustomer.View.MainActivity;
import com.example.foodgocustomer.ViewModel.LoginViewModel;
import com.example.foodgocustomer.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.tvRegisterNow.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

        InputEffectHelper.applyFocusEffect(binding.edtPhone);
        InputEffectHelper.applyFocusEffect(binding.edtPassword);
        InputEffectHelper.clearErrorOnTextChange(binding.edtPhone, binding.edtPassword);

        binding.btnLogin.setOnClickListener(v -> {
            clearFocusAndHideKeyboard();
            String phone = binding.edtPhone.getText().toString().trim();
            String password = binding.edtPassword.getText().toString().trim();
            if (phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.login(phone, password);
        });

        observeLogin();

//        viewModel.getLoading().observe(this, isLoading ->
//                binding.includeLoading.getRoot().setVisibility(isLoading ? View.VISIBLE : View.GONE));
    }

    private void observeLogin() {
        viewModel.getLoginResult().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showLoading(true);
                    break;

                case SUCCESS:
                    showLoading(false);
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
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

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

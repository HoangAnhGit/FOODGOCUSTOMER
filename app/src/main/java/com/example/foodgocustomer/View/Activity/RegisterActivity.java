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

import com.example.foodgocustomer.network.DTO.RegisterRequest;
import com.example.foodgocustomer.network.DTO.ApiResponse;
import com.example.foodgocustomer.R;
import com.example.foodgocustomer.databinding.ActivityRegisterBinding;
import com.example.foodgocustomer.network.ApiClient;
import com.example.foodgocustomer.network.API.LoginApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvRegisterNow.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        setupInputFocusEffect();
        binding.btnRegister.setOnClickListener(v -> registerUser());
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

        showLoading(true);

        if (!password.equals(confirm)) {
            Toast.makeText(this, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gửi request
        RegisterRequest request = new RegisterRequest(phone, password, confirm, fullName, email);

        LoginApi apiService = ApiClient.getClient().create(LoginApi.class);
        apiService.registerCustomer(request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    try {
                        String err = response.errorBody().string();
                        Log.e("RegisterError", err);
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại: " + err, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Lỗi không xác định!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                showLoading(false);
                Toast.makeText(RegisterActivity.this, "Không thể kết nối máy chủ: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            binding.includeLoading.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.includeLoading.getRoot().setVisibility(View.GONE);
        }
    }

    private void setupInputFocusEffect() {
        View.OnFocusChangeListener focusListener = (v, hasFocus) -> {
            if (hasFocus) {
                v.setBackgroundResource(R.drawable.bg_input_focused);
            } else {
                v.setBackgroundResource(R.drawable.bg_input_login);
            }
        };

        binding.edtName.setOnFocusChangeListener(focusListener);
        binding.edtPhone.setOnFocusChangeListener(focusListener);
        binding.edtEmail.setOnFocusChangeListener(focusListener);
        binding.edtPassword.setOnFocusChangeListener(focusListener);
        binding.edtPasswordAgain.setOnFocusChangeListener(focusListener);

        TextWatcher clearErrorWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.edtName.setError(null);
                binding.edtPhone.setError(null);
                binding.edtEmail.setError(null);
                binding.edtPassword.setError(null);
                binding.edtPasswordAgain.setError(null);
            }
        };

        binding.edtName.addTextChangedListener(clearErrorWatcher);
        binding.edtPhone.addTextChangedListener(clearErrorWatcher);
        binding.edtEmail.addTextChangedListener(clearErrorWatcher);
        binding.edtPassword.addTextChangedListener(clearErrorWatcher);
        binding.edtPasswordAgain.addTextChangedListener(clearErrorWatcher);
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

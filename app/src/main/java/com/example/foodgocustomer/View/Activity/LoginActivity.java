package com.example.foodgocustomer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodgocustomer.R;
import com.example.foodgocustomer.View.MainActivity;
import com.example.foodgocustomer.databinding.ActivityLoginBinding;
import com.example.foodgocustomer.Model.LoginRequest;
import com.example.foodgocustomer.Model.LoginResponse;
import com.example.foodgocustomer.network.ApiClient;
import com.example.foodgocustomer.network.FoodApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // xử lý ngoại lệ đang nhập để báo lên người dùng

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

        // Xử lý nút "Đăng nhập"
        binding.btnLogin.setOnClickListener(v -> performLogin());

        setupInputFocusEffect();

    }

    private void performLogin() {

        clearFocusAndHideKeyboard();
        String phone = binding.edtPhone.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();

        if (phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoading(true);

        // Tạo request model
        LoginRequest request = new LoginRequest(phone, password);

        // Gọi API đăng nhập
        foodApi.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse res = response.body();

                    // Có thể lưu token nếu có trong response
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();


                     startActivity(new Intent(LoginActivity.this, MainActivity.class));
                     finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Sai số điện thoại hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                showLoading(false);
                Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
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
        // Đổi màu viền khi focus
        View.OnFocusChangeListener focusListener = (v, hasFocus) -> {
            if (hasFocus) {
                v.setBackgroundResource(R.drawable.bg_input_focused);
            } else {
                v.setBackgroundResource(R.drawable.bg_input_login);
            }
        };
        binding.edtPhone.setOnFocusChangeListener(focusListener);
        binding.edtPassword.setOnFocusChangeListener(focusListener);

        // Ẩn lỗi khi người dùng bắt đầu gõ
        TextWatcher clearErrorWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {}

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.edtPhone.setError(null);
                binding.edtPassword.setError(null);
            }
        };

        binding.edtPhone.addTextChangedListener(clearErrorWatcher);
        binding.edtPassword.addTextChangedListener(clearErrorWatcher);
    }

    private void setupLoginButton() {
        binding.btnLogin.setOnClickListener(v -> {
            String phone = binding.edtPhone.getText().toString().trim();
            String password = binding.edtPassword.getText().toString().trim();

            if (phone.isEmpty()) {
                binding.edtPhone.setError("Vui lòng nhập số điện thoại");
                binding.edtPhone.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                binding.edtPassword.setError("Vui lòng nhập mật khẩu");
                binding.edtPassword.requestFocus();
                return;
            }
        });
    }

    private void clearFocusAndHideKeyboard() {
        // Mất focus khỏi các ô nhập
        binding.edtPhone.clearFocus();
        binding.edtPassword.clearFocus();

        // Ẩn bàn phím
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

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

    //TODO: xử lý lưu session

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

        setupInputFocusEffect();

        binding.btnLogin.setOnClickListener(v -> performLogin());


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

        LoginRequest request = new LoginRequest(phone, password);

        foodApi.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                showLoading(false);

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse res = response.body();
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    String errorMessage = "Đăng nhập thất bại!";

                    try {
                        if (response.errorBody() != null) {
                            String errorJson = response.errorBody().string();
                            Log.e("LOGIN_ERROR_BODY", errorJson);

                            if (errorJson.contains("password") || errorJson.contains("phone")) {
                                errorMessage = "Sai số điện thoại hoặc mật khẩu!";
                            } else if (errorJson.contains("blocked")) {
                                errorMessage = "Tài khoản của bạn đã bị khóa!";
                            } else {
                                errorMessage = "Máy chủ từ chối yêu cầu, vui lòng thử lại sau!";
                            }
                        }
                    } catch (Exception e) {
                        Log.e("LOGIN_PARSE_ERROR", "Không đọc được lỗi từ server: " + e.getMessage());
                    }

                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();

                    Log.e("LOGIN_RESPONSE", "Code: " + response.code() + ", Message: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                showLoading(false);

                String message;
                if (t.getMessage() != null && t.getMessage().contains("timeout")) {
                    message = "Kết nối tới máy chủ quá lâu, vui lòng kiểm tra mạng!";
                } else if (t.getMessage() != null && t.getMessage().contains("failed to connect")) {
                    message = "Không thể kết nối đến máy chủ, vui lòng kiểm tra địa chỉ IP hoặc Internet!";
                } else {
                    message = "Lỗi không xác định: " + t.getMessage();
                }

                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                Log.e("LOGIN_FAILURE", "onFailure: ", t);
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
        binding.edtPhone.setOnFocusChangeListener(focusListener);
        binding.edtPassword.setOnFocusChangeListener(focusListener);

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

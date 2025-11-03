package com.example.foodgocustomer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodgocustomer.Model.RegisterRequest;
import com.example.foodgocustomer.Model.ApiResponse;
import com.example.foodgocustomer.databinding.ActivityRegisterBinding;
import com.example.foodgocustomer.network.ApiClient;
import com.example.foodgocustomer.network.FoodApi;

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

        // 👉 Chuyển sang màn hình Login
        binding.tvRegisterNow.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        // 👉 Xử lý nút Đăng ký
        binding.btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String fullName = binding.edtName.getText().toString().trim();
        String phone = binding.edtPhone.getText().toString().trim();
        String email = binding.edtEmail.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();
        String confirm = binding.edtPasswordAgain.getText().toString().trim();

        // Kiểm tra rỗng
        if (fullName.isEmpty() || phone.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoading(true);

        // Kiểm tra mật khẩu trùng khớp
        if (!password.equals(confirm)) {
            Toast.makeText(this, "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gửi request
        RegisterRequest request = new RegisterRequest(phone, password, confirm, fullName, email);

        FoodApi apiService = ApiClient.getClient().create(FoodApi.class);
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
            public void onFailure(Call<ApiResponse> call, Throwable t) {
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
}

package com.example.foodgocustomer.View.Activity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.foodgocustomer.R;
import com.example.foodgocustomer.ViewModel.ProfileViewModel;
import com.example.foodgocustomer.databinding.ActivityEditProflieBinding;
import com.example.foodgocustomer.network.DTO.UserProfileDto;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProflieBinding binding;
    private ProfileViewModel profileViewModel;
    private ProgressDialog progressDialog;
    private Uri selectedImageUri = null;

    private ActivityResultLauncher<String> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityEditProflieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.setCancelable(false);

        loadProfileData();

        binding.btnSaveProfile.setOnClickListener(v -> saveProfile());

        imagePickerLauncher = registerForActivityResult(

                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        selectedImageUri = uri;

                        binding.imgAvatar.setImageURI(selectedImageUri);

                    }
                }
        );

        binding.imgAvatar.setOnClickListener(v -> {

            imagePickerLauncher.launch("image/*");
        });
    }

    private void editAvatar(){

    }

    private void loadProfileData() {
        progressDialog.show();

        profileViewModel.getProfile().observe(this, result -> {
            switch (result.status) {
                case SUCCESS:
                    progressDialog.dismiss();
                    UserProfileDto user = result.data;
                    if (user != null) {
                        binding.edtFullname.setText(user.getFullName());
                        binding.edtPhone.setText(user.getPhoneNumber());
                        binding.edtEmail.setText(user.getEmail());


                        // Load avatar (nếu có)
                        if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
                            Glide.with(this)
                                    .load(user.getAvatarUrl())
                                    .placeholder(R.drawable.avartar)
                                    .into(binding.imgAvatar);
                        }
                    }
                    break;
                case ERROR:
                    progressDialog.dismiss();
                    Toast.makeText(this, "Lỗi khi tải thông tin", Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    progressDialog.show();
                    break;
            }
        });
    }

    private void saveProfile() {
        String name = binding.edtFullname.getText().toString().trim();
        String email = binding.edtEmail.getText().toString().trim();

        if (name.isEmpty()) {
            binding.edtFullname.setError("Không được để trống");
            return;
        }

        UserProfileDto update = new UserProfileDto(name,
                binding.edtPhone.getText().toString().trim(),
                email,
                null);

        progressDialog.show();
        profileViewModel.updateProfile(update).observe(this, result -> {
            switch (result.status) {
                case SUCCESS:
                    progressDialog.dismiss();
                    Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case ERROR:
                    progressDialog.dismiss();
                    Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    progressDialog.show();
                    break;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

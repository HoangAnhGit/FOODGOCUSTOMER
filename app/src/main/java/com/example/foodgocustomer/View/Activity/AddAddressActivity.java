package com.example.foodgocustomer.View.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodgocustomer.Repository.ProfileRepository;
import com.example.foodgocustomer.ViewModel.ProfileViewModel;
import com.example.foodgocustomer.databinding.ActivityAddAddressBinding;
import com.example.foodgocustomer.network.DTO.AddressDto;
import com.example.foodgocustomer.Util.Result;

public class AddAddressActivity extends AppCompatActivity {

    private ActivityAddAddressBinding binding;
    private ProfileViewModel addressViewModel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBack.setOnClickListener(view -> {finish();});

        addressViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(ProfileViewModel.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang thêm địa chỉ...");
        progressDialog.setCancelable(false);

        binding.imgBack.setOnClickListener(v -> finish());
        binding.btnSaveAddress.setOnClickListener(v -> handleSaveAddress());
    }

    private void handleSaveAddress() {
        String name = binding.edtName.getText().toString().trim();
        String street = binding.edtStreet.getText().toString().trim();
        String ward = binding.edtWard.getText().toString().trim();
        String district = binding.edtDistrict.getText().toString().trim();
        String city = binding.edtCity.getText().toString().trim();
        boolean isDefault = binding.switchDefault.isChecked();

        if (!validateInput(name, street, ward, district, city)) {
            return;
        }

        String fullAddress = String.format("%s, %s, %s, %s, %s",
                name, street, ward, district, city);

        AddressDto newAddress = new AddressDto(street, ward, district, city, fullAddress, isDefault);

        callAddAddressApi(newAddress);
    }


    private boolean validateInput(String name, String street, String ward, String district, String city) {
        if (name.isEmpty()) {
            binding.edtName.setError("Vui lòng nhập tên người nhận");
            return false;
        }
        if (street.isEmpty()) {
            binding.edtStreet.setError("Vui lòng nhập số nhà, tên đường");
            return false;
        }
        if (ward.isEmpty()) {
            binding.edtWard.setError("Vui lòng nhập Phường/Xã");
            return false;
        }
        if (district.isEmpty()) {
            binding.edtDistrict.setError("Vui lòng nhập Quận/Huyện");
            return false;
        }
        if (city.isEmpty()) {
            binding.edtCity.setError("Vui lòng nhập Tỉnh/Thành phố");
            return false;
        }
        return true;
    }

    private void callAddAddressApi(AddressDto addressDto) {
        progressDialog.show();

        addressViewModel.addAddress(addressDto).observe(this, result -> {
            switch (result.status) {
                case SUCCESS:
                    progressDialog.dismiss();
                    Toast.makeText(this, "Thêm địa chỉ thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case ERROR:
                    progressDialog.dismiss();
                    Toast.makeText(this, "Lỗi: " + result.message, Toast.LENGTH_LONG).show();
                    break;
                case LOADING:
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
package com.example.foodgocustomer.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodgocustomer.Model.Address;
import com.example.foodgocustomer.Util.Result;
import com.example.foodgocustomer.View.Adapter.AddressAdapter;
import com.example.foodgocustomer.ViewModel.ProfileViewModel;
import com.example.foodgocustomer.databinding.ActivityManagerAddressBinding;

import java.util.ArrayList;
import java.util.List;

public class ManagerAddressActivity extends AppCompatActivity implements AddressAdapter.OnAddressClickListener {

    private ActivityManagerAddressBinding binding;
    private ProfileViewModel addressViewModel;
    private AddressAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManagerAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addressViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        setupRecyclerView();

        setupClickListeners();

        // 4. Quan sát (Observe) dữ liệu từ ViewModel
        observeAddresses();
    }

    private void setupRecyclerView() {
        addressAdapter = new AddressAdapter(new ArrayList<>(), this);
        binding.rvAddresses.setLayoutManager(new LinearLayoutManager(this));
        binding.rvAddresses.setAdapter(addressAdapter);
    }

    private void setupClickListeners() {
        binding.imgBack.setOnClickListener(view -> finish());

        binding.btnAddAddress.setOnClickListener(view -> {
            startActivity(new Intent(ManagerAddressActivity.this, AddAddressActivity.class));
        });
    }

    private void observeAddresses() {
        addressViewModel.getAddresses().observe(this, result -> {
            if (result == null) return;

            switch (result.status) { // Sử dụng .status (vì nó là public final)
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.rvAddresses.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.rvAddresses.setVisibility(View.VISIBLE);


                    // result.data bây giờ là List<Address>
                    if (result.data != null && !result.data.isEmpty()) {
                        addressAdapter.updateData(result.data);
                    } else {
                        addressAdapter.updateData(new ArrayList<>());
                        Toast.makeText(this, "Bạn chưa có địa chỉ nào.", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.rvAddresses.setVisibility(View.GONE);
                    Toast.makeText(this, result.message, Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        observeAddresses();
    }

    @Override
    public void onAddressClick(Address address) {
        Toast.makeText(this, "Đã chọn: " + address.getCustomerName(), Toast.LENGTH_SHORT).show();
        // TODO: Xử lý logic khi chọn địa chỉ
    }
}
package com.example.foodgocustomer.View.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodgocustomer.View.Adapter.OrderItemAdapter;
import com.example.foodgocustomer.ViewModel.OrderDetailViewModel;
import com.example.foodgocustomer.databinding.ActivityDetailOrderBinding; // Đổi tên này nếu file XML của bạn tên khác
import com.example.foodgocustomer.network.DTO.AddressInfoDto;
import com.example.foodgocustomer.network.DTO.OrderSummaryDto;
import com.example.foodgocustomer.network.DTO.ResponseOrderDetailDto;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailOrderActivity extends AppCompatActivity {

    private ActivityDetailOrderBinding binding;
    private OrderDetailViewModel viewModel;
    private OrderItemAdapter itemAdapter;
    private int orderId = -1;
    private final DecimalFormat priceFormat = new DecimalFormat("#,###đ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        orderId = getIntent().getIntExtra("ORDER_ID", -1);
        if (orderId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy ID đơn hàng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        viewModel = new ViewModelProvider(this).get(OrderDetailViewModel.class);

        setupRecyclerView();

        binding.btnBack.setOnClickListener(v -> finish());

        loadAndObserveData();
    }

    private void setupRecyclerView() {
        itemAdapter = new OrderItemAdapter(new ArrayList<>());
        binding.rcvOrderItems.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvOrderItems.setAdapter(itemAdapter);
    }

    private void loadAndObserveData() {

        viewModel.fetchOrderDetail(orderId).observe(this, result -> {
            if (result == null) return;


            switch (result.status) {
                case LOADING:
                case SUCCESS:
                    if (result.data != null) {
                        bindDataToView(result.data);
                    }
                    break;
                case ERROR:
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show();
                    break;
            }
            viewModel.fetchOrderDetail(orderId).removeObservers(this);
        });
    }


    private void bindDataToView(ResponseOrderDetailDto data) {
        // Trạng thái đơn hàng
        binding.tvTimeEstimate.setText(data.getEstimatedDeliveryTime());
        binding.tvStatus.setText(data.getStatusText());

        // Thông tin chung
        binding.tvOrderCode.setText("Mã đơn: " + data.getOrderCode());
        if (data.getNote() != null && !data.getNote().isEmpty()) {
            binding.tvOrderNote.setText("Ghi chú: " + data.getNote());
            binding.tvOrderNote.setVisibility(View.VISIBLE);
        } else {
            binding.tvOrderNote.setVisibility(View.GONE);
        }

        // Thông tin tài xế
        if (data.getShipperInfo() != null) {
            String shipperInfo = "Tài xế: " + data.getShipperInfo().getFullName() + " - " + data.getShipperInfo().getPhoneNumber();
            binding.tvShipperInfo.setText(shipperInfo);
            binding.tvShipperInfo.setVisibility(View.VISIBLE);
        } else {
            binding.tvShipperInfo.setVisibility(View.GONE);
        }

        AddressInfoDto address = data.getAddressInfo();
        binding.tvFromStore.setText(address.getRestaurantName());
        binding.tvFromAddress.setText(address.getRestaurantAddress());
        binding.tvToAddress.setText(address.getDeliveryAddress());
        String userInfo = address.getCustomerName() + " - " + address.getCustomerPhone();
        binding.tvUsername.setText(userInfo);

        OrderSummaryDto summary = data.getSummary();

        itemAdapter.updateData(summary.getItems());

        binding.tvTotal.setText(priceFormat.format(summary.getSubtotal()));
        binding.tvGrandTotal.setText(priceFormat.format(summary.getGrandTotal()));
        binding.tvPaymentStatus.setText(summary.getPaymentStatusText().toUpperCase());


    }
}
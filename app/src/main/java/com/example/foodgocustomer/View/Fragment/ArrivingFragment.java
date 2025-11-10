package com.example.foodgocustomer.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodgocustomer.View.Activity.DetailOrderActivity;
import com.example.foodgocustomer.View.Adapter.OrderHistoryAdapter;
import com.example.foodgocustomer.ViewModel.ProfileViewModel;
import com.example.foodgocustomer.databinding.FragmentArrivingBinding;
import com.example.foodgocustomer.network.DTO.ItemOrderHistoryDto;

import java.util.ArrayList;
import java.util.List;

public class ArrivingFragment extends Fragment implements OrderHistoryAdapter.OnItemClickListener {

    // === TÊN TRẠNG THÁI CẦN LỌC ===
    // (Thay đổi "DELIVERING" nếu server của bạn dùng tên khác)
    private static final String STATUS_FILTER = "DELIVERING";

    private FragmentArrivingBinding binding;
    private ProfileViewModel profileViewModel;
    private OrderHistoryAdapter orderAdapter;
    private List<ItemOrderHistoryDto> orderList = new ArrayList<>();

    // Biến quản lý phân trang
    private int currentPage = 1;
    private int totalPages = 1;
    private boolean isLoading = false;
    private final int PAGE_SIZE = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentArrivingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Khởi tạo ViewModel
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        // 2. Cài đặt RecyclerView
        setupRecyclerView();

        // 3. Tải trang đầu tiên với trạng thái đã lọc
        loadArrivingOrders(currentPage);

        // 4. Cài đặt sự kiện cuộn
        setupScrollListener();
    }

    private void setupRecyclerView() {
        orderAdapter = new OrderHistoryAdapter(orderList, this);
        // Giả sử RecyclerView trong layout của bạn có id là rcvArriving
        binding.recyclerArriving.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerArriving.setAdapter(orderAdapter);
    }

    /**
     * Hàm gọi API để tải các đơn hàng đang giao
     */
    private void loadArrivingOrders(int page) {
        if (isLoading) return;
        isLoading = true;
        // Giả sử bạn có ProgressBar tên là progressBar
        binding.progressBar.setVisibility(View.VISIBLE);

        // Gọi ViewModel với trạng thái "DELIVERING"
        profileViewModel.getOrderHistory(page, PAGE_SIZE, STATUS_FILTER).observe(getViewLifecycleOwner(), result -> {
            if (result == null) {
                isLoading = false;
                binding.progressBar.setVisibility(View.GONE);
                return;
            }

            switch (result.status) {
                case LOADING:
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    isLoading = false;

                    if (result.data != null) {
                        this.currentPage = result.data.getPageNumber();
                        this.totalPages = result.data.getTotalPages();

                        if (page == 1) {
                            orderList.clear();
                        }

                        orderList.addAll(result.data.getData());
                        orderAdapter.notifyDataSetChanged();

                        // Hiển thị thông báo nếu danh sách rỗng
                        if (orderList.isEmpty()) {
                            binding.tvEmptyMessage.setVisibility(View.VISIBLE); // (Cần thêm TextView này)
                        } else {
                            binding.tvEmptyMessage.setVisibility(View.GONE);
                        }
                    }
                    break;
                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    isLoading = false;
                    Toast.makeText(getContext(), result.message, Toast.LENGTH_SHORT).show();
                    break;
            }
            // Hủy observe
            profileViewModel.getOrderHistory(page, PAGE_SIZE, STATUS_FILTER).removeObservers(getViewLifecycleOwner());
        });
    }

    /**
     * Xử lý sự kiện cuộn để tải thêm
     */
    private void setupScrollListener() {
        binding.recyclerArriving.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && (currentPage < totalPages) &&
                            (visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                            firstVisibleItemPosition >= 0) {

                        loadArrivingOrders(currentPage + 1);
                    }
                }
            }
        });
    }

    /**
     * Xử lý khi click vào một đơn hàng
     */
    @Override
    public void onItemClick(ItemOrderHistoryDto order) {
        Toast.makeText(getContext(), "Xem chi tiết đơn: " + order.getOrderId(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), DetailOrderActivity.class);
        intent.putExtra("ORDER_ID", order.getOrderId()); // (Giả sử ItemOrderHistoryDto có getOrderId())
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
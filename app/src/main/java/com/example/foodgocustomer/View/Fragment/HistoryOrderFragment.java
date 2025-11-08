package com.example.foodgocustomer.View.Fragment;

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

import com.example.foodgocustomer.View.Adapter.OrderHistoryAdapter;
import com.example.foodgocustomer.ViewModel.ProfileViewModel;
import com.example.foodgocustomer.databinding.FragmentHistoryOrderBinding;
import com.example.foodgocustomer.network.DTO.ItemOrderHistoryDto; // <-- Import model DTO

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment này implement OnItemClickListener của Adapter
 */
public class HistoryOrderFragment extends Fragment implements OrderHistoryAdapter.OnItemClickListener {

    private FragmentHistoryOrderBinding binding;
    private ProfileViewModel profileViewModel;
    private OrderHistoryAdapter orderAdapter;
    private final List<ItemOrderHistoryDto> orderList = new ArrayList<>();


    private int currentPage = 1;
    private int totalPages = 1;
    private boolean isLoading = false;
    private final int PAGE_SIZE = 10;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        setupRecyclerView();

        // 3. Tải dữ liệu trang đầu tiên
        // (Bạn có thể thay null bằng "COMPLETED" hoặc "CANCELLED" nếu muốn lọc)
        loadOrderHistory(currentPage, null);
        setupScrollListener();
    }


    private void setupRecyclerView() {
        orderAdapter = new OrderHistoryAdapter(orderList, this);
        binding.rcvOrderHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvOrderHistory.setAdapter(orderAdapter);
    }


    private void loadOrderHistory(int page, String status) {
        if (isLoading) return;
        isLoading = true;


        profileViewModel.getOrderHistory(page, PAGE_SIZE, status).observe(getViewLifecycleOwner(), result -> {
            if (result == null) {
                isLoading = false;

                return;
            }

            switch (result.status) {
                case LOADING:
                    break;
                case SUCCESS:
                    isLoading = false;
                    Toast.makeText(binding.getRoot().getContext(), "OKE",Toast.LENGTH_SHORT).show();
                    if (result.data != null) {
                        this.currentPage = result.data.getPageNumber();
                        this.totalPages = result.data.getTotalPages();


                        if (page == 1) {
                            orderList.clear();
                        }

                        orderList.addAll(result.data.getData());
                        orderAdapter.notifyDataSetChanged();
                    }
                    break;
                case ERROR:
                    isLoading = false;
                    Toast.makeText(getContext(), result.message, Toast.LENGTH_SHORT).show();
                    break;
            }
            profileViewModel.getOrderHistory(page, PAGE_SIZE, status).removeObservers(getViewLifecycleOwner());
        });
    }


    private void setupScrollListener() {
        binding.rcvOrderHistory.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        loadOrderHistory(currentPage + 1, null); // (Hoặc status bạn đang lọc)
                    }
                }
            }
        });
    }


    @Override
    public void onItemClick(ItemOrderHistoryDto order) {
        Toast.makeText(getContext(), "Bạn đã chọn đơn hàng: " + order.getOrderId(), Toast.LENGTH_SHORT).show();
        // TODO: Mở màn hình Chi tiết Đơn hàng
        // Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        // intent.putExtra("ORDER_ID", order.getOrderId());
        // startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
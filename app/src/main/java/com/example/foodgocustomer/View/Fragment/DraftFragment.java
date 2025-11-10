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
import com.example.foodgocustomer.databinding.FragmentDraftBinding;
import com.example.foodgocustomer.network.DTO.ItemOrderHistoryDto;

import java.util.ArrayList;
import java.util.List;

public class DraftFragment extends Fragment implements OrderHistoryAdapter.OnItemClickListener {


    private static final String STATUS_FILTER = "CANCELLED";

    private FragmentDraftBinding binding;
    private ProfileViewModel profileViewModel;
    private OrderHistoryAdapter orderAdapter;
    private List<ItemOrderHistoryDto> orderList = new ArrayList<>();

    private int currentPage = 1;
    private int totalPages = 1;
    private boolean isLoading = false;
    private final int PAGE_SIZE = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDraftBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        setupRecyclerView();

        loadDraftOrders(currentPage);


        setupScrollListener();
    }

    private void setupRecyclerView() {
        orderAdapter = new OrderHistoryAdapter(orderList, this);
        binding.rcvDraft.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvDraft.setAdapter(orderAdapter);
    }

    private void loadDraftOrders(int page) {
        if (isLoading) return;
        isLoading = true;

        binding.progressBar.setVisibility(View.VISIBLE);

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


                        if (orderList.isEmpty()) {
                            binding.tvEmptyMessage.setVisibility(View.VISIBLE);
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

            profileViewModel.getOrderHistory(page, PAGE_SIZE, STATUS_FILTER).removeObservers(getViewLifecycleOwner());
        });
    }

    private void setupScrollListener() {
        binding.rcvDraft.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                        loadDraftOrders(currentPage + 1);
                    }
                }
            }
        });
    }


    @Override
    public void onItemClick(ItemOrderHistoryDto order) {
        Toast.makeText(getContext(), "Tiếp tục đơn hàng nháp: " + order.getOrderId(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), DetailOrderActivity.class);
        intent.putExtra("ORDER_ID", order.getOrderId());
        startActivity(intent);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
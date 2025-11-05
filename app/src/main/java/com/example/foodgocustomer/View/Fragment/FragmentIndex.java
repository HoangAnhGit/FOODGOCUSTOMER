package com.example.foodgocustomer.View.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodgocustomer.R;
import com.example.foodgocustomer.View.Adapter.BannerAdapter;
import com.example.foodgocustomer.View.Adapter.FoodAdapter;
import com.example.foodgocustomer.databinding.FragmentIndexBinding;
import com.example.foodgocustomer.network.API.FoodApi;
import com.example.foodgocustomer.network.DTO.FoodResponse;
import com.example.foodgocustomer.network.ApiClient;
import com.example.foodgocustomer.network.DTO.PagedResponse;


import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentIndex extends Fragment {

    private ViewPager2 bannerViewPager;
    private Handler handler = new Handler();
    private int currentPage = 0;
    private FragmentIndexBinding binding;

    private final List<Integer> bannerImages = Arrays.asList(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
    );

    private final Runnable bannerRunnable = new Runnable() {
        @Override
        public void run() {
            if (bannerImages.isEmpty()) return;
            currentPage = (currentPage + 1) % bannerImages.size();
            bannerViewPager.setCurrentItem(currentPage, true);
            handler.postDelayed(this, 3000);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentIndexBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        startViewPager();
        loadFoodList();

        return view;
    }

    private void startViewPager() {
        bannerViewPager = binding.bannerViewPager;
        BannerAdapter adapter = new BannerAdapter(getContext(), bannerImages);
        bannerViewPager.setAdapter(adapter);
        handler.postDelayed(bannerRunnable, 3000);
    }

    private void loadFoodList() {
        FoodApi dishApi = ApiClient.getClient().create(FoodApi.class);
        Call<PagedResponse<FoodResponse>> call = dishApi.getDishes(1, 10);

        call.enqueue(new Callback<PagedResponse<FoodResponse>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<FoodResponse>> call,
                                   @NonNull Response<PagedResponse<FoodResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PagedResponse<FoodResponse> pagedResponse = response.body();
                    List<FoodResponse> foods = pagedResponse.getData();
                    if (foods != null) {
                        setupRecyclerView(foods);
                    } else {
                        Toast.makeText(getContext(), "Không có dữ liệu!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Không tải được danh sách món ăn", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<FoodResponse>> call, @NonNull Throwable t) {
                Log.e("FoodAPI", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupRecyclerView(List<FoodResponse> foods) {
        FoodAdapter.OnFoodClickListener listener = new FoodAdapter.OnFoodClickListener() {
            @Override
            public void onAddClick(FoodResponse food) {
                Toast.makeText(getContext(), "Đã thêm " + food.getDishName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClick(FoodResponse food) {
                Toast.makeText(getContext(), "Chi tiết: " + food.getDishName(), Toast.LENGTH_SHORT).show();
            }
        };

        FoodAdapter foodAdapter = new FoodAdapter(getContext(), foods, listener);
        binding.rcvCategoryFood.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvCategoryFood.setAdapter(foodAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(bannerRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(bannerRunnable, 3000);
    }
}

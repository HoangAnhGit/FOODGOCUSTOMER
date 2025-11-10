package com.example.foodgocustomer.Repository;

import android.app.Application; // <-- Quan trọng
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodgocustomer.Util.Result;
import com.example.foodgocustomer.network.API.DishApi;
import com.example.foodgocustomer.network.ApiClient;
import com.example.foodgocustomer.network.DTO.DishResponse;
import com.example.foodgocustomer.network.DTO.PagedResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DishRepository {

    private final DishApi apiService;

    public DishRepository(Application application) {
        apiService = ApiClient.getClientAuth(application).create(DishApi.class);
    }

    public LiveData<Result<PagedResponse<DishResponse>>> getAllFood(int pageNumber, int pageSize) {
        MutableLiveData<Result<PagedResponse<DishResponse>>> data = new MutableLiveData<>();
        data.setValue(Result.loading());

        apiService.getAllDishes(pageNumber, pageSize).enqueue(new Callback<PagedResponse<DishResponse>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<DishResponse>> call, @NonNull Response<PagedResponse<DishResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(Result.success(response.body()));
                } else {
                    data.setValue(Result.error("Lỗi khi tải món ăn: " + response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<DishResponse>> call, @NonNull Throwable t) {
                data.setValue(Result.error("Lỗi mạng (Dish): " + t.getMessage()));
            }
        });

        return data;
    }
    public LiveData<Result<PagedResponse<DishResponse>>> getDishesByRestaurant(int restaurantId, int pageNumber, int pageSize) {
        MutableLiveData<Result<PagedResponse<DishResponse>>> data = new MutableLiveData<>();
        data.setValue(Result.loading());

        apiService.getDishesByRestaurant(restaurantId, pageNumber, pageSize).enqueue(new Callback<PagedResponse<DishResponse>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<DishResponse>> call, @NonNull Response<PagedResponse<DishResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(Result.success(response.body()));
                } else {
                    data.setValue(Result.error("Lỗi tải món ăn (Nhà hàng): " + response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<DishResponse>> call, @NonNull Throwable t) {
                data.setValue(Result.error("Lỗi mạng (Dish by Rest): " + t.getMessage()));
            }
        });

        return data;
    }
}
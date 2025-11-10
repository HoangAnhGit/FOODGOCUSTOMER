package com.example.foodgocustomer.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodgocustomer.Util.Result;
import com.example.foodgocustomer.network.API.RestaurantApi;
import com.example.foodgocustomer.network.ApiClient;
import com.example.foodgocustomer.network.DTO.ItemRestaurantDto;
import com.example.foodgocustomer.network.DTO.PagedResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantRepository {
    private final RestaurantApi apiService;

    public RestaurantRepository(Context context) {
        apiService = ApiClient.getClientAuth(context).create(RestaurantApi.class);
    }

    public LiveData<Result<PagedResponse<ItemRestaurantDto>>> getRestaurants(int pageNumber, int pageSize) {
        MutableLiveData<Result<PagedResponse<ItemRestaurantDto>>> data = new MutableLiveData<>();
        data.setValue(Result.loading());

        apiService.getRestaurants(pageNumber, pageSize).enqueue(new Callback<PagedResponse<ItemRestaurantDto>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<ItemRestaurantDto>> call, @NonNull Response<PagedResponse<ItemRestaurantDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(Result.success(response.body()));
                } else {
                    data.setValue(Result.error("Lỗi khi tải nhà hàng: " + response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<ItemRestaurantDto>> call, @NonNull Throwable t) {
                data.setValue(Result.error("Lỗi mạng: " + t.getMessage()));
            }
        });

        return data;
    }


}
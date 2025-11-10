package com.example.foodgocustomer.Repository;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodgocustomer.Util.Result;
import com.example.foodgocustomer.network.API.DishApi;
import com.example.foodgocustomer.network.ApiClient;
import com.example.foodgocustomer.network.DTO.RequestOrderDto;
import com.example.foodgocustomer.network.DTO.ResponseOrderDetailDto;
import com.example.foodgocustomer.network.DTO.ResponseOrderDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {
    private final DishApi apiService;

    public OrderRepository(Application application) {
        apiService = ApiClient.getClientAuth(application).create(DishApi.class);
    }

    public LiveData<Result<ResponseOrderDetailDto>> getOrderDetail(int orderId) {
        MutableLiveData<Result<ResponseOrderDetailDto>> data = new MutableLiveData<>();
        data.setValue(Result.loading());

        apiService.getOrderDetail(orderId).enqueue(new Callback<ResponseOrderDetailDto>() {
            @Override
            public void onResponse(@NonNull Call<ResponseOrderDetailDto> call, @NonNull Response<ResponseOrderDetailDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(Result.success(response.body()));
                } else {
                    data.setValue(Result.error("Lỗi tải chi tiết: " + response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseOrderDetailDto> call, @NonNull Throwable t) {
                data.setValue(Result.error("Lỗi mạng: " + t.getMessage()));
            }
        });
        return data;
    }

    public LiveData<Result<ResponseOrderDto>> createOrder(RequestOrderDto orderRequest) {
        MutableLiveData<Result<ResponseOrderDto>> data = new MutableLiveData<>();
        data.setValue(Result.loading());

        // apiService là client đã được xác thực (auth)
        apiService.createOrder(orderRequest).enqueue(new Callback<ResponseOrderDto>() {
            @Override
            public void onResponse(@NonNull Call<ResponseOrderDto> call, @NonNull Response<ResponseOrderDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(Result.success(response.body()));
                } else {
                    String errorMessage = "Lỗi: " + response.code();
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                        }
                    } catch (Exception e) {
                    }
                    data.setValue(Result.error(errorMessage));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseOrderDto> call, @NonNull Throwable t) {
                data.setValue(Result.error("Lỗi mạng (CreateOrder): " + t.getMessage()));
            }
        });
        return data;
    }
}
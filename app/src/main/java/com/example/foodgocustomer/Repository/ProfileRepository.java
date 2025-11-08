package com.example.foodgocustomer.Repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodgocustomer.Model.Address;
import com.example.foodgocustomer.Util.Result;
import com.example.foodgocustomer.network.API.CustomerApi;
import com.example.foodgocustomer.network.ApiClient;
import com.example.foodgocustomer.network.DTO.AddressDto;
import com.example.foodgocustomer.network.DTO.ApiResponse;
import com.example.foodgocustomer.network.DTO.UserProfileDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {
    private final CustomerApi customerApi;

    public ProfileRepository(Context context) {
        customerApi = ApiClient.getClientAuth(context).create(CustomerApi.class);
    }

    public LiveData<Result<UserProfileDto>> getProfile() {
        MutableLiveData<Result<UserProfileDto>> resultLiveData = new MutableLiveData<>();
        resultLiveData.setValue(Result.loading());

        customerApi.getProfile().enqueue(new Callback<UserProfileDto>() {
            @Override
            public void onResponse(@NonNull Call<UserProfileDto> call, @NonNull Response<UserProfileDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    resultLiveData.setValue(Result.success(response.body()));
                } else {
                    resultLiveData.setValue(Result.error("Không thể tải thông tin hồ sơ"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserProfileDto> call, @NonNull Throwable t) {
                resultLiveData.setValue(Result.error("Lỗi kết nối: " + t.getMessage()));
            }
        });

        return resultLiveData;
    }

    public LiveData<Result<ApiResponse>> editProfile(UserProfileDto userProfileDto) {
        MutableLiveData<Result<ApiResponse>> resultLiveData = new MutableLiveData<>();
        resultLiveData.setValue(Result.loading());

        customerApi.editProfile(userProfileDto).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    resultLiveData.setValue(Result.success(response.body()));
                } else {
                    resultLiveData.setValue(Result.error("Cập nhật thất bại"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                resultLiveData.setValue(Result.error("Lỗi kết nối: " + t.getMessage()));
            }
        });

        return resultLiveData;
    }

    public LiveData<Result<List<Address>>> getAddress(){
        MutableLiveData<Result<List<Address>>> result = new MutableLiveData<>();
        result.setValue(Result.loading());

        customerApi.getAddress().enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(@NonNull Call<List<Address>> call, @NonNull Response<List<Address>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    result.setValue(Result.success(response.body()));
                } else {
                    result.setValue(Result.error("Tải thất bại"));
                }
                if (!response.isSuccessful()) {
                    Log.e("ProfileRepository", "Lỗi API: " + response.code() + " - " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Address>> call, @NonNull Throwable throwable) {
                result.setValue(Result.error("Lỗi kết nối: " + throwable.getMessage()));
            }
        });
        return  result;
    }

    public LiveData<Result<ApiResponse>> addAddress(AddressDto addressDto){
        MutableLiveData<Result<ApiResponse>> result = new MutableLiveData<>();
        result.setValue(Result.loading());

        customerApi.addAddress(addressDto).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse>  response) {
                if (response.isSuccessful() && response.body() != null) {
                    result.setValue(Result.success(response.body()));
                } else {
                    result.setValue(Result.error("Thêm thất bại"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable throwable) {
                result.setValue(Result.error("Lỗi kết nối: " + throwable.getMessage()));
            }
        });
        return  result;
    }
}

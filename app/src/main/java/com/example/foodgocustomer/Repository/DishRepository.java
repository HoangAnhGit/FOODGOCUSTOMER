package com.example.foodgocustomer.Repository;

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
    private final DishApi dishApi;

    public DishRepository(){ dishApi = ApiClient.getClient().create(DishApi.class);}

    public LiveData<Result<PagedResponse<DishResponse>>> getAllFood(){
        MutableLiveData<Result<PagedResponse<DishResponse>>> getAllFood = new MutableLiveData<>();
        getAllFood.setValue(Result.loading());

        dishApi.getAllFoods(1,10).enqueue(new Callback<PagedResponse<DishResponse>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<DishResponse>> call, @NonNull Response<PagedResponse<DishResponse>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    getAllFood.setValue(Result.success(response.body()));
                }
                else {
                    getAllFood.setValue(Result.error("Load data error"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<DishResponse>> call, @NonNull Throwable throwable) {
                    getAllFood.setValue(Result.error("Lỗi : " + throwable.getMessage()));
            }
        });
        return getAllFood;
    }

    public  LiveData<Result<PagedResponse<DishResponse>>> getDishesByRestaurant(int restaurantId){
        MutableLiveData<Result<PagedResponse<DishResponse>>> getDishesByRestaurant = new MutableLiveData<>();
        getDishesByRestaurant.setValue(Result.loading());

        dishApi.getDishesByRestaurant(restaurantId, 1, 10).enqueue(new Callback<PagedResponse<DishResponse>>() {
            @Override
            public void onResponse(@NonNull Call<PagedResponse<DishResponse>> call, @NonNull Response<PagedResponse<DishResponse>> response) {
                if (response.isSuccessful() && response.body()!=null){
                    getDishesByRestaurant.setValue(Result.success(response.body()));
                }
                else{
                    getDishesByRestaurant.setValue(Result.error("Load data error"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<PagedResponse<DishResponse>> call, @NonNull Throwable throwable) {
                    getDishesByRestaurant.setValue(Result.error("Lỗi : "+ throwable.getMessage()));
            }
        });

        return getDishesByRestaurant;
    }
}

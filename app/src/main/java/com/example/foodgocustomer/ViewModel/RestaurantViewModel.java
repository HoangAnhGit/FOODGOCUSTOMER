package com.example.foodgocustomer.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodgocustomer.Repository.RestaurantRepository;
import com.example.foodgocustomer.Util.Result;
import com.example.foodgocustomer.network.DTO.ItemRestaurantDto;
import com.example.foodgocustomer.network.DTO.PagedResponse;

public class RestaurantViewModel extends AndroidViewModel {
    private final RestaurantRepository restaurantRepository;

    public RestaurantViewModel(@NonNull Application application) {
        super(application);
        restaurantRepository = new RestaurantRepository(application);
    }
    public LiveData<Result<PagedResponse<ItemRestaurantDto>>> fetchRestaurants(int pageNumber, int pageSize) {
        return restaurantRepository.getRestaurants(pageNumber, pageSize);
    }
}

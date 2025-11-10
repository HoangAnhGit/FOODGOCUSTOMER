package com.example.foodgocustomer.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foodgocustomer.Repository.DishRepository;
import com.example.foodgocustomer.Util.Result;
import com.example.foodgocustomer.network.DTO.DishResponse;
import com.example.foodgocustomer.network.DTO.PagedResponse;

public class DishViewModel extends AndroidViewModel {

    private final DishRepository dishRepository;

    public DishViewModel(@NonNull Application application) {
        super(application);
        dishRepository = new DishRepository(application);
    }

    public LiveData<Result<PagedResponse<DishResponse>>> getAllDish() {
        return dishRepository.getAllFood(1, 10);
    }

    public LiveData<Result<PagedResponse<DishResponse>>> fetchDishesByRestaurant(int restaurantId, int pageNumber, int pageSize) {
        return dishRepository.getDishesByRestaurant(restaurantId, pageNumber, pageSize);
    }
}
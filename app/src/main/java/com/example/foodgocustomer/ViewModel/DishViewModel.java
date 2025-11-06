package com.example.foodgocustomer.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodgocustomer.Repository.DishRepository;
import com.example.foodgocustomer.Util.Result;
import com.example.foodgocustomer.network.DTO.DishResponse;
import com.example.foodgocustomer.network.DTO.PagedResponse;

public class DishViewModel extends ViewModel {

    private final DishRepository dishRepository;
    MutableLiveData<Result<PagedResponse<DishResponse>>> resultMutableLiveData = new MutableLiveData<>();

    public DishViewModel (){ dishRepository = new DishRepository();}

    public LiveData<Result<PagedResponse<DishResponse>>> getDish(){
        return resultMutableLiveData;
    }

    public void getAllDish(){
        resultMutableLiveData.setValue(Result.loading());
        dishRepository.getAllFood().observeForever(resultMutableLiveData::setValue);
    }

    public void getDishByRestaurant(String restaurantId){
        resultMutableLiveData.setValue(Result.loading());
        dishRepository.getDishesByRestaurant(Integer.parseInt(restaurantId)).observeForever(resultMutableLiveData::setValue);
    }
}

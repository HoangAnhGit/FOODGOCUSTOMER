package com.example.foodgocustomer.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.foodgocustomer.Repository.OrderRepository;
import com.example.foodgocustomer.Util.Result;
import com.example.foodgocustomer.network.DTO.RequestOrderDto;
import com.example.foodgocustomer.network.DTO.ResponseOrderDetailDto;
import com.example.foodgocustomer.network.DTO.ResponseOrderDto;

public class OrderDetailViewModel extends AndroidViewModel {
    private final OrderRepository orderRepository;

    public OrderDetailViewModel(@NonNull Application application) {
        super(application);
        orderRepository = new OrderRepository(application);
    }

    public LiveData<Result<ResponseOrderDetailDto>> fetchOrderDetail(int orderId) {
        return orderRepository.getOrderDetail(orderId);
    }

    public LiveData<Result<ResponseOrderDto>> createOrder(RequestOrderDto orderRequest) {
        return orderRepository.createOrder(orderRequest);
    }
}
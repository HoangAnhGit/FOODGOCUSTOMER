package com.example.foodgocustomer.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodgocustomer.Repository.AuthRepository;
import com.example.foodgocustomer.Util.Result;
import com.example.foodgocustomer.network.DTO.LoginResponse;
import com.example.foodgocustomer.network.DTO.RegisterResponse;

public class RegisterViewModel extends ViewModel {
    private final AuthRepository repository;
    private final MutableLiveData<Result<RegisterResponse>> registerResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    public RegisterViewModel() {
        repository = new AuthRepository();
    }

    public LiveData<Result<RegisterResponse>> getRegisterResult() {
        return registerResult;
    }

    public void register(
            String phone,
            String password,
            String confirm,
            String fullName,
            String email
    ) {
        loading.setValue(true);
        registerResult.setValue(Result.loading());
        repository.register(phone, password, confirm, fullName, email).observeForever(registerResult::setValue);
    }



    public LiveData<Boolean> getLoading() {
        return loading;
    }
}

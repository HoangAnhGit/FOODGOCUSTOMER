package com.example.foodgocustomer.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodgocustomer.Repository.AuthRepository;
import com.example.foodgocustomer.Util.Result;
import com.example.foodgocustomer.network.DTO.LoginResponse;

public class LoginViewModel extends ViewModel {

    private final AuthRepository repository;
    private final MutableLiveData<Result<LoginResponse>> loginResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    public LoginViewModel() {
        repository = new AuthRepository();
    }

    public LiveData<Result<LoginResponse>> getLoginResult() {
        return loginResult;
    }

    public void login(String phone, String password) {
        loading.setValue(true);
        loginResult.setValue(Result.loading());
        repository.login(phone, password).observeForever(loginResult::setValue);
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }
}

package com.example.foodgocustomer.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodgocustomer.Model.Address;
import com.example.foodgocustomer.Repository.ProfileRepository;
import com.example.foodgocustomer.Util.Result;
import com.example.foodgocustomer.network.DTO.AddressDto;
import com.example.foodgocustomer.network.DTO.ApiResponse;
import com.example.foodgocustomer.network.DTO.UserProfileDto;

import java.io.Closeable;
import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private final ProfileRepository profileRepository;
    private MutableLiveData<Result<UserProfileDto>> userProfileResult;
    private MutableLiveData<Result<ApiResponse>> updateProfileResult;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new ProfileRepository(application);
    }

    public LiveData<Result<UserProfileDto>> getProfile() {
        if (userProfileResult == null) {
            userProfileResult = new MutableLiveData<>();
            loadProfile();
        }
        return userProfileResult;
    }

    private void loadProfile() {
        userProfileResult = (MutableLiveData<Result<UserProfileDto>>) profileRepository.getProfile();
    }

    public LiveData<Result<ApiResponse>> updateProfile(UserProfileDto userProfileDto) {
        updateProfileResult = (MutableLiveData<Result<ApiResponse>>) profileRepository.editProfile(userProfileDto);
        return updateProfileResult;
    }

    public LiveData<Result<List<Address>>> getAddresses() {
        return profileRepository.getAddress();
    }


    public LiveData<Result<ApiResponse>> addAddress(AddressDto addressDto) {
        return profileRepository.addAddress(addressDto);
    }
}

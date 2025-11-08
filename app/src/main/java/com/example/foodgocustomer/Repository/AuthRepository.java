    package com.example.foodgocustomer.Repository;

    import android.content.Context;

    import androidx.annotation.NonNull;
    import androidx.lifecycle.LiveData;
    import androidx.lifecycle.MutableLiveData;

    import com.example.foodgocustomer.Util.Result;
    import com.example.foodgocustomer.Util.TokenManager;
    import com.example.foodgocustomer.network.API.AuthApi;
    import com.example.foodgocustomer.network.ApiClient;
    import com.example.foodgocustomer.network.DTO.LoginRequest;
    import com.example.foodgocustomer.network.DTO.LoginResponse;
    import com.example.foodgocustomer.network.DTO.RegisterRequest;
    import com.example.foodgocustomer.network.DTO.RegisterResponse;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class AuthRepository {

        private final AuthApi authApi;

        public AuthRepository() {
            authApi = ApiClient.getClient().create(AuthApi.class);
        }



        public LiveData<Result<LoginResponse>> login(Context context, String phone, String password) {
            MutableLiveData<Result<LoginResponse>> resultLiveData = new MutableLiveData<>();
            LoginRequest request = new LoginRequest(phone, password);

            authApi.login(request).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        LoginResponse data = response.body();

                        TokenManager.getInstance(context).saveToken(data.getToken(), data.getUserType());

                        resultLiveData.setValue(Result.success(response.body()));
                    } else {
                        resultLiveData.setValue(Result.error("Sai số điện thoại hoặc mật khẩu"));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    resultLiveData.setValue(Result.error("Lỗi kết nối: " + t.getMessage()));
                }
            });

            return resultLiveData;
        }

        public LiveData<Result<RegisterResponse>> register(
                String phone,
                String password,
                String confirm,
                String fullName,
                String email
        ) {
            MutableLiveData<Result<RegisterResponse>> resultRegister = new MutableLiveData<>();
            resultRegister.setValue(Result.loading());

            RegisterRequest request = new RegisterRequest(phone, password, confirm, fullName, email);

            authApi.registerCustomer(request).enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        resultRegister.setValue(Result.success(response.body()));
                    } else {
                        resultRegister.setValue(Result.error("Đăng ký thất bại: " + response.message()));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {
                    resultRegister.setValue(Result.error("Lỗi kết nối: " + t.getMessage()));
                }
            });

            return resultRegister;
        }
    }

package id.kelompok04.doize.architecture.repository;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.kelompok04.doize.api.ApiUtils;
import id.kelompok04.doize.architecture.dao.UserDao;
import id.kelompok04.doize.architecture.viewmodel.UserViewModel;
import id.kelompok04.doize.model.response.ListUserResponse;
import id.kelompok04.doize.model.response.LoginResponse;
import id.kelompok04.doize.model.response.RequestResponse;
import id.kelompok04.doize.model.User;
import id.kelompok04.doize.model.response.UserResponse;
import id.kelompok04.doize.service.UserService;
import id.kelompok04.doize.ui.activity.LoginActivity;
import id.kelompok04.doize.ui.activity.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static final String TAG = "UserRepository";

    private static UserRepository INSTANCE;
    private UserService mUserService;
    private static UserDao mUserDao;

    private UserRepository(Context context) {
        mUserService = ApiUtils.getUserService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            mUserDao = new UserDao();
            INSTANCE = new UserRepository(context);
        }
    }

    public static UserRepository get() {
        return INSTANCE;
    }


    public LiveData<LoginResponse> register(User user) {
        MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();

        Log.d(TAG, "getUsers: Called");
        Call<LoginResponse> call = mUserService.register(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d(TAG, "onResponse: " + response.body());
                loginResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

        return loginResponseMutableLiveData;
    }


    public LiveData<LoginResponse> doLogin(String email, String password) {
        MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();

        Call<LoginResponse> call = mUserService.login(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                Log.d(TAG, "onResponse: " + loginResponse);
                loginResponseMutableLiveData.setValue(loginResponse);
                mUserDao.setUserLogin(loginResponse.getUser());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

        return loginResponseMutableLiveData;
    }

    public LiveData<User> getUserLogin(){
        return mUserDao.getUserLogin();
    }

    public LiveData<UserResponse> updateUser(User user) {
        MutableLiveData<UserResponse> userResponseMutableLiveData = new MutableLiveData<>();

        Log.d(TAG, "getUsers: Called");
        Call<UserResponse> call = mUserService.updateUser(mUserDao.getUserLogin().getValue().getIdUser(), user);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                Log.d(TAG, "onResponse: " + userResponse);
                userResponseMutableLiveData.setValue(userResponse);
                mUserDao.setUserLogin(userResponse.getData());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage() );
            }
        });

        return userResponseMutableLiveData;

    }

}

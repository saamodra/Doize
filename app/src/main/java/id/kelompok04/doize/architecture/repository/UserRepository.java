package id.kelompok04.doize.architecture.repository;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.List;

import id.kelompok04.doize.api.ApiUtils;
import id.kelompok04.doize.architecture.dao.UserDao;
import id.kelompok04.doize.architecture.viewmodel.UserViewModel;
import id.kelompok04.doize.model.response.ListUserResponse;
import id.kelompok04.doize.model.response.LoginResponse;
import id.kelompok04.doize.model.response.RequestResponse;
import id.kelompok04.doize.model.User;
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
            INSTANCE = new UserRepository(context);
        }
    }

    public static UserRepository get() {
        return INSTANCE;
    }


    public void getUsers() {
        Log.d(TAG, "getUsers: Called");
        Call<ListUserResponse> call = mUserService.getUsers();
        call.enqueue(new Callback<ListUserResponse>() {
            @Override
            public void onResponse(Call<ListUserResponse> call, Response<ListUserResponse> response) {
                Log.d(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<ListUserResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

//        return mUserDao.getUsers();
    }

    public void getUser() {
        Log.d(TAG, "getUser: Called");
        Call<User> call = mUserService.getUserById("1");
        call.enqueue(new Callback<User>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d(TAG, "onResponse: " + response.body().getFirstName());
                Log.d(TAG, "getUsers.onResponse: called");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onFailure API Call : " + t.getMessage());
            }
        });

//        return mUserDao.getUsers();
    }

    public void doLogin(String email, String password) {
        Log.d(TAG, "login: " + email + password);
        Call<LoginResponse> call = mUserService.login(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d(TAG, "onResponse: " + response.body());
//                LoginResponse loginResponse = response.body();
//                if (response.body().getStatus() == 200) {
////                    mUserViewModel.setLoginResponse(response.body());
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    intent.putExtra("firstname", response.body().getData().getUser().getFirstName());
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}

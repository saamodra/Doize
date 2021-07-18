package id.kelompok04.doize.architecture.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import id.kelompok04.doize.architecture.repository.UserRepository;
import id.kelompok04.doize.model.response.LoginResponse;

public class UserViewModel extends ViewModel {
    private static final String TAG = "UserViewModel";
    private UserRepository mUserRepository;

    private LoginResponse mLoginResponse;

    public UserViewModel() {
        mUserRepository = UserRepository.get();
        mLoginResponse = new LoginResponse();
    }

    public LiveData<LoginResponse> login(String email, String password) {
        return mUserRepository.doLogin(email, password);
    }

}

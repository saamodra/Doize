package id.kelompok04.doize.architecture.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import id.kelompok04.doize.architecture.repository.UserRepository;
import id.kelompok04.doize.model.User;
import id.kelompok04.doize.model.response.LoginResponse;
import id.kelompok04.doize.model.response.UserResponse;

public class UserViewModel extends ViewModel {
    private static final String TAG = "UserViewModel";
    private UserRepository mUserRepository;

    public UserViewModel() {
        mUserRepository = UserRepository.get();
    }

    public LiveData<LoginResponse> login(String email, String password) {
        return mUserRepository.doLogin(email, password);
    }

    public LiveData<LoginResponse> register(User user) {
        return mUserRepository.register(user);
    }

    public LiveData<User> getUserLogin(){
        return mUserRepository.getUserLogin();
    }

    public LiveData<UserResponse> updateProfile(User user) {
        return mUserRepository.updateUser(user);
    }
}

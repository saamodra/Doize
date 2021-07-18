package id.kelompok04.doize.architecture.dao;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import id.kelompok04.doize.model.response.LoginResponse;

public class UserDao {
    private MutableLiveData<LoginResponse> mLoginResponse = new MutableLiveData<>();

    public LiveData<LoginResponse> getLogin() {
        return mLoginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        mLoginResponse.setValue(loginResponse);
    }
}

package id.kelompok04.doize.architecture.dao;

import android.util.Log;

import id.kelompok04.doize.model.response.LoginResponse;

public class UserDao {
    private LoginResponse mLoginResponse = new LoginResponse();

    public LoginResponse getLoginResponse() {
        return mLoginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        mLoginResponse = loginResponse;
    }
}

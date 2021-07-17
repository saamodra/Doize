package id.kelompok04.doize.architecture.viewmodel;

import androidx.lifecycle.ViewModel;

import id.kelompok04.doize.architecture.repository.UserRepository;
import id.kelompok04.doize.model.response.LoginResponse;

public class UserViewModel extends ViewModel {
    private UserRepository mUserRepository;

    private LoginResponse mLoginResponse;

    public UserViewModel() {
        mUserRepository = UserRepository.get();
        mLoginResponse = new LoginResponse();
    }

    public void login(String email, String password) {
        mUserRepository.doLogin(email, password);
    }

    public LoginResponse getLoginResponse() {
        return mLoginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        mLoginResponse = loginResponse;
    }
}

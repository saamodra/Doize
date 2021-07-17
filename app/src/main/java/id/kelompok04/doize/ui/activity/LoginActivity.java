package id.kelompok04.doize.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import id.kelompok04.doize.R;
import id.kelompok04.doize.api.ApiUtils;
import id.kelompok04.doize.architecture.viewmodel.UserViewModel;
import id.kelompok04.doize.model.response.LoginResponse;
import id.kelompok04.doize.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private UserViewModel mUserViewModel;
    private UserService mUserService;
    private Button mLoginButton;
    private TextInputEditText mEmailTxt;
    private TextInputEditText mPasswordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setContentView(R.layout.activity_login);

        mLoginButton = findViewById(R.id.btnLogin);
        mEmailTxt = findViewById(R.id.txtEmail);
        mPasswordTxt = findViewById(R.id.txtPassword);
        mUserService = ApiUtils.getUserService();

        mLoginButton.setOnClickListener(v -> {
            Toast.makeText(this, mEmailTxt.getText().toString() + ' ' + mPasswordTxt.getText().toString(), Toast.LENGTH_SHORT).show();
            String email = mEmailTxt.getText().toString();
            String password = mPasswordTxt.getText().toString();

//            mUserViewModel.login(email, password);
            doLogin(email, password);
        });
    }


    public void doLogin(String email, String password) {
        Log.d(TAG, "login: " + email + password);
        Call<LoginResponse> call = mUserService.login(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d(TAG, "onResponse: " + response.body());
                LoginResponse loginResponse = response.body();
                if (loginResponse.getStatus() == 200) {
                    mUserViewModel.setLoginResponse(loginResponse);
                    String userLoginObject = gson.toJson(anime);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userLogin", (Parcelable) loginResponse.getData().getUser());
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


}
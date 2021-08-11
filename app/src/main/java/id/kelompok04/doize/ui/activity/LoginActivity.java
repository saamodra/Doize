package id.kelompok04.doize.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;

import id.kelompok04.doize.R;
import id.kelompok04.doize.api.ApiUtils;
import id.kelompok04.doize.architecture.viewmodel.UserViewModel;
import id.kelompok04.doize.helper.ValidationHelper;
import id.kelompok04.doize.model.response.LoginResponse;
import id.kelompok04.doize.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    SharedPreferences pref;

    private UserViewModel mUserViewModel;
    private UserService mUserService;
    private Button mLoginButton;
    private TextInputEditText mEmailTxt;
    private TextInputEditText mPasswordTxt;
    private TextInputLayout mEmailLayout;
    private TextInputLayout mPasswordLayout;
    private TextView mSignUpTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get Shared Preference
        pref = getSharedPreferences("user_pref", MODE_PRIVATE);

        if (pref.contains("email") && pref.contains("password")){

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        } else {

            mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

            mLoginButton = findViewById(R.id.btnLogin);
            mSignUpTv = findViewById(R.id.tvSignUp);
            mEmailTxt = findViewById(R.id.txtEmail);
            mPasswordTxt = findViewById(R.id.txtPassword);
            mEmailLayout = findViewById(R.id.txtLayoutEmail);
            mPasswordLayout = findViewById(R.id.txtLayoutPassword);

            mLoginButton.setOnClickListener(v -> {
                String email = mEmailTxt.getText().toString();
                String password = mPasswordTxt.getText().toString();

                if (validate(v)) {
                    ProgressDialog progressDialog = ProgressDialog.show(this, "Sign In", "Signing in...");
                    mUserViewModel.login(email, password).observe(this, new Observer<LoginResponse>() {
                        @Override
                        public void onChanged(LoginResponse loginResponse) {
                            if (loginResponse.getStatus() == 200) {

                                // Set Shared Preference
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("email", loginResponse.getUser().getEmail());
                                editor.putString("password", loginResponse.getUser().getPassword());
                                editor.putString("birth_date", loginResponse.getUser().getBirthDate());
                                editor.putString("phone", loginResponse.getUser().getPhone());
                                editor.putString("name", loginResponse.getUser().getName());
                                editor.putString("id", loginResponse.getUser().getIdUser());
                                editor.apply();

                                // Passing string object to intent extra
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                FancyToast.makeText(LoginActivity.this, loginResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                            }

                            progressDialog.dismiss();
                        }
                    });
                }
            });

            mSignUpTv.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            });
        }
    }

    public boolean validate(View v) {
        boolean emailValidation = ValidationHelper.requiredTextInputValidation(mEmailLayout);
        boolean passwordValidation = ValidationHelper.requiredTextInputValidation(mPasswordLayout);

        return emailValidation && passwordValidation;
    }
}
package id.kelompok04.doize.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.UserViewModel;
import id.kelompok04.doize.helper.ValidationHelper;
import id.kelompok04.doize.model.User;
import id.kelompok04.doize.model.response.LoginResponse;

public class RegisterActivity extends AppCompatActivity {

    private UserViewModel mUserViewModel;
    private TextView mSignInTv;
    private Button mBtnSignUp;
    private TextInputLayout mEmailLayout;
    private TextInputLayout mPasswordLayout;
    private TextInputLayout mNameLayout;
    private TextInputLayout mConfirmationPasswordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        mSignInTv = findViewById(R.id.tvSignIn);
        mBtnSignUp = findViewById(R.id.btnSignUp);
        mEmailLayout = findViewById(R.id.txtLayoutEmail);
        mPasswordLayout = findViewById(R.id.txtLayoutPassword);
        mNameLayout = findViewById(R.id.txtLayoutName);
        mConfirmationPasswordLayout = findViewById(R.id.txtLayoutPasswordConfirmation);

        mSignInTv.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        mBtnSignUp.setOnClickListener(v -> {
            String name = mNameLayout.getEditText().getText().toString();
            String email = mEmailLayout.getEditText().getText().toString();
            String password = mPasswordLayout.getEditText().getText().toString();

            User newUser = new User(name, email, password, "1");

            if (validate(v)) {
                ProgressDialog progressDialog = ProgressDialog.show(this, "Sign Up", "Signing Up...");
                mUserViewModel.register(newUser).observe(this, loginResponse -> {
                    if (loginResponse.getStatus() == 200) {
                        Toast.makeText(RegisterActivity.this, "Sign Up Success!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Sign Up Failed!", Toast.LENGTH_SHORT).show();
                    }

                    progressDialog.dismiss();
                });

            }
        });
    }

    public boolean validate(View v) {
        boolean nameValidation = ValidationHelper.requiredTextInputValidation(mNameLayout);
        boolean emailValidation = ValidationHelper.requiredTextInputValidation(mEmailLayout);
        boolean passwordValidation = ValidationHelper.requiredTextInputValidation(mPasswordLayout);
        boolean confirmationValidation = ValidationHelper.confirmationValidation(mPasswordLayout, mConfirmationPasswordLayout);

        return nameValidation && emailValidation && passwordValidation && confirmationValidation;
    }


}
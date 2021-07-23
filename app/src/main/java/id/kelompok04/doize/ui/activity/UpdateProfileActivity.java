package id.kelompok04.doize.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.UserViewModel;
import id.kelompok04.doize.helper.ValidationHelper;
import id.kelompok04.doize.model.User;
import id.kelompok04.doize.service.UserService;

public class UpdateProfileActivity extends AppCompatActivity {
    private static final String TAG = "UpdateProfileActivity";

    private UserViewModel mUserViewModel;
    private Button mUpdateProfileButton;
    private TextInputEditText mNameTxt;
    private TextInputEditText mPhoneTxt;
    private TextInputEditText mEmailTxt;
    private TextInputEditText mBirthDateTxt;
    private TextInputLayout mNameLayout;
    private TextInputLayout mEmailLayout;
    private TextInputLayout mPhoneLayout;
    private TextInputLayout mBirthDateLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        mNameLayout = findViewById(R.id.txtLayoutName);
        mPhoneLayout = findViewById(R.id.txtLayoutPhone);
        mEmailLayout = findViewById(R.id.txtLayoutEmail);
        mUpdateProfileButton = findViewById(R.id.btnUpdateProfile);

        String names;
        String emails;
        String phones;
        String birth_dates;

        Bundle bundle = getIntent().getExtras();
        names = bundle.getString("name");
        emails = bundle.getString("email");
        phones = bundle.getString("phone");
        birth_dates = bundle.getString("birth_date");

        mBirthDateTxt=(TextInputEditText) findViewById(R.id.txtLayoutBirthDate);

        mNameTxt.setText(names);
        mPhoneTxt.setText(phones);
        mBirthDateTxt.setText(birth_dates);
        mEmailTxt.setText(emails);

        mUpdateProfileButton.setOnClickListener(v -> {
            String name = mNameLayout.getEditText().getText().toString();
            String email = mEmailLayout.getEditText().getText().toString();
            String phone = mPhoneLayout.getEditText().getText().toString();
            String birth_date = mBirthDateLayout.getEditText().getText().toString();

            User updateUser = new User(name, birth_date, email, phone);

            if (validate(v)) {
                mUserViewModel.updateProfile(updateUser).observe(this, loginResponse -> {
                    if (loginResponse.getStatus() == 200) {
                        Toast.makeText(UpdateProfileActivity.this, "Update Profile Success!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdateProfileActivity.this, "Update Profile Failed!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    public boolean validate(View v) {
        boolean nameValidation = ValidationHelper.requiredTextInputValidation(mNameLayout);
        boolean emailValidation = ValidationHelper.requiredTextInputValidation(mEmailLayout);
        boolean phoneValidation = ValidationHelper.requiredTextInputValidation(mPhoneLayout);

        return nameValidation && emailValidation && phoneValidation;
    }
}

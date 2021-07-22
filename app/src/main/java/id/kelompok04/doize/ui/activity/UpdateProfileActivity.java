package id.kelompok04.doize.ui.activity;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

import id.kelompok04.doize.architecture.viewmodel.UserViewModel;
import id.kelompok04.doize.model.User;
import id.kelompok04.doize.service.UserService;

public class UpdateProfileActivity extends AppCompatActivity {
    private static final String TAG = "UpdateProfileActivity";

    private UserViewModel mUserViewModel;
    private Button mUpdateProfileButton;
    private TextInputEditText mNameTxt;
    private TextInputEditText mPhoneTxt;
    private TextInputEditText mEmailTxt;
    private TextInputLayout mNameLayout;
    private TextInputLayout mEmailLayout;
    private TextInputLayout mPhoneLayout;

}

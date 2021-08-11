package id.kelompok04.doize.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.UserViewModel;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.ValidationHelper;
import id.kelompok04.doize.model.User;
import id.kelompok04.doize.model.response.LoginResponse;
import id.kelompok04.doize.model.response.UserResponse;
import id.kelompok04.doize.ui.activity.MainActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PasswordFragment extends Fragment {

    private static final String TAG = "PasswordFragment";

    // Component
    private TextInputLayout mCurrentLayout;
    private TextInputLayout mPasswordLayout;
    private TextInputLayout mConfirmationPasswordLayout;
    private Button mButtonUpdate;


    private SharedPreferences userPreferences;

    // View Model
    private UserViewModel mUserViewModel;

    public PasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PasswordFragment newInstance(String param1, String param2) {
        PasswordFragment fragment = new PasswordFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userPreferences = requireActivity().getSharedPreferences("user_pref", MODE_PRIVATE);
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_password, container, false);

        mCurrentLayout = view.findViewById(R.id.txtLayoutCurrentPassword);
        mPasswordLayout = view.findViewById(R.id.txtLayoutUpdatePassword);
        mConfirmationPasswordLayout = view.findViewById(R.id.txtLayoutUpdatePasswordConfirmation);
        mButtonUpdate = view.findViewById(R.id.btnUpdatePassword);

        mButtonUpdate.setOnClickListener(v -> {
            String currentPass = userPreferences.getString("password", "");
            String currentPassInput = mCurrentLayout.getEditText().getText().toString();
            String newPassword = mPasswordLayout.getEditText().getText().toString();
            String idUser = userPreferences.getString("id", "");

            String name = userPreferences.getString("name", "");
            String phone = userPreferences.getString("phone", "");
            String birth_date = userPreferences.getString("birth_date", "");
            String email = userPreferences.getString("email", "");

            User user = new User();
            user.setPassword(newPassword);
            user.setIdUser(idUser);
            user.setName(name);
            user.setEmail(email);

            if (validate(v)) {
                if (currentPassInput.equals(currentPass)) {

                    ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "Password", "Updating password ...");
                    mUserViewModel.updateProfile(user).observe(getViewLifecycleOwner(), userResponse -> {
                        if (userResponse.getStatus() == 200) {
                            User addedUser = userResponse.getData();

                            SharedPreferences.Editor editor = userPreferences.edit();
                            editor.putString("name", addedUser.getName());
                            editor.putString("password", addedUser.getPassword());
                            editor.putString("email", addedUser.getEmail());
                            editor.apply();

                            ((MainActivity) requireActivity()).setHeaderUser(addedUser.getName(), addedUser.getEmail());
                            FancyToast.makeText(getActivity(), "Password updated succesfuly", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                            mCurrentLayout.getEditText().setText("");
                            mPasswordLayout.getEditText().setText("");
                            mConfirmationPasswordLayout.getEditText().setText("");

                        } else {
                            FancyToast.makeText(getActivity(), userResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }
                        progressDialog.dismiss();
                    });
                } else {
                    FancyToast.makeText(getActivity(), "Current password was entered incorrectly", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
            }

        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public boolean validate(View v) {
        boolean currentPasswordValidation = ValidationHelper.requiredTextInputValidation(mCurrentLayout);
        boolean passwordValidation = ValidationHelper.requiredTextInputValidation(mPasswordLayout);
        boolean confirmationValidation = ValidationHelper.confirmationValidation(mPasswordLayout, mConfirmationPasswordLayout);

        return currentPasswordValidation && passwordValidation && confirmationValidation;
    }
}
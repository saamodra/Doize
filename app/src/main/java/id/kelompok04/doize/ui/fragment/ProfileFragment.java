package id.kelompok04.doize.ui.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.ScheduleViewModel;
import id.kelompok04.doize.architecture.viewmodel.UserViewModel;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.DateType;
import id.kelompok04.doize.model.User;
import id.kelompok04.doize.model.response.UserResponse;
import id.kelompok04.doize.ui.activity.MainActivity;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    // View Model
    private UserViewModel mUserViewModel;

    // Component
    private TextInputLayout mNameLayout;
    private TextInputLayout mPhoneLayout;
    private TextInputLayout mEmailLayout;
    private TextInputLayout mBirthDateLayout;
    private Button mButtonUpdate;
    private SharedPreferences userPreferences;

    // Date Format
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userPreferences = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mNameLayout = view.findViewById(R.id.txtLayoutName);
        mPhoneLayout = view.findViewById(R.id.txtLayoutPhone);
        mEmailLayout = view.findViewById(R.id.txtLayoutEmail);
        mBirthDateLayout = view.findViewById(R.id.txtLayoutBirthDate);
        mButtonUpdate = view.findViewById(R.id.btnUpdateProfile);

        mButtonUpdate.setOnClickListener(v -> {
            String name = mNameLayout.getEditText().getText().toString();
            String phone = mPhoneLayout.getEditText().getText().toString();
            String email = mEmailLayout.getEditText().getText().toString();
            String birth_date = mBirthDateLayout.getEditText().getText().toString();
            String newBirthDate = DateConverter.toDbFrom(mSimpleDateFormat, birth_date);
            String idUser = userPreferences.getString("id", "");

            User user = new User();
            user.setName(name);
            user.setPhone(phone);
            user.setEmail(email);
            user.setBirthDate(newBirthDate);
            user.setIdUser(idUser);

            ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "Profile", "Updating profile ...");
            mUserViewModel.updateProfile(user).observe(getViewLifecycleOwner(), userResponse -> {
                if (userResponse.getStatus() == 200) {
                    User addedUser = userResponse.getData();

                    SharedPreferences.Editor editor = userPreferences.edit();
                    editor.putString("name", addedUser.getName());
                    editor.putString("phone", addedUser.getPhone());
                    editor.putString("email", addedUser.getEmail());
                    editor.putString("birth_date", addedUser.getBirthDate());
                    editor.apply();

                    ((MainActivity)requireActivity()).setHeaderUser(addedUser.getName(), addedUser.getEmail());
                    FancyToast.makeText(getActivity(), userResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                } else {
                    FancyToast.makeText(getActivity(), userResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                }

                progressDialog.dismiss();
            });

        });

        mBirthDateLayout.getEditText().setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();

            Date birthDate = null;
            String birth_date = userPreferences.getString("birth_date", "");
            if (birth_date != null && !birth_date.equals("")) {
                birthDate = DateConverter.fromDbToDate(DateType.DATE, birth_date);
            }

            DatePickerFragment dialog = DatePickerFragment.newInstance(DateType.DATE, mBirthDateLayout.getEditText(), birthDate != null ? birthDate : new Date());
            dialog.setTargetFragment(ProfileFragment.this, 0);
            dialog.show(fragmentManager, "DialogDate");
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String name = userPreferences.getString("name", "");
        String phone = userPreferences.getString("phone", "");
        String birth_date = userPreferences.getString("birth_date", "");
        String email = userPreferences.getString("email", "");
        
        if (birth_date != null && !birth_date.equals("")) {
            String newBirthDate = DateConverter.fromDbTo(mSimpleDateFormat, birth_date);
            mBirthDateLayout.getEditText().setText(newBirthDate);
        }

        mNameLayout.getEditText().setText(name);
        mPhoneLayout.getEditText().setText(phone);
        mEmailLayout.getEditText().setText(email);
    }
}
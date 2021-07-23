package id.kelompok04.doize.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import id.kelompok04.doize.model.User;
import id.kelompok04.doize.model.response.UserResponse;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements DatePickerFragment.Callbacks {
    private static final String TAG = "ProfileFragment";

    // View Model
    private UserViewModel mUserViewModel;

    // Component
    private TextInputLayout mNameLayout;
    private TextInputLayout mPhoneLayout;
    private TextInputLayout mEmailLayout;
    private TextInputLayout mBirthDateLayout;
    private Button mButtonUpdate;

    // Date Format
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

            User user = new User();
            user.setName(name);
            user.setPhone(phone);
            user.setEmail(email);
            user.setBirthDate(newBirthDate);

            mUserViewModel.updateProfile(user).observe(getViewLifecycleOwner(), new Observer<UserResponse>() {
                @Override
                public void onChanged(UserResponse userResponse) {
                    if (userResponse.getStatus() == 200) {
                        FancyToast.makeText(getActivity(), userResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();

                    } else {
                        FancyToast.makeText(getActivity(), userResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                    }
                }
            });

        });

        mBirthDateLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
                dialog.setTargetFragment(ProfileFragment.this, 0);
                dialog.show(fragmentManager, "DialogDate");
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUserViewModel.getUserLogin().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                String newBirthDate = DateConverter.fromDbTo(mSimpleDateFormat, user.getBirthDate());

                mNameLayout.getEditText().setText(user.getName());
                mPhoneLayout.getEditText().setText(user.getPhone());
                mEmailLayout.getEditText().setText(user.getEmail());
                mBirthDateLayout.getEditText().setText(newBirthDate);
            }
        });
    }

    @Override
    public void onDateSelected(Date date) {
        String birthDate = mSimpleDateFormat.format(date);
        mBirthDateLayout.getEditText().setText(birthDate);
    }
}
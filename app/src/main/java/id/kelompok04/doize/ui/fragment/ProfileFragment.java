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
import id.kelompok04.doize.model.User;
import id.kelompok04.doize.model.response.UserResponse;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements DatePickerFragment.Callbacks {

    private UserViewModel mUserViewModel;

    private TextInputLayout mNameLayout;
    private TextInputLayout mPhoneLayout;
    private TextInputLayout mEmailLayout;
    private TextInputLayout mBirthDateLayout;
    private Button mButtonUpdate;
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameLayout.getEditText().getText().toString();
                String phone = mPhoneLayout.getEditText().getText().toString();
                String email = mEmailLayout.getEditText().getText().toString();
                String birth_date = mBirthDateLayout.getEditText().getText().toString();
                Date birthDate = new Date();

                try {
                    birthDate = mSimpleDateFormat.parse(birth_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String newBirthDate = mDateFormat.format(birthDate);

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

            }
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

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUserViewModel.getUserLogin().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Date birthDate = new Date();

                try {
                    birthDate = mDateFormat.parse(user.getBirthDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String newBirthDate = mSimpleDateFormat.format(birthDate);
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
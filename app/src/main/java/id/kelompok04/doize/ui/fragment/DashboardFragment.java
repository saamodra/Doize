package id.kelompok04.doize.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import java.io.Serializable;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.UserViewModel;
import id.kelompok04.doize.model.User;
import id.kelompok04.doize.ui.activity.LoginActivity;
import id.kelompok04.doize.ui.activity.MainActivity;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";
    private static final String USERLOGIN = "UserLogin";

    private TextView mUserLogin;
    private Button mBtnLogout;

    private TextView mUserHeaderProfile;
    private TextView mUserHeaderEmail;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        Gson gson = new Gson();

//        User user = new User("Kelompok 04", "kel4@gmail.com", "password", "1");
        SharedPreferences preferences = this.getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        String name = (preferences.getString("name", ""));

        mUserLogin = v.findViewById(R.id.txtDashboardWelcome);
        mUserLogin.setText("Welcome, " + name);

        mBtnLogout = v.findViewById(R.id.btnLogout);
        mBtnLogout.setOnClickListener(v1 -> {

            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        return v;
    }
}

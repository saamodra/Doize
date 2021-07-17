package id.kelompok04.doize.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.io.Serializable;

import id.kelompok04.doize.R;
import id.kelompok04.doize.model.User;
import id.kelompok04.doize.ui.activity.LoginActivity;
import id.kelompok04.doize.ui.activity.MainActivity;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";
    private static final String USERLOGIN = "UserLogin";

    private TextView mUserLogin;
    private Button mBtnLogout;

    public static DashboardFragment newInstance(String user) {
        Bundle args = new Bundle();
        args.putSerializable(USERLOGIN, user);
        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        Gson gson = new Gson();

        User userLogin = gson.fromJson((String) getArguments().getSerializable(USERLOGIN), User.class);

        mUserLogin = v.findViewById(R.id.txtDashboardWelcome);
        mUserLogin.setText("Welcome, " + userLogin.getFirstName());

        mBtnLogout = v.findViewById(R.id.btnLogout);
        mBtnLogout.setOnClickListener(v1 -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        });

        return v;
    }
}

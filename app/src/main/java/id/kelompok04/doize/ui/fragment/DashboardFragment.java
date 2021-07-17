package id.kelompok04.doize.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;

import id.kelompok04.doize.R;
import id.kelompok04.doize.model.User;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";
    private static final String USERLOGIN = "UserLogin";

    private TextView mUserLogin;

    public static DashboardFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable(USERLOGIN, (Serializable) user);
        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        User userLogin = (User) getArguments().getSerializable(USERLOGIN);

        mUserLogin = v.findViewById(R.id.txtDashboardWelcome);
        mUserLogin.setText("Welcome, " + userLogin.getFirstName());

        return v;
    }
}

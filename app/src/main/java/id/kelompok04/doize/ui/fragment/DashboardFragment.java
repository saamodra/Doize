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

import id.kelompok04.doize.R;
import id.kelompok04.doize.ui.activity.LoginActivity;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";

    private TextView mUserLogin;
    private Button mBtnLogout;

    SharedPreferences preferences;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        preferences = getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        String name = (preferences.getString("name", ""));

        mUserLogin = v.findViewById(R.id.txtDashboardWelcome);
        mUserLogin.setText("Welcome, " + name);

        mBtnLogout = v.findViewById(R.id.btnLogout);
        mBtnLogout.setOnClickListener(v1 -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();

            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("email");
            editor.remove("password");
            editor.remove("name");
            editor.remove("id");
            editor.apply();
        });

        return v;
    }
}

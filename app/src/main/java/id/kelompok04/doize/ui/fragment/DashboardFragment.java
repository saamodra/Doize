package id.kelompok04.doize.ui.fragment;

import android.annotation.SuppressLint;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import id.kelompok04.doize.R;
import id.kelompok04.doize.ui.activity.LoginActivity;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";

    private TextView mUserLoginName, mTodayDate;

    SharedPreferences preferences;

    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd MMM yyyy");

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        preferences = getActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        String name = (preferences.getString("name", ""));

        mUserLoginName = v.findViewById(R.id.tv_dashboard_name);
        mUserLoginName.setText(name);

        mTodayDate = v.findViewById(R.id.tv_dashboard_day);
        mTodayDate.setText(mSimpleDateFormat.format(new Date()));

        return v;
    }
}

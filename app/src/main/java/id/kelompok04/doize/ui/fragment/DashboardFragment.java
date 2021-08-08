package id.kelompok04.doize.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.AssignmentViewModel;
import id.kelompok04.doize.architecture.viewmodel.DailyActivityViewModel;
import id.kelompok04.doize.architecture.viewmodel.DetailScheduleViewModel;
import id.kelompok04.doize.architecture.viewmodel.ScheduleViewModel;
import id.kelompok04.doize.model.Assignment;
import id.kelompok04.doize.model.DailyActivity;
import id.kelompok04.doize.model.Schedule;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";

    private TextView mUserLoginName, mTodayDate;

    SharedPreferences preferences;

    // Data
    private AssignmentViewModel mAssignmentViewModel;
    private DailyActivityViewModel mDailyActivityViewModel;
    private ScheduleViewModel mScheduleViewModel;
    private DetailScheduleViewModel mDetailScheduleViewModel;
    private List<Assignment> mAssignmentList;
    private List<DailyActivity> mDailyActivityList;
    private List<Schedule> mScheduleList;

    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd MMM yyyy");

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAssignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
        mDailyActivityViewModel = new ViewModelProvider(this).get(DailyActivityViewModel.class);
        mScheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
//        mScheduleViewModel = new ViewModelProvider(this).get()
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

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mScheduleViewModel
    }
}

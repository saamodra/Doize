package id.kelompok04.doize.ui.fragment;

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
import android.widget.TextView;

import java.util.List;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.DetailScheduleViewModel;
import id.kelompok04.doize.architecture.viewmodel.ScheduleViewModel;
import id.kelompok04.doize.model.DetailSchedule;

public class ScheduleDetailFragment extends Fragment {
    private static final String TAG = "ScheduleDetailFragment";

    private DetailScheduleViewModel mDetailScheduleViewModel;

    private TextView mTvScheduleName;
    private TextView mTvScheduleDesc;

    public ScheduleDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailScheduleViewModel = new ViewModelProvider(this).get(DetailScheduleViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_detail, container, false);
        mTvScheduleName = view.findViewById(R.id.tv_schedule_name);
        mTvScheduleDesc = view.findViewById(R.id.tv_schedule_description);
        Log.d(TAG, "onCreateView: " + mDetailScheduleViewModel);
        String name = getArguments().getString("name");
        String desc = getArguments().getString("desc");

        mTvScheduleName.setText(name);
        mTvScheduleDesc.setText(desc);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDetailScheduleViewModel.getDetailSchedules();
    }
}
package id.kelompok04.doize.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.ScheduleViewModel;
import id.kelompok04.doize.model.Schedule;

public class ScheduleFragment extends Fragment {
    private static final String TAG = "ScheduleFragment";

    private ScheduleViewModel mScheduleViewModel;
    private RecyclerView mScheduleRecyclerView;
    private ScheduleAdapter mAdapter = new ScheduleAdapter(Collections.emptyList());


    public static ScheduleFragment newInstance() {
        ScheduleFragment fragment = new ScheduleFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mScheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);
        mScheduleRecyclerView = v.findViewById(R.id.rv_schedule);
        mScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mScheduleRecyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScheduleViewModel.getSchedules().observe(getViewLifecycleOwner(), new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> schedules) {
                updateUI(schedules);
                Log.d(TAG, "onChanged: " + schedules);
            }
        });
    }

    private void updateUI(List<Schedule> crimes) {
        mAdapter = new ScheduleAdapter(crimes);
        mScheduleRecyclerView.setAdapter(mAdapter);
    }

    private class ScheduleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mScheduleName;
        private TextView mScheduleDesc;

        private Schedule mSchedule;

        public ScheduleHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_row_schedule, parent, false));
            itemView.setOnClickListener(this);

            mScheduleName = itemView.findViewById(R.id.tv_schedule_name);
            mScheduleDesc = itemView.findViewById(R.id.tv_schedule_description);
        }

        public void bind(Schedule schedule) {
            mScheduleName.setText(schedule.getNameSchedule());
            mScheduleDesc.setText(schedule.getDescriptionSchedule());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "Onclick on " + mScheduleDesc.getText(), Toast.LENGTH_SHORT).show();
        }
    }

    private class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Schedule> mSchedules;

        public ScheduleAdapter(List<Schedule> schedules) {
            mSchedules = schedules;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ScheduleHolder(layoutInflater, parent);
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Schedule schedule = mSchedules.get(position);
            ((ScheduleHolder) holder).bind(schedule);
        }

        @Override
        public int getItemCount() {
            return mSchedules.size();
        }
    }
}
package id.kelompok04.doize.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shashank.sony.fancytoastlib.FancyToast;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.AssignmentViewModel;
import id.kelompok04.doize.architecture.viewmodel.DailyActivityViewModel;
import id.kelompok04.doize.architecture.viewmodel.DetailScheduleViewModel;
import id.kelompok04.doize.architecture.viewmodel.ScheduleViewModel;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.DoizeHelper;
import id.kelompok04.doize.model.Assignment;
import id.kelompok04.doize.model.DailyActivity;
import id.kelompok04.doize.model.DetailSchedule;
import id.kelompok04.doize.model.Schedule;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";

    // Components
    private TextView mUserLoginName, mTodayDate;
    private RecyclerView mRvAssignments, mRvSchedules, mRvDailyActivities;
    private View scheduleEmptyCard;

    SharedPreferences preferences;

    // Data
    private AssignmentViewModel mAssignmentViewModel;
    private DailyActivityViewModel mDailyActivityViewModel;
    private ScheduleViewModel mScheduleViewModel;
    private DetailScheduleViewModel mDetailScheduleViewModel;
    private List<Assignment> mAssignmentList;
    private List<DailyActivity> mDailyActivityList;
    private List<Schedule> mScheduleList;

    // Adapter
    private ScheduleAdapter mScheduleAdapter = new ScheduleAdapter(Collections.emptyList());

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

        mRvSchedules = v.findViewById(R.id.rv_dashboard_schedules);
        mRvSchedules.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvSchedules.setAdapter(mScheduleAdapter);

        scheduleEmptyCard = v.findViewById(R.id.dashboard_schedule_empty_card);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScheduleViewModel.getSchedulesDayUser(DoizeHelper.getIdUserPref(requireActivity())).observe(getViewLifecycleOwner(), this::updateScheduleUI);
    }

    private void updateScheduleUI(List<Schedule> schedules) {;
        mScheduleList = schedules;
        mScheduleAdapter = new ScheduleAdapter(schedules);
        mRvSchedules.setAdapter(mScheduleAdapter);

        scheduleEmptyCard.setVisibility(schedules.size() == 0 ? View.VISIBLE : View.GONE);
        mRvSchedules.setVisibility(schedules.size() == 0 ? View.GONE : View.VISIBLE);
    }

    private class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder> {

        private List<Schedule> mSchedules;

        public ScheduleAdapter(List<Schedule> schedules) {
            mSchedules = schedules;
        }

        @NonNull
        @Override
        public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ScheduleHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
            Schedule schedule = mSchedules.get(position);
            holder.bind(schedule);
        }

        @Override
        public int getItemCount() {
            return mSchedules.size();
        }

        private class ScheduleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView mScheduleName;
            private RecyclerView rvItem;
            private ScheduleDetailDayItemAdapter mScheduleDetailDayItemAdapter = new ScheduleDetailDayItemAdapter(Collections.emptyList());

            public ScheduleHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_row_schedule_detail, parent, false));
                itemView.setOnClickListener(this);

                mScheduleName = itemView.findViewById(R.id.tv_schedule_day);
                rvItem = itemView.findViewById(R.id.rv_schedule_detail_item);
                rvItem.setLayoutManager(new LinearLayoutManager(parent.getContext()));
            }

            public void bind(Schedule schedule) {
                mScheduleName.setText(schedule.getNameSchedule());
                mScheduleDetailDayItemAdapter = new ScheduleDetailDayItemAdapter(schedule.getDetailSchedule());
                rvItem.setAdapter(mScheduleDetailDayItemAdapter);
            }

            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.fragment_container).navigate(R.id.action_scheduleFragment_to_scheduleDetailFragment);
            }
        }

        public class ScheduleDetailDayItemAdapter extends RecyclerView.Adapter<ScheduleDetailDayItemAdapter.ScheduleDetailDayItemHolder> {

            private List<DetailSchedule> mDetailSchedules;

            @SuppressLint("SimpleDateFormat")
            private final SimpleDateFormat mSimpleTimeFormat = new SimpleDateFormat("HH:mm");

            public ScheduleDetailDayItemAdapter(List<DetailSchedule> mDetailSchedules) {
                this.mDetailSchedules = mDetailSchedules;
            }

            @NonNull
            @Override
            public ScheduleDetailDayItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

                return new ScheduleDetailDayItemHolder(layoutInflater, parent);
            }


            @Override
            public void onBindViewHolder(@NonNull ScheduleDetailDayItemHolder holder, int position) {
                DetailSchedule detailSchedule = mDetailSchedules.get(position);
                holder.bind(detailSchedule);
            }

            @Override
            public int getItemCount() {
                return mDetailSchedules.size();
            }

            private class ScheduleDetailDayItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

                private TextView tvScheduleDetailName;
                private TextView tvScheduleDetailTime;
                private ImageButton editButton;
                private ImageButton deleteButton;

                private DetailSchedule mDetailSchedule;

                public ScheduleDetailDayItemHolder(LayoutInflater inflater, ViewGroup parent) {
                    super(inflater.inflate(R.layout.item_row_schedule_detail_item, parent, false));
                    itemView.setOnClickListener(this);

                    tvScheduleDetailName = itemView.findViewById(R.id.tv_schedule_detail_name);
                    tvScheduleDetailTime = itemView.findViewById(R.id.tv_schedule_detail_time);
                    editButton = itemView.findViewById(R.id.edit_detail_schedule);
                    deleteButton = itemView.findViewById(R.id.del_detail_schedule);
                    editButton.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.GONE);
                }

                @SuppressLint("SetTextI18n")
                public void bind(DetailSchedule detailSchedule) {
                    mDetailSchedule = detailSchedule;
                    tvScheduleDetailName.setText(mDetailSchedule.getNameDetailSchedule());

                    String startTime = DateConverter.fromDbTimeTo(mSimpleTimeFormat, mDetailSchedule.getStartTime());
                    String endTime = DateConverter.fromDbTimeTo(mSimpleTimeFormat, mDetailSchedule.getEndTime());
                    tvScheduleDetailTime.setText(startTime + " - " + endTime);
                }

                @Override
                public void onClick(View v) {

                }
            }
        }
    }


}

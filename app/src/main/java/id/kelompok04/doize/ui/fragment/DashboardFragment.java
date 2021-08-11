package id.kelompok04.doize.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.DoizeConstants;
import id.kelompok04.doize.helper.DoizeHelper;
import id.kelompok04.doize.model.Assignment;
import id.kelompok04.doize.model.DailyActivity;
import id.kelompok04.doize.model.DetailSchedule;
import id.kelompok04.doize.model.Schedule;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";

    // Components
    private TextView mUserLoginName, mTodayDate, mTvAssignmentSeeAll, mTvDailyActivitySeeAll, mTvScheduleSeeAll;
    private RecyclerView mRvAssignments, mRvSchedules, mRvDailyActivities;
    private View scheduleEmptyCard, assignmentEmptyCard, dailyActivityEmptyCard;

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
    private AssignmentAdapter mAssignmentAdapter = new AssignmentAdapter(Collections.emptyList());
    private DailyActivityAdapter mDailyActivityAdapter = new DailyActivityAdapter(Collections.emptyList());

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

        mRvAssignments = v.findViewById(R.id.rv_dashboard_assignments);
        mRvAssignments.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvAssignments.setAdapter(mAssignmentAdapter);

        mRvDailyActivities = v.findViewById(R.id.rv_dashboard_daily_activity);
        mRvDailyActivities.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvDailyActivities.setAdapter(mDailyActivityAdapter);

        scheduleEmptyCard = v.findViewById(R.id.dashboard_schedule_empty_card);
        assignmentEmptyCard = v.findViewById(R.id.dashboard_assignment_empty_card);
        dailyActivityEmptyCard = v.findViewById(R.id.dashboard_daily_activity_empty_card);

        mTvAssignmentSeeAll = v.findViewById(R.id.tv_assignment_seeall);
        mTvDailyActivitySeeAll = v.findViewById(R.id.tv_daily_activity_seeall);
        mTvScheduleSeeAll = v.findViewById(R.id.tv_schedule_seeall);

        mTvAssignmentSeeAll.setOnClickListener(v1 -> {
            Navigation.findNavController(getActivity(), R.id.fragment_container).navigate(R.id.action_dashboardFragment_to_assignmentFragment);
        });

        mTvDailyActivitySeeAll.setOnClickListener(v1 -> {
            Navigation.findNavController(getActivity(), R.id.fragment_container).navigate(R.id.action_dashboardFragment_to_dailyActivityFragment);
        });

        mTvScheduleSeeAll.setOnClickListener(v1 -> {
            Navigation.findNavController(getActivity(), R.id.fragment_container).navigate(R.id.action_dashboardFragment_to_scheduleFragment);
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAssignmentViewModel.getAssignments(DoizeHelper.getIdUserPref(requireActivity())).observe(getViewLifecycleOwner(), this::updateAssignmentUI);
        mDailyActivityViewModel.getDailyActivities(DoizeHelper.getIdUserPref(requireActivity())).observe(getViewLifecycleOwner(), this::updateDailyActivityUI);
        mScheduleViewModel.getSchedulesDayUser(DoizeHelper.getIdUserPref(requireActivity())).observe(getViewLifecycleOwner(), this::updateScheduleUI);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateDailyActivityUI(List<DailyActivity> dailyActivities) {
        Log.d(TAG, "updateDailyActivityUI: " + dailyActivities);
        mDailyActivityList = DoizeHelper.getDashboardDailyActivities(dailyActivities);
        mDailyActivityAdapter = new DailyActivityAdapter(mDailyActivityList);
        mRvDailyActivities.setAdapter(mDailyActivityAdapter);

        dailyActivityEmptyCard.setVisibility(mDailyActivityList.size() == 0 ? View.VISIBLE : View.GONE);
        mRvDailyActivities.setVisibility(mDailyActivityList.size() == 0 ? View.GONE : View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateAssignmentUI(List<Assignment> assignments) {
        Log.d(TAG, "updateAssignmentUI: " + assignments);
        mAssignmentList = DoizeHelper.getDashboardAssignments(assignments);
        mAssignmentAdapter = new AssignmentAdapter(mAssignmentList);
        mRvAssignments.setAdapter(mAssignmentAdapter);

        assignmentEmptyCard.setVisibility(mAssignmentList.size() == 0 ? View.VISIBLE : View.GONE);
        mRvAssignments.setVisibility(mAssignmentList.size() == 0 ? View.GONE : View.VISIBLE);
    }

    private void updateScheduleUI(List<Schedule> schedules) {;
        mScheduleList = schedules;
        mScheduleAdapter = new ScheduleAdapter(schedules);
        mRvSchedules.setAdapter(mScheduleAdapter);

        scheduleEmptyCard.setVisibility(schedules.size() == 0 ? View.VISIBLE : View.GONE);
        mRvSchedules.setVisibility(schedules.size() == 0 ? View.GONE : View.VISIBLE);
    }

    private class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentHolder> {

        private List<Assignment> mAssignments;

        public AssignmentAdapter(List<Assignment> assignments) {
            mAssignments = assignments;
        }

        @NonNull
        @Override
        public AssignmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new AssignmentHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull AssignmentHolder holder, int position) {
            Assignment assignment = mAssignments.get(position);
            holder.bind(assignment);
        }

        @Override
        public int getItemCount() {
            return mAssignments.size();
        }

        private class AssignmentHolder extends RecyclerView.ViewHolder {

            private TextView mAssignmentName;
            private TextView mAssignmentCourse;
            private TextView mAssignmentTime;
            private ImageView mStarButton;
            private ImageView mCheckButton;
            private FrameLayout borderLeft;
            private Assignment mAssignment;

            public AssignmentHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_row_assignment, parent, false));

                mAssignmentName = itemView.findViewById(R.id.tv_assignment_name);
                mAssignmentCourse = itemView.findViewById(R.id.tv_assignment_course);
                mAssignmentTime = itemView.findViewById(R.id.tv_assignment_time);
                mStarButton = itemView.findViewById(R.id.star_icon);
                mCheckButton = itemView.findViewById(R.id.iv_check_assignment);
                borderLeft = itemView.findViewById(R.id.border_left_card_assignment);

                mStarButton.setOnClickListener(v -> {
                    int priority = mAssignment.getPriority() == 0 ? 1 : 0;
                    mAssignment.setPriority(priority);
                    mAssignmentViewModel.updateAssignment(-1,  mAssignment);
                });

                mCheckButton.setOnClickListener(v -> {
                    int workingStatus = mAssignment.getWorkingStatus() == 0 ? 1 : 0;
                    mAssignment.setWorkingStatus(workingStatus);
                    mAssignmentViewModel.updateAssignment(-1,  mAssignment);
                });
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            public void bind(Assignment assignment) {
                mAssignment = assignment;
                mAssignmentName.setText(assignment.getNameAssignment());
                mAssignmentCourse.setText(assignment.getCourse());

                Drawable borderLeftSrc = getResources()
                        .getDrawable((mAssignment.getWorkingStatus() == 0) ? R.drawable.border_left_purple : R.drawable.border_left_green);
                Drawable checkSrc = getResources()
                        .getDrawable((mAssignment.getWorkingStatus() == 0) ? R.drawable.ic_checked_false : R.drawable.ic_checked_true);

                mAssignmentName.setPaintFlags(mAssignment.getWorkingStatus() == 0 ? 0 : (mAssignmentName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG));

                borderLeft.setBackground(borderLeftSrc);
                mCheckButton.setImageDrawable(checkSrc);

                String dueDate = DateConverter.fromDbDateTimeTo(DoizeConstants.FULL_FORMAT, assignment.getDuedateAssignment());
                mAssignmentTime.setText(dueDate);

                Drawable star = getResources()
                        .getDrawable((mAssignment.getPriority() == 0) ? R.drawable.ic_star_bordered : R.drawable.ic_star_filled);

                mStarButton.setImageDrawable(star);
            }
        }
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

        private class ScheduleHolder extends RecyclerView.ViewHolder {

            private TextView mScheduleName;
            private RecyclerView rvItem;
            private ScheduleDetailDayItemAdapter mScheduleDetailDayItemAdapter = new ScheduleDetailDayItemAdapter(Collections.emptyList());

            public ScheduleHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_row_schedule_detail, parent, false));

                mScheduleName = itemView.findViewById(R.id.tv_schedule_day);
                rvItem = itemView.findViewById(R.id.rv_schedule_detail_item);
                rvItem.setLayoutManager(new LinearLayoutManager(parent.getContext()));
            }

            public void bind(Schedule schedule) {
                mScheduleName.setText(schedule.getNameSchedule());
                mScheduleDetailDayItemAdapter = new ScheduleDetailDayItemAdapter(schedule.getDetailSchedule());
                rvItem.setAdapter(mScheduleDetailDayItemAdapter);
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

    private class DailyActivityAdapter extends RecyclerView.Adapter<DailyActivityAdapter.DailyActivityHolder> {

        private List<DailyActivity> mDailyActivities;

        public DailyActivityAdapter(List<DailyActivity> dailyActivities) {
            mDailyActivities = dailyActivities;
        }

        @NonNull
        @Override
        public DailyActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new DailyActivityHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DailyActivityHolder holder, int position) {
            DailyActivity dailyActivity = mDailyActivities.get(position);
            holder.bind(dailyActivity);
        }

        @Override
        public int getItemCount() {
            return mDailyActivities.size();
        }

        private class DailyActivityHolder extends RecyclerView.ViewHolder {

            private TextView mDailyActivityName;
            private TextView mDailyActivityTime;
            private ImageView mStarButton;
            private ImageView mCheckButton;
            private FrameLayout borderLeft;
            private DailyActivity mDailyActivity;

            public DailyActivityHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_row_daily_activity, parent, false));

                mDailyActivityName = itemView.findViewById(R.id.tv_daily_activity_name);
                mDailyActivityTime = itemView.findViewById(R.id.tv_daily_activity_time);
                mStarButton = itemView.findViewById(R.id.star_icon);
                mCheckButton = itemView.findViewById(R.id.iv_check_daily_activity);
                borderLeft = itemView.findViewById(R.id.border_left_card_daily_activity);

                mStarButton.setOnClickListener(v -> {
                    int priority = mDailyActivity.getPriority() == 0 ? 1 : 0;
                    mDailyActivity.setPriority(priority);
                    mDailyActivityViewModel.updateDailyActivity(-1,  mDailyActivity);
                });

                mCheckButton.setOnClickListener(v -> {
                    int workingStatus = mDailyActivity.getWorkingStatus() == 0 ? 1 : 0;
                    mDailyActivity.setWorkingStatus(workingStatus);
                    mDailyActivityViewModel.updateDailyActivity(-1,  mDailyActivity);
                });
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            public void bind(DailyActivity dailyActivity) {
                mDailyActivity = dailyActivity;
                mDailyActivityName.setText(dailyActivity.getNameDailyActivity());

                Drawable borderLeftSrc = getResources()
                        .getDrawable((mDailyActivity.getWorkingStatus() == 0) ? R.drawable.border_left_purple : R.drawable.border_left_green);
                Drawable checkSrc = getResources()
                        .getDrawable((mDailyActivity.getWorkingStatus() == 0) ? R.drawable.ic_checked_false : R.drawable.ic_checked_true);

                mDailyActivityName.setPaintFlags(mDailyActivity.getWorkingStatus() == 0 ? 0 : (mDailyActivityName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG));

                borderLeft.setBackground(borderLeftSrc);
                mCheckButton.setImageDrawable(checkSrc);

                String dueDate = DateConverter.fromDbDateTimeTo(DoizeConstants.FULL_FORMAT, dailyActivity.getDuedateDailyActivity());
                mDailyActivityTime.setText(dueDate);

                Drawable star = getResources()
                        .getDrawable((mDailyActivity.getPriority() == 0) ? R.drawable.ic_star_bordered : R.drawable.ic_star_filled);

                mStarButton.setImageDrawable(star);
            }
        }
    }


}

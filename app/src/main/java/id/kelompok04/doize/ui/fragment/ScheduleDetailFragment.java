package id.kelompok04.doize.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.DetailScheduleViewModel;
import id.kelompok04.doize.architecture.viewmodel.ScheduleViewModel;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.DoizeConstants;
import id.kelompok04.doize.model.DetailSchedule;
import id.kelompok04.doize.model.Schedule;

public class ScheduleDetailFragment extends Fragment {
    private static final String TAG = "ScheduleDetailFragment";

    private DetailScheduleViewModel mDetailScheduleViewModel;
    private ScheduleDetailAdapter mScheduleDetailAdapter = new ScheduleDetailAdapter(Collections.emptyList());

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat mSimpleTimeFormat = new SimpleDateFormat("HH:mm");

    // Components
    private TextView mTvScheduleName;
    private TextView mTvScheduleDesc;
    private RecyclerView rvScheduleDetail;

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
        rvScheduleDetail = view.findViewById(R.id.rv_schedule_detail);
        rvScheduleDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvScheduleDetail.setAdapter(mScheduleDetailAdapter);

        mTvScheduleName = view.findViewById(R.id.tv_schedule_name);
        mTvScheduleDesc = view.findViewById(R.id.tv_schedule_description);

        String name = getArguments().getString("scheduleName");
        String desc = getArguments().getString("scheduleDesc");

        mTvScheduleName.setText(name);
        mTvScheduleDesc.setText(desc);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String idSchedule = getArguments().getString("scheduleId");

        mDetailScheduleViewModel.getDetailSchedules(idSchedule).observe(getViewLifecycleOwner(), new Observer<List<List<DetailSchedule>>>() {
            @Override
            public void onChanged(List<List<DetailSchedule>> lists) {
                Log.d(TAG, "onChanged: " + lists);
                updateUI(lists);
            }
        });
    }

    private void updateUI(List<List<DetailSchedule>> detailSchedule) {
        mScheduleDetailAdapter = new ScheduleDetailAdapter(detailSchedule);
        rvScheduleDetail.setAdapter(mScheduleDetailAdapter);
    }

    private class ScheduleDetailHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mDay;
        private RecyclerView rvItem;
        private List<DetailSchedule> listSchedule;
        private ScheduleDetailDayItemAdapter mScheduleDetailDayItemAdapter;

        public ScheduleDetailHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_row_schedule_detail, parent, false));
            itemView.setOnClickListener(this);

            mDay = itemView.findViewById(R.id.tv_schedule_day);
            rvItem = itemView.findViewById(R.id.rv_schedule_detail_item);
            rvItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        public void bind(String day, List<DetailSchedule> listSchedule) {
            this.listSchedule = listSchedule;
            mDay.setText(day);
            mScheduleDetailDayItemAdapter = new ScheduleDetailDayItemAdapter(listSchedule);
            rvItem.setAdapter(mScheduleDetailDayItemAdapter);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: on detail");

        }
    }

    private class ScheduleDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<List<DetailSchedule>> mDetailSchedules;

        public ScheduleDetailAdapter(List<List<DetailSchedule>> mDetailSchedules) {
            this.mDetailSchedules = mDetailSchedules;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ScheduleDetailHolder(layoutInflater, parent);
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            List<DetailSchedule> detailSchedule = mDetailSchedules.get(position);
            String day = DoizeConstants.DAY_LIST.get(position);
            ((ScheduleDetailHolder) holder).bind(day, detailSchedule);
        }

        @Override
        public int getItemCount() {
            return mDetailSchedules.size();
        }
    }


    private class ScheduleDetailDayItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvScheduleDetailName;
        private TextView tvScheduleDetailTime;

        private DetailSchedule mDetailSchedule;

        public ScheduleDetailDayItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_row_schedule_detail_item, parent, false));
            itemView.setOnClickListener(this);

            tvScheduleDetailName = itemView.findViewById(R.id.tv_schedule_detail_name);
            tvScheduleDetailTime = itemView.findViewById(R.id.tv_schedule_detail_time);
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

    private class ScheduleDetailDayItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<DetailSchedule> mDetailSchedules;

        public ScheduleDetailDayItemAdapter(List<DetailSchedule> mDetailSchedules) {
            this.mDetailSchedules = mDetailSchedules;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ScheduleDetailDayItemHolder(layoutInflater, parent);
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            DetailSchedule detailSchedule = mDetailSchedules.get(position);
            ((ScheduleDetailDayItemHolder) holder).bind(detailSchedule);
        }

        @Override
        public int getItemCount() {
            return mDetailSchedules.size();
        }
    }

}
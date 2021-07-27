package id.kelompok04.doize.architecture.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.kelompok04.doize.R;
import id.kelompok04.doize.helper.DoizeConstants;
import id.kelompok04.doize.model.DetailSchedule;

public class ScheduleDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<List<DetailSchedule>> mDetailSchedules;

    public ScheduleDetailAdapter(List<List<DetailSchedule>> mDetailSchedules) {
        this.mDetailSchedules = mDetailSchedules;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

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
            rvItem.setLayoutManager(new LinearLayoutManager(parent.getContext()));
        }

        public void bind(String day, List<DetailSchedule> listSchedule) {
            this.listSchedule = listSchedule;
            mDay.setText(day);
            mScheduleDetailDayItemAdapter = new ScheduleDetailDayItemAdapter(listSchedule);
            rvItem.setAdapter(mScheduleDetailDayItemAdapter);
        }

        @Override
        public void onClick(View v) {


        }
    }
}

package id.kelompok04.doize.architecture.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import id.kelompok04.doize.R;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.model.DetailSchedule;

public class ScheduleDetailDayItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DetailSchedule> mDetailSchedules;

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat mSimpleTimeFormat = new SimpleDateFormat("HH:mm");

    public ScheduleDetailDayItemAdapter(List<DetailSchedule> mDetailSchedules) {
        this.mDetailSchedules = mDetailSchedules;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

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
}

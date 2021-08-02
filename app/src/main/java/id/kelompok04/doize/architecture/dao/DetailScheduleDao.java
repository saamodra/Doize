package id.kelompok04.doize.architecture.dao;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.kelompok04.doize.helper.DoizeConstants;
import id.kelompok04.doize.model.DetailSchedule;
import id.kelompok04.doize.model.Schedule;

public class DetailScheduleDao {
    private static final String TAG = "DetailScheduleDao";
    private MutableLiveData<List<List<DetailSchedule>>> detailSchedules = new MutableLiveData<>();

    public LiveData<List<List<DetailSchedule>>> getDetailSchedules() {
        return detailSchedules;
    }

    public void setDetailSchedules(List<List<DetailSchedule>> detailSchedules) {
        this.detailSchedules.setValue(detailSchedules);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private DetailSchedule getDetailScheduleById(int detailScheduleId, List<List<DetailSchedule>> detailScheduleList) {
        for (List<DetailSchedule> detailSchedules : detailScheduleList) {
            for (DetailSchedule detailSchedule : detailSchedules) {
                if (detailSchedule.getIdDetailSchedule() == detailScheduleId) {
                    return detailSchedule;
                }
            }
        }

        return null;
    }

    public void deleteDetailSchedule(int day, int detailScheduleId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<List<DetailSchedule>> detailScheduleList = detailSchedules.getValue();
            if(detailScheduleList != null) {
                List<DetailSchedule> detailScheduleDay = detailScheduleList.get(day);
                // Loop schedule by day
                for (DetailSchedule detailSchedule : detailScheduleDay) {
                    if (detailSchedule.getIdDetailSchedule() == detailScheduleId) {
                        int index = detailScheduleDay.indexOf(detailSchedule);
                        detailScheduleDay.remove(index);
                        detailScheduleList.set(index, detailScheduleDay);

                        // Set data to dao
                        detailSchedules.setValue(detailScheduleList);
                        return;
                    }
                }
            }
        }
    }

    public void updateDetailSchedule(int dayBefore, DetailSchedule detailScheduleParam) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<List<DetailSchedule>> detailScheduleList = detailSchedules.getValue();
            if(detailScheduleList != null) {
                List<DetailSchedule> detailScheduleDay = detailScheduleList.get(dayBefore);
                for (DetailSchedule detailSchedule : detailScheduleDay) {
                    if (detailSchedule.getIdDetailSchedule() == detailScheduleParam.getIdDetailSchedule()) {
                        Log.d(TAG, "updateDetailSchedule: Update detail schedule");
                        detailScheduleDay.remove(detailSchedule);
                        detailScheduleList.set(dayBefore, detailScheduleDay);

                        int dayAfter = DoizeConstants.DAY_LIST.indexOf(detailScheduleParam.getDaySchedule());
                        List<DetailSchedule> detailScheduleUpdateDay = detailScheduleList.get(dayAfter);
                        detailScheduleUpdateDay.add(detailScheduleParam);
                        detailScheduleList.set(dayAfter, detailScheduleUpdateDay);

                        // Set data to dao
                        detailSchedules.setValue(detailScheduleList);
                        return;
                    }
                }
            }
        }
    }

    public void addDetailSchedule(int day, DetailSchedule detailScheduleParam) {
        List<List<DetailSchedule>> detailScheduleList = detailSchedules.getValue();
        if(detailScheduleList != null) {
            List<DetailSchedule> detailScheduleDay = detailScheduleList.get(day);
            detailScheduleDay.add(detailScheduleParam);
            detailScheduleList.set(day, detailScheduleDay);

            // Set data to dao
            detailSchedules.setValue(detailScheduleList);
        }
    }
}

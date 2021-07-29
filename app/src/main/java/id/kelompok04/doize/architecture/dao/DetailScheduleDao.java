package id.kelompok04.doize.architecture.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.kelompok04.doize.model.DetailSchedule;
import id.kelompok04.doize.model.Schedule;

public class DetailScheduleDao {
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
//                int index = 0;
                // Loop Day
//                for (List<DetailSchedule> detailScheduleDay : detailScheduleList) {
                List<DetailSchedule> detailScheduleDay = detailScheduleList.get(day);
                // Loop schedule by day
                for (DetailSchedule detailSchedule : detailScheduleDay) {
                    if (detailSchedule.getIdDetailSchedule() == detailScheduleId) {
                        int index = detailScheduleList.indexOf(detailScheduleDay);
                        detailScheduleDay.remove(detailSchedule);
                        detailScheduleList.set(index, detailScheduleDay);
                    }
                }
//                    index += 1;
//                }
            }
        }
    }

    public void updateDetailSchedule(int day, DetailSchedule detailScheduleParam) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<List<DetailSchedule>> detailScheduleList = detailSchedules.getValue();
            if(detailScheduleList != null) {

                List<DetailSchedule> detailScheduleDay = detailScheduleList.get(day);

                for (DetailSchedule detailSchedule : detailScheduleDay) {
                    if (detailSchedule.getIdDetailSchedule() == detailScheduleParam.getIdDetailSchedule()) {
                        int index = detailScheduleList.indexOf(detailScheduleDay);
                        detailScheduleDay.remove(detailSchedule);
                        detailScheduleList.set(index, detailScheduleDay);
                        return;
                    }
                }

                detailScheduleDay.add(detailScheduleParam);
            }

        }
    }
}

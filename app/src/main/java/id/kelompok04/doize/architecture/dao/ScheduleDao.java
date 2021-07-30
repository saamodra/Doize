package id.kelompok04.doize.architecture.dao;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.kelompok04.doize.model.Schedule;

public class ScheduleDao {
    private static final String TAG = "ScheduleDao";
    private MutableLiveData<List<Schedule>> schedules = new MutableLiveData<>();

    public LiveData<List<Schedule>> getListSchedule() {
        return schedules;
    }

    public void setListSchedule(List<Schedule> schedules) {
        this.schedules.setValue(schedules);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Schedule getScheduleById(int scheduleId, List<Schedule> scheduleList) {
        return scheduleList
                .stream()
                .filter(schedule -> schedule.getIdSchedule() == scheduleId)
                .findFirst()
                .orElse(null);
    }

    public void deleteSchedule(int scheduleId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<Schedule> scheduleList = schedules.getValue();
            if(scheduleList != null) {
                Schedule selectedSchedule = scheduleList
                        .stream().filter(schedule -> schedule.getIdSchedule() == scheduleId)
                        .findAny().orElse(null);

                if(selectedSchedule != null) {
                    scheduleList.remove(selectedSchedule);
                    schedules.setValue(scheduleList);
                }
            }
        }
    }

    public void addToPosition(int position, Schedule schedule) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<Schedule> scheduleList = schedules.getValue();
            if(scheduleList != null) {
                scheduleList.add(position, schedule);
                schedules.setValue(scheduleList);
            }
        }
    }

    public void updateSchedule(Schedule schedule) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<Schedule> scheduleList = schedules.getValue();
            if(scheduleList != null) {
                Schedule selectedSchedule = getScheduleById(schedule.getIdSchedule(), scheduleList);

                if(selectedSchedule != null) {
                    int index = scheduleList.indexOf(selectedSchedule);
                    scheduleList.set(index, schedule);
                    schedules.setValue(scheduleList);
                }
            }
        }
    }

    public void addSchedule(Schedule schedule) {
        List<Schedule> schedulesList = schedules.getValue();
        schedulesList.add(schedule);
        schedules.setValue(schedulesList);
    }
}

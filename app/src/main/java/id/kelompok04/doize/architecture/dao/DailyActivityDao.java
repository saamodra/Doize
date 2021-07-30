package id.kelompok04.doize.architecture.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.kelompok04.doize.model.DailyActivity;
import id.kelompok04.doize.model.Schedule;

public class DailyActivityDao {
    private MutableLiveData<List<DailyActivity>> dailyActivities = new MutableLiveData<>();

    public LiveData<List<DailyActivity>> getListDailyActivity() {
        return dailyActivities;
    }

    public void setListDailyActivity(List<DailyActivity> dailyActivities) {
        this.dailyActivities.setValue(dailyActivities);
    }
}

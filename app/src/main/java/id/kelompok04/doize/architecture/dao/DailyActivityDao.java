package id.kelompok04.doize.architecture.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.kelompok04.doize.model.DailyActivity;

public class DailyActivityDao {
    private static final String TAG = "DailyActivityDao";
    private MutableLiveData<List<DailyActivity>> dailyActivities = new MutableLiveData<>();

    public LiveData<List<DailyActivity>> getListDailyActivity() {
        return dailyActivities;
    }

    public void setListDailyActivity(List<DailyActivity> dailyActivities) {
        this.dailyActivities.setValue(dailyActivities);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private DailyActivity getDailyActivityById(int dailyActivityId, List<DailyActivity> dailyActivityList) {
        return dailyActivityList
                .stream()
                .filter(dailyActivity -> dailyActivity.getIdDailyActivity() == dailyActivityId)
                .findFirst()
                .orElse(null);
    }

    public void addToPosition(int position, DailyActivity dailyActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<DailyActivity> dailyActivityList = dailyActivities.getValue();
            if(dailyActivityList != null) {
                dailyActivityList.add(position, dailyActivity);
                dailyActivities.setValue(dailyActivityList);
            }
        }
    }

    public void deleteDailyActivity(int dailyActivityId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<DailyActivity> dailyActivityList = dailyActivities.getValue();
            if(dailyActivityList != null) {
                DailyActivity selectedDailyActivity = dailyActivityList
                        .stream().filter(dailyActivity -> dailyActivity.getIdDailyActivity() == dailyActivityId)
                        .findAny().orElse(null);

                if(selectedDailyActivity != null) {
                    dailyActivityList.remove(selectedDailyActivity);
                    dailyActivities.setValue(dailyActivityList);
                }
            }
        }
    }

    public void updateDailyActivity(DailyActivity dailyActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<DailyActivity> dailyActivityList = dailyActivities.getValue();
            if(dailyActivityList != null) {
                DailyActivity selectedDailyActivity = getDailyActivityById(dailyActivity.getIdDailyActivity(), dailyActivityList);

                if(selectedDailyActivity != null) {
                    int index = dailyActivityList.indexOf(selectedDailyActivity);
                    dailyActivityList.set(index, dailyActivity);
                    dailyActivities.setValue(dailyActivityList);
                }
            }
        }
    }

    public void addDailyActivity(DailyActivity dailyActivity) {
        List<DailyActivity> dailyActivityList = dailyActivities.getValue();
        dailyActivityList.add(dailyActivity);
        dailyActivities.setValue(dailyActivityList);
    }

}

package id.kelompok04.doize.architecture.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.kelompok04.doize.model.PomodoroActivity;

public class PomodoroActivityDao {
    private static final String TAG = "PomodoroActivityDao";
    private MutableLiveData<List<PomodoroActivity>> pomodoroActivities = new MutableLiveData<>();

    public LiveData<List<PomodoroActivity>> getListPomodoroActivity() {
        return pomodoroActivities;
    }

    public void setListPomodoroActivity(List<PomodoroActivity> pomodoroActivities) {
        this.pomodoroActivities.setValue(pomodoroActivities);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private PomodoroActivity getPomodoroActivityById(int pomodoroActivityId, List<PomodoroActivity> pomodoroActivityList) {
        return pomodoroActivityList
                .stream()
                .filter(pomodoroActivity -> pomodoroActivity.getIdPomodoroActivity() == pomodoroActivityId)
                .findFirst()
                .orElse(null);
    }

    public void deletePomodoroActivity(int pomodoroActivityId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<PomodoroActivity> pomodoroActivityList = pomodoroActivities.getValue();
            if(pomodoroActivityList != null) {
                PomodoroActivity selectedPomodoroActivity = pomodoroActivityList
                        .stream().filter(pomodoroActivity -> pomodoroActivity.getIdPomodoroActivity() == pomodoroActivityId)
                        .findAny().orElse(null);

                if(selectedPomodoroActivity != null) {
                    pomodoroActivityList.remove(selectedPomodoroActivity);
                    pomodoroActivities.setValue(pomodoroActivityList);
                }
            }
        }
    }

    public void addToPosition(int position, PomodoroActivity pomodoroActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<PomodoroActivity> pomodoroActivityList = pomodoroActivities.getValue();
            if(pomodoroActivityList != null) {
                pomodoroActivityList.add(position, pomodoroActivity);
                pomodoroActivities.setValue(pomodoroActivityList);
            }
        }
    }

    public void updatePomodoroActivity(PomodoroActivity pomodoroActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<PomodoroActivity> pomodoroActivityList = pomodoroActivities.getValue();
            if(pomodoroActivityList != null) {
                PomodoroActivity selectedPomodoroActivity = getPomodoroActivityById(pomodoroActivity.getIdPomodoroActivity(), pomodoroActivityList);

                if(selectedPomodoroActivity != null) {
                    int index = pomodoroActivityList.indexOf(selectedPomodoroActivity);
                    pomodoroActivityList.set(index, pomodoroActivity);
                    pomodoroActivities.setValue(pomodoroActivityList);
                }
            }
        }
    }

    public void addPomodoroActivity(PomodoroActivity pomodoroActivity) {
        List<PomodoroActivity> pomodoroActivitiesList = pomodoroActivities.getValue();
        pomodoroActivitiesList.add(pomodoroActivity);
        pomodoroActivities.setValue(pomodoroActivitiesList);
    }
}

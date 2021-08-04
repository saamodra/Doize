package id.kelompok04.doize.architecture.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import id.kelompok04.doize.model.Pomodoro;

public class PomodoroDao {
    private static final String TAG = "PomodoroDao";
    private MutableLiveData<Pomodoro> pomodoroUser = new MutableLiveData<>();

    public void setPomodoroUser(Pomodoro pomodoro) {
        pomodoroUser.setValue(pomodoro);
    }

    public LiveData<Pomodoro> getPomodoroUser() {
        return pomodoroUser;
    }
}

package id.kelompok04.doize.architecture.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.kelompok04.doize.architecture.repository.PomodoroActivityRepository;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.model.PomodoroActivity;
import id.kelompok04.doize.model.response.PomodoroActivityResponse;

public class PomodoroActivityViewModel extends ViewModel {
    private PomodoroActivityRepository mPomodoroActivityRepository;

    public PomodoroActivityViewModel() {
        mPomodoroActivityRepository = PomodoroActivityRepository.get();
    }

    public LiveData<List<PomodoroActivity>> getPomodoroActivites(int idPomodoro) {
        return mPomodoroActivityRepository.getPomodoroActivities(idPomodoro);
    }

    public LiveData<PomodoroActivityResponse> addPomodoroActivity(PomodoroActivity pomodoroActivity) {
        return mPomodoroActivityRepository.addPomodoroActivity(pomodoroActivity);
    }

    public LiveData<PomodoroActivityResponse> addToPosition(int position, PomodoroActivity pomodoroActivity) {
        return mPomodoroActivityRepository.updatePomodoroActivity(CrudType.ADD, position, pomodoroActivity);
    }

    public LiveData<PomodoroActivityResponse> updatePomodoroActivity(PomodoroActivity pomodoroActivity) {
        return mPomodoroActivityRepository.updatePomodoroActivity(CrudType.EDIT, -1, pomodoroActivity);
    }

    public LiveData<PomodoroActivityResponse> deletePomodoroActivity(int id) {
        return mPomodoroActivityRepository.deletePomodoroActivity(id);
    }
}

package id.kelompok04.doize.architecture.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import id.kelompok04.doize.architecture.repository.PomodoroRepository;
import id.kelompok04.doize.model.Pomodoro;
import id.kelompok04.doize.model.response.PomodoroResponse;

public class PomodoroViewModel extends ViewModel {
    private PomodoroRepository mPomodoroRepository;

    public PomodoroViewModel() {
        mPomodoroRepository = PomodoroRepository.get();
    }

    public LiveData<Pomodoro> getPomodoro(int idUser) {
        return mPomodoroRepository.getPomodoro(idUser);
    }

    public LiveData<PomodoroResponse> addPomodoro(int pomodoro) {
        return mPomodoroRepository.addPomodoro(pomodoro);
    }

    public LiveData<PomodoroResponse> updatePomodoro(Pomodoro pomodoro) {
        return mPomodoroRepository.updatePomodoro(pomodoro);
    }
}

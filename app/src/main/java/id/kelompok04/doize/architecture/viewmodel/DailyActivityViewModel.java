package id.kelompok04.doize.architecture.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.kelompok04.doize.architecture.repository.DailyActivityRepository;
import id.kelompok04.doize.model.DailyActivity;

public class DailyActivityViewModel extends ViewModel {
    private DailyActivityRepository mDailyActivityRepository;

    public DailyActivityViewModel() {
        mDailyActivityRepository = DailyActivityRepository.get();
    }

    public LiveData<List<DailyActivity>> getDailyActivities() {
        return mDailyActivityRepository.getDailyActivities();
    }
}

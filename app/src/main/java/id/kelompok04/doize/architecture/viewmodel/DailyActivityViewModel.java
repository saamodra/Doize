package id.kelompok04.doize.architecture.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.kelompok04.doize.architecture.repository.DailyActivityRepository;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.model.Assignment;
import id.kelompok04.doize.model.DailyActivity;
import id.kelompok04.doize.model.response.AssignmentResponse;
import id.kelompok04.doize.model.response.DailyActivityResponse;

public class DailyActivityViewModel extends ViewModel {
    private DailyActivityRepository mDailyActivityRepository;

    public DailyActivityViewModel() {
        mDailyActivityRepository = DailyActivityRepository.get();
    }

    public LiveData<List<DailyActivity>> getDailyActivities(int idUser) {
        return mDailyActivityRepository.getDailyActivities(idUser);
    }

    public LiveData<List<DailyActivity>> getDailyActivitiesOnce(int idUser) {
        return mDailyActivityRepository.getDailyActivitiesOnce(idUser);
    }

    public LiveData<DailyActivityResponse> addToPosition(int position, DailyActivity dailyActivity) {
        return mDailyActivityRepository.updateDailyActivity(CrudType.ADD, position, dailyActivity);
    }

    public LiveData<DailyActivityResponse> addDailyActivity(DailyActivity dailyActivity) {
        return mDailyActivityRepository.addDailyActivity(dailyActivity);
    }

    public LiveData<DailyActivityResponse> updateDailyActivity(int position, DailyActivity dailyActivity) {
        return mDailyActivityRepository.updateDailyActivity(CrudType.EDIT, position, dailyActivity);
    }

    public LiveData<DailyActivityResponse> deleteDailyActivity(int id) {
        return mDailyActivityRepository.deleteDailyActivity(id);
    }
}

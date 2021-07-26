package id.kelompok04.doize.architecture.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.kelompok04.doize.architecture.repository.DetailScheduleRepository;
import id.kelompok04.doize.model.DetailSchedule;

public class DetailScheduleViewModel extends ViewModel {
    private DetailScheduleRepository mDetailScheduleRepository;

    public DetailScheduleViewModel() {
        mDetailScheduleRepository = DetailScheduleRepository.get();
    }

    public LiveData<List<DetailSchedule>> getDetailSchedules() {
        return mDetailScheduleRepository.getDetailSchedules();
    }
}

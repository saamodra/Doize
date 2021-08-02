package id.kelompok04.doize.architecture.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.kelompok04.doize.architecture.repository.DetailScheduleRepository;
import id.kelompok04.doize.model.DetailSchedule;
import id.kelompok04.doize.model.response.DetailScheduleResponse;

public class DetailScheduleViewModel extends ViewModel {
    private DetailScheduleRepository mDetailScheduleRepository;

    public DetailScheduleViewModel() {
        mDetailScheduleRepository = DetailScheduleRepository.get();
    }

    public LiveData<List<List<DetailSchedule>>> getDetailSchedules(String idSchedule) {
        return mDetailScheduleRepository.getDetailSchedules(idSchedule);
    }

    public LiveData<DetailScheduleResponse> addDetailSchedule(DetailSchedule detailSchedule) {
        return mDetailScheduleRepository.addDetailSchedule(detailSchedule);
    }

    public LiveData<DetailScheduleResponse> deleteDetailSchedule(int id) {
        return mDetailScheduleRepository.deleteDetailSchedule(id);
    }

    public LiveData<DetailScheduleResponse> updateDetailSchedule(int dayBefore, DetailSchedule detailSchedule) {
        return mDetailScheduleRepository.updateDetailSchedule(dayBefore, detailSchedule);
    }
}

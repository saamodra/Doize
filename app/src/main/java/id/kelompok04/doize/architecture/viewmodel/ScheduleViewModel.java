package id.kelompok04.doize.architecture.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.kelompok04.doize.architecture.repository.ScheduleRepository;
import id.kelompok04.doize.model.Schedule;
import id.kelompok04.doize.model.response.ScheduleResponse;

public class ScheduleViewModel extends ViewModel {
    private ScheduleRepository mScheduleRepository;

    public ScheduleViewModel() {
        mScheduleRepository = ScheduleRepository.get();
    }

    public LiveData<List<Schedule>> getSchedules() {
        return mScheduleRepository.getSchedules();
    }

    public LiveData<ScheduleResponse> addSchedule(Schedule schedule) {
        return mScheduleRepository.addSchedule(schedule);
    }


}

package id.kelompok04.doize.architecture.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.kelompok04.doize.architecture.repository.ScheduleRepository;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.model.Schedule;
import id.kelompok04.doize.model.response.ScheduleResponse;

public class ScheduleViewModel extends ViewModel {
    private ScheduleRepository mScheduleRepository;

    public ScheduleViewModel() {
        mScheduleRepository = ScheduleRepository.get();
    }

    public LiveData<List<Schedule>> getSchedules(int idUser) {
        return mScheduleRepository.getSchedules(idUser);
    }

    public LiveData<List<Schedule>> getSchedulesDayUser(int idUser) {
        return mScheduleRepository.getSchedulesDayUser(idUser);
    }

    public LiveData<ScheduleResponse> addSchedule(Schedule schedule) {
        return mScheduleRepository.addSchedule(schedule);
    }

    public LiveData<ScheduleResponse> addToPosition(int position, Schedule schedule) {
        return mScheduleRepository.updateSchedule(CrudType.ADD, position, schedule);
    }

    public LiveData<ScheduleResponse> updateSchedule(Schedule schedule) {
        return mScheduleRepository.updateSchedule(CrudType.EDIT, -1, schedule);
    }

    public LiveData<ScheduleResponse> deleteSchedule(int id) {
        return mScheduleRepository.deleteSchedule(id);
    }
}

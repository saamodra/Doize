package id.kelompok04.doize.architecture.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.kelompok04.doize.model.Schedule;

public class ScheduleDao {
    private MutableLiveData<List<Schedule>> listSchedule = new MutableLiveData<>();

    public LiveData<List<Schedule>> getListSchedule() {
        return listSchedule;
    }

    public void setListSchedule(List<Schedule> listSchedule) {
        this.listSchedule.setValue(listSchedule);
    }
}

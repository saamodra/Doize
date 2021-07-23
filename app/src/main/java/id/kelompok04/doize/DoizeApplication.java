package id.kelompok04.doize;

import android.app.Application;

import id.kelompok04.doize.architecture.repository.ScheduleRepository;
import id.kelompok04.doize.architecture.repository.UserRepository;

public class DoizeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UserRepository.initialize(this);
        ScheduleRepository.initialize(this);
    }
}

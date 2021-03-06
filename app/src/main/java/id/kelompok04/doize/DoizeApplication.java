package id.kelompok04.doize;

import android.app.Application;

import id.kelompok04.doize.architecture.repository.AssignmentRepository;
import id.kelompok04.doize.architecture.repository.DailyActivityRepository;
import id.kelompok04.doize.architecture.repository.DetailScheduleRepository;
import id.kelompok04.doize.architecture.repository.PomodoroActivityRepository;
import id.kelompok04.doize.architecture.repository.PomodoroRepository;
import id.kelompok04.doize.architecture.repository.ScheduleRepository;
import id.kelompok04.doize.architecture.repository.UserRepository;

public class DoizeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UserRepository.initialize(this);
        ScheduleRepository.initialize(this);
        DetailScheduleRepository.initialize(this);
        AssignmentRepository.initialize(this);
        DailyActivityRepository.initialize(this);
        PomodoroRepository.initialize(this);
        PomodoroActivityRepository.initialize(this);
    }
}

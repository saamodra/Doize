package id.kelompok04.doize.architecture.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.kelompok04.doize.api.ApiUtils;
import id.kelompok04.doize.architecture.dao.ScheduleDao;
import id.kelompok04.doize.model.Schedule;
import id.kelompok04.doize.model.response.ListScheduleResponse;
import id.kelompok04.doize.service.ScheduleService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleRepository {
    private static final String TAG = "ScheduleRepository";

    private static ScheduleRepository INSTANCE;
    private ScheduleService mScheduleService;
    private static ScheduleDao mScheduleDao;

    private ScheduleRepository(Context context) {
        mScheduleService = ApiUtils.getScheduleService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            mScheduleDao = new ScheduleDao();
            INSTANCE = new ScheduleRepository(context);
        }
    }

    public static ScheduleRepository get() {
        return INSTANCE;
    }

    public LiveData<List<Schedule>> getSchedules() {
        Log.d(TAG, "getUsers: Called");
        Call<ListScheduleResponse> call = mScheduleService.getSchedules();
        call.enqueue(new Callback<ListScheduleResponse>() {
            @Override
            public void onResponse(Call<ListScheduleResponse> call, Response<ListScheduleResponse> response) {
                ListScheduleResponse listSchedule = response.body();
                Log.d(TAG, "onResponse: " + listSchedule);
                if (listSchedule.getStatus() == 200) {
                    mScheduleDao.setListSchedule(listSchedule.getData());
                }
            }

            @Override
            public void onFailure(Call<ListScheduleResponse> call, Throwable t) {

            }
        });

        return mScheduleDao.getListSchedule();
    }
}

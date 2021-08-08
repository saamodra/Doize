package id.kelompok04.doize.architecture.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.kelompok04.doize.api.ApiUtils;
import id.kelompok04.doize.architecture.dao.ScheduleDao;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.model.Schedule;
import id.kelompok04.doize.model.response.ListScheduleResponse;
import id.kelompok04.doize.model.response.ScheduleResponse;
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

    public LiveData<List<Schedule>> getSchedules(int idUser) {
        Log.d(TAG, "getSchedules: Called");
        Call<ListScheduleResponse> call = mScheduleService.getSchedules(idUser);
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
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return mScheduleDao.getListSchedule();
    }

    public LiveData<List<Schedule>> getSchedulesDayUser(int idUser) {
        Log.d(TAG, "getSchedules: Called");
        Call<ListScheduleResponse> call = mScheduleService.getSchedulesDayUser(idUser);
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
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return mScheduleDao.getListSchedule();
    }

    public LiveData<ScheduleResponse> addSchedule(Schedule schedule) {
        MutableLiveData<ScheduleResponse> scheduleResponseMutableLiveData = new MutableLiveData<>();

        Log.i(TAG, "addSchedule: " + schedule);
        Call<ScheduleResponse> call = mScheduleService.addSchedule(schedule);
        call.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                ScheduleResponse scheduleResponse = response.body();
                scheduleResponseMutableLiveData.setValue(scheduleResponse);

                if (scheduleResponse.getStatus() == 200) {
                    mScheduleDao.addSchedule(scheduleResponse.getData());
                }
            }

            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return scheduleResponseMutableLiveData;
    }

    public LiveData<ScheduleResponse> updateSchedule(CrudType crudType, int position, Schedule schedule) {
        MutableLiveData<ScheduleResponse> scheduleResponseMutableLiveData = new MutableLiveData<>();

        Log.i(TAG, "updateSchedule: " + schedule);
        Call<ScheduleResponse> call = mScheduleService.updateSchedule(schedule.getIdSchedule(), schedule);
        call.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                ScheduleResponse scheduleResponse = response.body();
                scheduleResponseMutableLiveData.setValue(scheduleResponse);

                if (scheduleResponse.getStatus() == 200) {
                    if (crudType == CrudType.ADD) {
                        mScheduleDao.addToPosition(position, schedule);
                    } else {
                        mScheduleDao.updateSchedule(scheduleResponse.getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return scheduleResponseMutableLiveData;
    }

    public LiveData<ScheduleResponse> deleteSchedule(int id) {
        MutableLiveData<ScheduleResponse> scheduleResponseMutableLiveData = new MutableLiveData<>();

        Call<ScheduleResponse> call = mScheduleService.deleteScheduleById(id);
        call.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                ScheduleResponse scheduleResponse = response.body();
                scheduleResponseMutableLiveData.setValue(scheduleResponse);

                if (scheduleResponse.getStatus() == 200) {
                    mScheduleDao.deleteSchedule(id);
                }
            }

            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return scheduleResponseMutableLiveData;
    }
}

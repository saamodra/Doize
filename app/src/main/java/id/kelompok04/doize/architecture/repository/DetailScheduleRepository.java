package id.kelompok04.doize.architecture.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.kelompok04.doize.api.ApiUtils;
import id.kelompok04.doize.architecture.dao.DetailScheduleDao;
import id.kelompok04.doize.helper.DoizeConstants;
import id.kelompok04.doize.model.DetailSchedule;
import id.kelompok04.doize.model.Schedule;
import id.kelompok04.doize.model.response.DetailScheduleResponse;
import id.kelompok04.doize.model.response.ListDetailScheduleResponse;
import id.kelompok04.doize.model.response.ScheduleResponse;
import id.kelompok04.doize.service.DetailScheduleService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailScheduleRepository {
    private static final String TAG = "DetailScheduleRepository";

    private static DetailScheduleRepository INSTANCE;
    private DetailScheduleService mDetailScheduleService;
    private static DetailScheduleDao mDetailScheduleDao;

    private DetailScheduleRepository(Context context) {
        mDetailScheduleService = ApiUtils.getDetailScheduleService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            mDetailScheduleDao = new DetailScheduleDao();
            INSTANCE = new DetailScheduleRepository(context);
        }
    }

    public static DetailScheduleRepository get() {
        return INSTANCE;
    }

    @SuppressLint("LongLogTag")
    public LiveData<List<List<DetailSchedule>>> getDetailSchedules(String idSchedule) {
        Log.d(TAG, "getDetailSchedules: Called");
        Call<ListDetailScheduleResponse> call = mDetailScheduleService.getDetailSchedules(idSchedule);
        call.enqueue(new Callback<ListDetailScheduleResponse>() {
            @Override
            public void onResponse(Call<ListDetailScheduleResponse> call, Response<ListDetailScheduleResponse> response) {
                ListDetailScheduleResponse listDetailSchedule = response.body();
                Log.d(TAG, "onResponse: " + listDetailSchedule);
                if (listDetailSchedule.getStatus() == 200) {
                    mDetailScheduleDao.setDetailSchedules(listDetailSchedule.getData());
                }
            }

            @Override
            public void onFailure(Call<ListDetailScheduleResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return mDetailScheduleDao.getDetailSchedules();
    }

    @SuppressLint("LongLogTag")
    public LiveData<DetailScheduleResponse> addDetailSchedule(DetailSchedule detailSchedule) {
        MutableLiveData<DetailScheduleResponse> detailScheduleResponseMutableLiveData = new MutableLiveData<>();

        Log.i(TAG, "addDetailSchedule: " + detailSchedule);
        Call<DetailScheduleResponse> call = mDetailScheduleService.addDetailSchedule(detailSchedule);
        call.enqueue(new Callback<DetailScheduleResponse>() {
            @Override
            public void onResponse(Call<DetailScheduleResponse> call, Response<DetailScheduleResponse> response) {
                DetailScheduleResponse detailScheduleResponse = response.body();

                if (detailScheduleResponse.getStatus() == 200) {
                    int day = DoizeConstants.DAY_LIST.indexOf(detailSchedule.getDaySchedule());
                    mDetailScheduleDao.addDetailSchedule(day, detailScheduleResponse.getDetailSchedule());
                    detailScheduleResponseMutableLiveData.setValue(detailScheduleResponse);
                }
            }

            @Override
            public void onFailure(Call<DetailScheduleResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return detailScheduleResponseMutableLiveData;
    }

    @SuppressLint("LongLogTag")
    public LiveData<DetailScheduleResponse> updateDetailSchedule(int dayBefore, DetailSchedule detailSchedule) {
        MutableLiveData<DetailScheduleResponse> detailScheduleResponseMutableLiveData = new MutableLiveData<>();

        Log.i(TAG, "editDetailSchedule: " + detailSchedule);
        Call<DetailScheduleResponse> call = mDetailScheduleService.updateDetailSchedule(detailSchedule.getIdDetailSchedule(), detailSchedule);
        call.enqueue(new Callback<DetailScheduleResponse>() {
            @Override
            public void onResponse(Call<DetailScheduleResponse> call, Response<DetailScheduleResponse> response) {
                DetailScheduleResponse detailScheduleResponse = response.body();

                if (detailScheduleResponse.getStatus() == 200) {
                    mDetailScheduleDao.updateDetailSchedule(dayBefore, detailScheduleResponse.getDetailSchedule());
                    detailScheduleResponseMutableLiveData.setValue(detailScheduleResponse);
                }
            }

            @Override
            public void onFailure(Call<DetailScheduleResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return detailScheduleResponseMutableLiveData;
    }

    @SuppressLint("LongLogTag")
    public LiveData<DetailScheduleResponse> deleteDetailSchedule(int id) {
        MutableLiveData<DetailScheduleResponse> detailScheduleResponseMutableLiveData = new MutableLiveData<>();

        Call<DetailScheduleResponse> call = mDetailScheduleService.deleteDetailScheduleById(id);
        call.enqueue(new Callback<DetailScheduleResponse>() {
            @Override
            public void onResponse(Call<DetailScheduleResponse> call, Response<DetailScheduleResponse> response) {
                DetailScheduleResponse detailScheduleResponse = response.body();

                if (detailScheduleResponse.getStatus() == 200) {
                    DetailSchedule detailSchedule = detailScheduleResponse.getDetailSchedule();
                    int day = DoizeConstants.DAY_LIST.indexOf(detailSchedule.getDaySchedule());
                    mDetailScheduleDao.deleteDetailSchedule(day, detailSchedule.getIdDetailSchedule());

                    detailScheduleResponseMutableLiveData.setValue(detailScheduleResponse);
                }
            }

            @Override
            public void onFailure(Call<DetailScheduleResponse> call, Throwable t) {

            }
        });

        return detailScheduleResponseMutableLiveData;
    }


}

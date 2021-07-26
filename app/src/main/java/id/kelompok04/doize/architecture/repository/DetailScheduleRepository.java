package id.kelompok04.doize.architecture.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.kelompok04.doize.api.ApiUtils;
import id.kelompok04.doize.model.DetailSchedule;
import id.kelompok04.doize.model.response.ListDetailScheduleResponse;
import id.kelompok04.doize.service.DetailScheduleService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailScheduleRepository {
    private static final String TAG = "DetailScheduleRepository";

    private static DetailScheduleRepository INSTANCE;
    private DetailScheduleService mDetailScheduleService;

    private DetailScheduleRepository(Context context) {
        mDetailScheduleService = ApiUtils.getDetailScheduleService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
//            mScheduleDao = new ScheduleDao();
            INSTANCE = new DetailScheduleRepository(context);
        }
    }

    public static DetailScheduleRepository get() {
        return INSTANCE;
    }

    @SuppressLint("LongLogTag")
    public LiveData<List<DetailSchedule>> getDetailSchedules() {
        Log.d(TAG, "getDetailSchedules: Called");
        Call<ListDetailScheduleResponse> call = mDetailScheduleService.getDetailSchedules();
        call.enqueue(new Callback<ListDetailScheduleResponse>() {
            @Override
            public void onResponse(Call<ListDetailScheduleResponse> call, Response<ListDetailScheduleResponse> response) {
                ListDetailScheduleResponse listDetailSchedule = response.body();
                Log.d(TAG, "onResponse: " + listDetailSchedule);
//                if (listDetailSchedule.getStatus() == 200) {
//                    mDetailScheduleDao.setListDetailSchedule(listDetailSchedule.getData());
//                }
            }

            @Override
            public void onFailure(Call<ListDetailScheduleResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return null;
    }
}

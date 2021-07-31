package id.kelompok04.doize.architecture.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.kelompok04.doize.api.ApiUtils;
import id.kelompok04.doize.architecture.dao.DailyActivityDao;
import id.kelompok04.doize.model.DailyActivity;
import id.kelompok04.doize.model.response.DailyActivityResponse;
import id.kelompok04.doize.model.response.ListDailyActivityResponse;
import id.kelompok04.doize.service.DailyActivityService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyActivityRepository {
    private static final String TAG = "DailyActivityRepository";

    private static DailyActivityRepository INSTANCE;
    private DailyActivityService mDailyActivityService;
    private static DailyActivityDao mDailyActivityDao;

    private DailyActivityRepository(Context context) {
        mDailyActivityService = ApiUtils.getDailyActivityService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            mDailyActivityDao = new DailyActivityDao();
            INSTANCE = new DailyActivityRepository(context);
        }
    }

    public static DailyActivityRepository get() {
        return INSTANCE;
    }

    public LiveData<List<DailyActivity>> getDailyActivities() {
        Log.d(TAG, "getDailyActivities: Called");
        Call<ListDailyActivityResponse> call = mDailyActivityService.getDailyActivities();
        call.enqueue(new Callback<ListDailyActivityResponse>() {
            @Override
            public void onResponse(Call<ListDailyActivityResponse> call, Response<ListDailyActivityResponse> response) {
                ListDailyActivityResponse listActivity = response.body();
                Log.d(TAG, "onResponse: " + listActivity);
                if (listActivity.getStatus() == 200) {
                    mDailyActivityDao.setListDailyActivity(listActivity.getData());
                }
            }

            @Override
            public void onFailure(Call<ListDailyActivityResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return mDailyActivityDao.getListDailyActivity();
    }

    public LiveData<DailyActivityResponse> updateDailyActivity(DailyActivity dailyActivity) {
        MutableLiveData<DailyActivityResponse> dailyActivityResponseMutableLiveData = new MutableLiveData<>();

        Log.d(TAG, "updateDailyActivity: Called");
        Call<DailyActivityResponse> call = mDailyActivityService.updateDailyActivity(dailyActivity.getIdDailyActivity(), dailyActivity);
        call.enqueue(new Callback<DailyActivityResponse>() {
            @Override
            public void onResponse(Call<DailyActivityResponse> call, Response<DailyActivityResponse> response) {
                DailyActivityResponse dailyActivityResponse = response.body();
                dailyActivityResponseMutableLiveData.setValue(dailyActivityResponse);
                Log.d(TAG, "onResponse: " + dailyActivityResponse);

                if (dailyActivityResponse.getStatus() == 200) {
                    mDailyActivityDao.updateDailyActivity(dailyActivity);
                }
            }

            @Override
            public void onFailure(Call<DailyActivityResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return dailyActivityResponseMutableLiveData;
    }
}

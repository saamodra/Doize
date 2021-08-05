package id.kelompok04.doize.architecture.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.kelompok04.doize.api.ApiUtils;
import id.kelompok04.doize.architecture.dao.PomodoroActivityDao;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.model.PomodoroActivity;
import id.kelompok04.doize.model.response.ListPomodoroActivityResponse;
import id.kelompok04.doize.model.response.PomodoroActivityResponse;
import id.kelompok04.doize.service.PomodoroActivityService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PomodoroActivityRepository {
    private static final String TAG = "PomodoroActivityRepository";

    private static PomodoroActivityRepository INSTANCE;
    private PomodoroActivityService mPomodoroActivityService;
    private static PomodoroActivityDao mPomodoroActivityDao;

    private PomodoroActivityRepository(Context context) {
        mPomodoroActivityService = ApiUtils.getPomodoroActivityService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            mPomodoroActivityDao = new PomodoroActivityDao();
            INSTANCE = new PomodoroActivityRepository(context);
        }
    }

    public static PomodoroActivityRepository get() {
        return INSTANCE;
    }

    @SuppressLint("LongLogTag")
    public LiveData<List<PomodoroActivity>> getPomodoroActivities(int idPomodoro) {
        Log.d(TAG, "getPomodoroActivitys: Called");
        Call<ListPomodoroActivityResponse> call = mPomodoroActivityService.getPomodoroByActivity(idPomodoro);
        call.enqueue(new Callback<ListPomodoroActivityResponse>() {
            @Override
            public void onResponse(Call<ListPomodoroActivityResponse> call, Response<ListPomodoroActivityResponse> response) {
                ListPomodoroActivityResponse listPomodoroActivity = response.body();
                Log.d(TAG, "onResponse: " + listPomodoroActivity);
                if (listPomodoroActivity.getStatus() == 200) {
                    mPomodoroActivityDao.setListPomodoroActivity(listPomodoroActivity.getData());
                }
            }

            @Override
            public void onFailure(Call<ListPomodoroActivityResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return mPomodoroActivityDao.getListPomodoroActivity();
    }

    @SuppressLint("LongLogTag")
    public LiveData<PomodoroActivityResponse> addPomodoroActivity(PomodoroActivity pomodoroActivity) {
        MutableLiveData<PomodoroActivityResponse> pomodoroActivityResponseMutableLiveData = new MutableLiveData<>();

        Log.i(TAG, "addPomodoroActivity: " + pomodoroActivity);
        Call<PomodoroActivityResponse> call = mPomodoroActivityService.savePomodoroActivity(pomodoroActivity);
        call.enqueue(new Callback<PomodoroActivityResponse>() {
            @Override
            public void onResponse(Call<PomodoroActivityResponse> call, Response<PomodoroActivityResponse> response) {
                PomodoroActivityResponse pomodoroActivityResponse = response.body();
                pomodoroActivityResponseMutableLiveData.setValue(pomodoroActivityResponse);

                if (pomodoroActivityResponse.getStatus() == 200) {
                    mPomodoroActivityDao.addPomodoroActivity(pomodoroActivityResponse.getData());
                }
            }

            @Override
            public void onFailure(Call<PomodoroActivityResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return pomodoroActivityResponseMutableLiveData;
    }

    @SuppressLint("LongLogTag")
    public LiveData<PomodoroActivityResponse> updatePomodoroActivity(CrudType crudType, int position, PomodoroActivity pomodoroActivity) {
        MutableLiveData<PomodoroActivityResponse> pomodoroActivityResponseMutableLiveData = new MutableLiveData<>();

        Log.i(TAG, "updatePomodoroActivity: " + pomodoroActivity);
        Call<PomodoroActivityResponse> call = mPomodoroActivityService.updatePomodoroActivity(pomodoroActivity.getIdPomodoroActivity(), pomodoroActivity);
        call.enqueue(new Callback<PomodoroActivityResponse>() {
            @Override
            public void onResponse(Call<PomodoroActivityResponse> call, Response<PomodoroActivityResponse> response) {
                PomodoroActivityResponse pomodoroActivityResponse = response.body();
                pomodoroActivityResponseMutableLiveData.setValue(pomodoroActivityResponse);

                if (pomodoroActivityResponse.getStatus() == 200) {
                    if (crudType == CrudType.ADD) {
                        mPomodoroActivityDao.addToPosition(position, pomodoroActivity);
                    } else {
                        mPomodoroActivityDao.updatePomodoroActivity(pomodoroActivityResponse.getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<PomodoroActivityResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return pomodoroActivityResponseMutableLiveData;
    }

    public LiveData<PomodoroActivityResponse> deletePomodoroActivity(int id) {
        MutableLiveData<PomodoroActivityResponse> pomodoroActivityResponseMutableLiveData = new MutableLiveData<>();

        Call<PomodoroActivityResponse> call = mPomodoroActivityService.deletePomodoroActivity(id);
        call.enqueue(new Callback<PomodoroActivityResponse>() {
            @Override
            public void onResponse(Call<PomodoroActivityResponse> call, Response<PomodoroActivityResponse> response) {
                PomodoroActivityResponse pomodoroActivityResponse = response.body();
                pomodoroActivityResponseMutableLiveData.setValue(pomodoroActivityResponse);

                if (pomodoroActivityResponse.getStatus() == 200) {
                    mPomodoroActivityDao.deletePomodoroActivity(id);
                }
            }

            @Override
            public void onFailure(Call<PomodoroActivityResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return pomodoroActivityResponseMutableLiveData;
    }
}

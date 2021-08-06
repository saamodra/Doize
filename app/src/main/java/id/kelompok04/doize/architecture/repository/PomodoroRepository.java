package id.kelompok04.doize.architecture.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.kelompok04.doize.api.ApiUtils;
import id.kelompok04.doize.architecture.dao.PomodoroDao;
import id.kelompok04.doize.model.Assignment;
import id.kelompok04.doize.model.Pomodoro;
import id.kelompok04.doize.model.response.ListAssignmentResponse;
import id.kelompok04.doize.model.response.PomodoroResponse;
import id.kelompok04.doize.service.PomodoroService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PomodoroRepository {
    private static final String TAG = "PomodoroRepository";

    private static PomodoroRepository INSTANCE;
    private PomodoroService mPomodoroService;
    private static PomodoroDao mPomodoroDao;

    private PomodoroRepository(Context context) {
        mPomodoroService = ApiUtils.getPomodoroService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            mPomodoroDao = new PomodoroDao();
            INSTANCE = new PomodoroRepository(context);
        }
    }

    public static PomodoroRepository get() {
        return INSTANCE;
    }

    public LiveData<Pomodoro> getPomodoro(int idUser) {

        Log.d(TAG, "getPomodoro: Called");
        Call<PomodoroResponse> call = mPomodoroService.getPomodoroByUser(idUser);
        call.enqueue(new Callback<PomodoroResponse>() {
            @Override
            public void onResponse(Call<PomodoroResponse> call, Response<PomodoroResponse> response) {
                PomodoroResponse pomodoro = response.body();
                Log.d(TAG, "onResponse: " + pomodoro);
                if (pomodoro.getStatus() == 200) {
                    mPomodoroDao.setPomodoroUser(pomodoro.getData());
                }
            }

            @Override
            public void onFailure(Call<PomodoroResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return mPomodoroDao.getPomodoroUser();
    }

    public LiveData<PomodoroResponse> addPomodoro(int idUser) {
        MutableLiveData<PomodoroResponse> pomodoroResponseMutableLiveData = new MutableLiveData<>();

        Log.d(TAG, "getPomodoro: Called");
        Call<PomodoroResponse> call = mPomodoroService.savePomodoro(idUser);
        call.enqueue(new Callback<PomodoroResponse>() {
            @Override
            public void onResponse(Call<PomodoroResponse> call, Response<PomodoroResponse> response) {
                PomodoroResponse pomodoro = response.body();
                Log.d(TAG, "onResponse: " + pomodoro);
                if (pomodoro.getStatus() == 200) {
                    pomodoroResponseMutableLiveData.setValue(response.body());
                    mPomodoroDao.setPomodoroUser(pomodoro.getData());
                }
            }

            @Override
            public void onFailure(Call<PomodoroResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return pomodoroResponseMutableLiveData;
    }

    public LiveData<PomodoroResponse> updatePomodoro(Pomodoro pomodoro) {
        MutableLiveData<PomodoroResponse> pomodoroResponseMutableLiveData = new MutableLiveData<>();

        Log.d(TAG, "getPomodoro: Called");
        Call<PomodoroResponse> call = mPomodoroService.updatePomodoro(pomodoro.getIdPomodoro(), pomodoro);
        call.enqueue(new Callback<PomodoroResponse>() {
            @Override
            public void onResponse(Call<PomodoroResponse> call, Response<PomodoroResponse> response) {
                PomodoroResponse pomodoro = response.body();
                Log.d(TAG, "onResponse: " + pomodoro);
                if (pomodoro.getStatus() == 200) {
                    pomodoroResponseMutableLiveData.setValue(response.body());
                    mPomodoroDao.setPomodoroUser(pomodoro.getData());
                }
            }

            @Override
            public void onFailure(Call<PomodoroResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return pomodoroResponseMutableLiveData;
    }
}

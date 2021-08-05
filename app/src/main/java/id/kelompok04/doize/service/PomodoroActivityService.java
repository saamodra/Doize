package id.kelompok04.doize.service;

import id.kelompok04.doize.model.PomodoroActivity;
import id.kelompok04.doize.model.response.ListPomodoroActivityResponse;
import id.kelompok04.doize.model.response.PomodoroActivityResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PomodoroActivityService {
    @GET("pomodoro-activity/{id}")
    Call<ListPomodoroActivityResponse> getPomodoroByActivity(@Path("id") int idPomodoro);

    @POST("pomodoro-activity")
    Call<PomodoroActivityResponse> savePomodoroActivity(@Body PomodoroActivity pomodoroActivity);

    @PUT("pomodoro-activity/{id}")
    Call<PomodoroActivityResponse> updatePomodoroActivity(@Path("id") int idPomodoroAct, @Body PomodoroActivity pomodoroActivity);

    @DELETE("pomodoro-activity/{id}")
    Call<PomodoroActivityResponse> deletePomodoroActivity(@Path("id") int idPomodoroAct);
}

package id.kelompok04.doize.service;

import id.kelompok04.doize.model.Pomodoro;
import id.kelompok04.doize.model.response.ListDetailScheduleResponse;
import id.kelompok04.doize.model.response.PomodoroResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PomodoroService {
    @GET("pomodoro/{id}")
    Call<PomodoroResponse> getPomodoroByUser(@Path("id") int idUser);

    @POST("pomodoro")
    @FormUrlEncoded
    Call<PomodoroResponse> savePomodoro(@Field("id_user") int idUser);

    @PUT("pomodoro/{id}")
    Call<PomodoroResponse> updatePomodoro(@Path("id") int idUser, @Body Pomodoro pomodoro);
}

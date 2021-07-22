package id.kelompok04.doize.service;

import id.kelompok04.doize.model.Schedule;
import id.kelompok04.doize.model.response.ListScheduleResponse;
import id.kelompok04.doize.model.response.ScheduleResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ScheduleService {
    @GET("schedule")
    Call<ListScheduleResponse> getSchedules();

    @GET("schedule/{id}")
    Call<ScheduleResponse> getScheduleById(@Path("id") String id);

    @POST("schedule")
    Call<ScheduleResponse> addSchedule(@Body Schedule schedule);

    @PUT("schedule")
    Call<ScheduleResponse> updateSchedule(@Body Schedule schedule);

    @DELETE("schedule")
    Call<ScheduleResponse> deleteScheduleById(@Query("id") String id);
}

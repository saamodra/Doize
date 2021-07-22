package id.kelompok04.doize.service;

import id.kelompok04.doize.model.Schedule;
import id.kelompok04.doize.model.response.ListScheduleResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ScheduleService {
    @GET("schedule")
    Call<ListScheduleResponse> getSchedules();

    @GET("schedule/{id}")
    Call<Schedule> getScheduleById(@Path("id") String id);

    @PUT("schedule")
    Call<Schedule> updateSchedule(@Body Schedule schedule);

    @DELETE("schedule")
    Call<Schedule> deleteScheduleById(@Query("id") String id);
}

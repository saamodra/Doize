package id.kelompok04.doize.service;

import id.kelompok04.doize.model.DetailSchedule;
import id.kelompok04.doize.model.response.DetailScheduleResponse;
import id.kelompok04.doize.model.response.ListDetailScheduleResponse;
import id.kelompok04.doize.model.response.ScheduleResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DetailScheduleService {
    @GET("detail-schedule")
    Call<ListDetailScheduleResponse> getDetailSchedules(@Query("schedule") String idSchedule);

    @POST("detail-schedule")
    Call<DetailScheduleResponse> addDetailSchedule(@Body DetailSchedule detailSchedule);

    @PUT("detail-schedule/{id}")
    Call<DetailScheduleResponse> updateDetailSchedule(@Path("id") int id, @Body DetailSchedule detailSchedule);

    @DELETE("detail-schedule/{id}")
    Call<DetailScheduleResponse> deleteDetailScheduleById(@Path("id") int id);
}

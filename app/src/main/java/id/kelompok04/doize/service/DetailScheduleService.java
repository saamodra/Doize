package id.kelompok04.doize.service;

import id.kelompok04.doize.model.response.ListDetailScheduleResponse;
import id.kelompok04.doize.model.response.ScheduleResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DetailScheduleService {
    @GET("detail-schedule")
    Call<ListDetailScheduleResponse> getDetailSchedules(@Query("schedule") String idSchedule);

}

package id.kelompok04.doize.service;

import id.kelompok04.doize.model.response.ListDetailScheduleResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DetailScheduleService {
    @GET("detail-schedule")
    Call<ListDetailScheduleResponse> getDetailSchedules();
}

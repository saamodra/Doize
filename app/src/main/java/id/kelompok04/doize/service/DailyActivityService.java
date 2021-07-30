package id.kelompok04.doize.service;

import id.kelompok04.doize.model.response.ListDailyActivityResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DailyActivityService {
    @GET("daily-activity")
    Call<ListDailyActivityResponse> getDailyActivities();
}

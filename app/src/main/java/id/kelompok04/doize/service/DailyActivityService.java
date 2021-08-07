package id.kelompok04.doize.service;

import id.kelompok04.doize.model.DailyActivity;
import id.kelompok04.doize.model.response.DailyActivityResponse;
import id.kelompok04.doize.model.response.ListDailyActivityResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DailyActivityService {
    @GET("daily-activities/{id}")
    Call<ListDailyActivityResponse> getDailyActivities(@Path("id") int id);

    @POST("daily-activity")
    Call<DailyActivityResponse> addDailyActivity(@Body DailyActivity dailyActivity);

    @PUT("daily-activity/{id}")
    Call<DailyActivityResponse> updateDailyActivity(@Path("id") int id, @Body DailyActivity dailyActivity);

    @DELETE("daily-activity/{id}")
    Call<DailyActivityResponse> deleteDailyActivity(@Path("id") int id);
}

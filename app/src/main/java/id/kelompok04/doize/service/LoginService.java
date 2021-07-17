package id.kelompok04.doize.service;

import java.util.List;

import id.kelompok04.doize.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginService {
    @GET("login")
    Call<User> login(@Query("email") String email, @Query("password") String password);
    
}

package id.kelompok04.doize.service;

import java.util.List;

import id.kelompok04.doize.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserService {
    @GET("user")
    Call<User> getUserById(@Query("id") String id);

    @GET("users")
    Call<List<User>> getUsers();

    @POST("user")
    Call<User> addUser(@Body User user);

    @PUT("user")
    Call<User> updateUser(@Body User user);

    @DELETE("user")
    Call<User> deleteUserById(@Query("id") String id);
}

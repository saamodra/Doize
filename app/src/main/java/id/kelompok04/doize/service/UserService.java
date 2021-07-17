package id.kelompok04.doize.service;

import java.util.List;

import id.kelompok04.doize.model.response.ListUserResponse;
import id.kelompok04.doize.model.response.LoginResponse;
import id.kelompok04.doize.model.response.RequestResponse;
import id.kelompok04.doize.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @GET("user/{id}")
    Call<User> getUserById(@Path("id") String id);

    @GET("user")
    Call<ListUserResponse> getUsers();

    @POST("user")
    Call<User> addUser(@Body User user);

    @PUT("user")
    Call<User> updateUser(@Body User user);

    @DELETE("user")
    Call<User> deleteUserById(@Query("id") String id);

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);
}

package id.kelompok04.doize.service;

import id.kelompok04.doize.model.Assignment;
import id.kelompok04.doize.model.DetailSchedule;
import id.kelompok04.doize.model.response.AssignmentResponse;
import id.kelompok04.doize.model.response.ListAssignmentResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AssignmentService {
    @GET("assignments/{id}")
    Call<ListAssignmentResponse> getAssignments(@Path("id") int idUser);

    @POST("assignment")
    Call<AssignmentResponse> addAssignment(@Body Assignment assignment);

    @PUT("assignment/{id}")
    Call<AssignmentResponse> updateAssignment(@Path("id") int id, @Body Assignment assignment);

    @DELETE("assignment/{id}")
    Call<AssignmentResponse> deleteAssignment(@Path("id") int id);
}

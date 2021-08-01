package id.kelompok04.doize.architecture.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.kelompok04.doize.api.ApiUtils;
import id.kelompok04.doize.architecture.dao.AssignmentDao;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.model.Assignment;
import id.kelompok04.doize.model.response.AssignmentResponse;
import id.kelompok04.doize.model.response.ListAssignmentResponse;
import id.kelompok04.doize.model.response.ScheduleResponse;
import id.kelompok04.doize.service.AssignmentService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentRepository {
    private static final String TAG = "AssignmentRepository";

    private static AssignmentRepository INSTANCE;
    private AssignmentService mAssignmentService;
    private static AssignmentDao mAssignmentDao;

    private AssignmentRepository(Context context) {
        mAssignmentService = ApiUtils.getAssignmentService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            mAssignmentDao = new AssignmentDao();
            INSTANCE = new AssignmentRepository(context);
        }
    }

    public static AssignmentRepository get() {
        return INSTANCE;
    }

    public LiveData<List<Assignment>> getAssignments() {
        Log.d(TAG, "getAssignments: Called");
        Call<ListAssignmentResponse> call = mAssignmentService.getAssignments();
        call.enqueue(new Callback<ListAssignmentResponse>() {
            @Override
            public void onResponse(Call<ListAssignmentResponse> call, Response<ListAssignmentResponse> response) {
                ListAssignmentResponse listAssignment = response.body();
                Log.d(TAG, "onResponse: " + listAssignment);
                if (listAssignment.getStatus() == 200) {
                    mAssignmentDao.setListAssignment(listAssignment.getData());
                }
            }

            @Override
            public void onFailure(Call<ListAssignmentResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return mAssignmentDao.getListAssignment();
    }

    public LiveData<AssignmentResponse> addAssignment(Assignment assignment) {
        MutableLiveData<AssignmentResponse> assignmentResponseMutableLiveData = new MutableLiveData<>();

        Log.d(TAG, "getAssignments: Called");
        Call<AssignmentResponse> call = mAssignmentService.addAssignment(assignment);
        call.enqueue(new Callback<AssignmentResponse>() {
            @Override
            public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {
                AssignmentResponse assignmentResponse = response.body();
                assignmentResponseMutableLiveData.setValue(assignmentResponse);
                Log.d(TAG, "onResponse: " + assignmentResponse);

                if (assignmentResponse.getStatus() == 200) {
                    mAssignmentDao.addAssignment(assignmentResponse.getAssignment());
                }
            }

            @Override
            public void onFailure(Call<AssignmentResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return assignmentResponseMutableLiveData;
    }

    public LiveData<AssignmentResponse> updateAssignment(CrudType crudType, int position, Assignment assignment) {
        MutableLiveData<AssignmentResponse> assignmentResponseMutableLiveData = new MutableLiveData<>();

        Log.d(TAG, "getAssignments: Called");
        Call<AssignmentResponse> call = mAssignmentService.updateAssignment(assignment.getIdAssignment(), assignment);
        call.enqueue(new Callback<AssignmentResponse>() {
            @Override
            public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {
                AssignmentResponse assignmentResponse = response.body();
                assignmentResponseMutableLiveData.setValue(assignmentResponse);
                Log.d(TAG, "onResponse: " + assignmentResponse);

                if (assignmentResponse.getStatus() == 200) {
                    if (crudType == CrudType.ADD) {
                        mAssignmentDao.addToPosition(position, assignmentResponse.getAssignment());
                    } else {
                        mAssignmentDao.updateAssignment(assignmentResponse.getAssignment());
                    }
                }
            }

            @Override
            public void onFailure(Call<AssignmentResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return assignmentResponseMutableLiveData;
    }

    public LiveData<AssignmentResponse> deleteAssignment(int id) {
        MutableLiveData<AssignmentResponse> assignmentResponseMutableLiveData = new MutableLiveData<>();

        Call<AssignmentResponse> call = mAssignmentService.deleteAssignment(id);
        call.enqueue(new Callback<AssignmentResponse>() {
            @Override
            public void onResponse(Call<AssignmentResponse> call, Response<AssignmentResponse> response) {
                AssignmentResponse assignmentResponse = response.body();
                assignmentResponseMutableLiveData.setValue(assignmentResponse);

                if (assignmentResponse.getStatus() == 200) {
                    mAssignmentDao.deleteAssignment(id);
                }
            }

            @Override
            public void onFailure(Call<AssignmentResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return assignmentResponseMutableLiveData;
    }
}

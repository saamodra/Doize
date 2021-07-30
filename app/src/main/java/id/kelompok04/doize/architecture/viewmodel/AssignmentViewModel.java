package id.kelompok04.doize.architecture.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.kelompok04.doize.architecture.repository.AssignmentRepository;
import id.kelompok04.doize.architecture.repository.AssignmentRepository;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.model.Assignment;
import id.kelompok04.doize.model.response.AssignmentResponse;

public class AssignmentViewModel extends ViewModel {
    private AssignmentRepository mAssignmentRepository;

    public AssignmentViewModel() {
        mAssignmentRepository = AssignmentRepository.get();
    }

    public LiveData<List<Assignment>> getAssignments() {
        return mAssignmentRepository.getAssignments();
    }

//    public LiveData<AssignmentResponse> addAssignment(Assignment schedule) {
//        return mAssignmentRepository.(schedule);
//    }
//
//    public LiveData<AssignmentResponse> addToPosition(int position, Assignment schedule) {
//        return mAssignmentRepository.updateAssignment(CrudType.ADD, position, schedule);
//    }
//
//    public LiveData<AssignmentResponse> updateAssignment(Assignment schedule) {
//        return mAssignmentRepository.updateAssignment(CrudType.EDIT, -1, schedule);
//    }
//
//    public LiveData<AssignmentResponse> deleteAssignment(int id) {
//        return mAssignmentRepository.deleteAssignment(id);
//    }
}

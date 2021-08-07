package id.kelompok04.doize.architecture.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.kelompok04.doize.architecture.repository.AssignmentRepository;
import id.kelompok04.doize.architecture.repository.AssignmentRepository;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.model.Assignment;
import id.kelompok04.doize.model.Schedule;
import id.kelompok04.doize.model.response.AssignmentResponse;
import id.kelompok04.doize.model.response.ScheduleResponse;

public class AssignmentViewModel extends ViewModel {
    private AssignmentRepository mAssignmentRepository;

    public AssignmentViewModel() {
        mAssignmentRepository = AssignmentRepository.get();
    }

    public LiveData<List<Assignment>> getAssignments(int idUser) {
        return mAssignmentRepository.getAssignments(idUser);
    }

    public LiveData<List<Assignment>> getAssignmentsOnce(int idUser) {
        return mAssignmentRepository.getAssignmentsOnce(idUser);
    }

    public LiveData<AssignmentResponse> addToPosition(int position, Assignment assignment) {
        return mAssignmentRepository.updateAssignment(CrudType.ADD, position, assignment);
    }

    public LiveData<AssignmentResponse> addAssignment(Assignment assignment) {
        return mAssignmentRepository.addAssignment(assignment);
    }

    public LiveData<AssignmentResponse> updateAssignment(int position, Assignment assignment) {
        return mAssignmentRepository.updateAssignment(CrudType.EDIT, position, assignment);
    }

    public LiveData<AssignmentResponse> deleteAssignment(int id) {
        return mAssignmentRepository.deleteAssignment(id);
    }
}

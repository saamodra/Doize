package id.kelompok04.doize.architecture.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.kelompok04.doize.model.Assignment;

public class AssignmentDao {
    private static final String TAG = "AssignmentDao";
    private MutableLiveData<List<Assignment>> assignments = new MutableLiveData<>();

    public LiveData<List<Assignment>> getListAssignment() {
        return assignments;
    }

    public void setListAssignment(List<Assignment> assignments) {
        this.assignments.setValue(assignments);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Assignment getAssignmentById(int assignmentId, List<Assignment> assignmentList) {
        return assignmentList
                .stream()
                .filter(assignment -> assignment.getIdAssignment() == assignmentId)
                .findFirst()
                .orElse(null);
    }

    public void deleteAssignment(int assignmentId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<Assignment> assignmentList = assignments.getValue();
            if(assignmentList != null) {
                Assignment selectedAssignment = assignmentList
                        .stream().filter(assignment -> assignment.getIdAssignment() == assignmentId)
                        .findAny().orElse(null);

                if(selectedAssignment != null) {
                    assignmentList.remove(selectedAssignment);
                    assignments.setValue(assignmentList);
                }
            }
        }
    }

    public void addToPosition(int position, Assignment assignment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<Assignment> assignmentList = assignments.getValue();
            if(assignmentList != null) {
                assignmentList.add(position, assignment);
                assignments.setValue(assignmentList);
            }
        }
    }

    public void updateAssignment(Assignment assignment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<Assignment> assignmentList = assignments.getValue();
            if(assignmentList != null) {
                Assignment selectedAssignment = getAssignmentById(assignment.getIdAssignment(), assignmentList);

                if(selectedAssignment != null) {
                    int index = assignmentList.indexOf(selectedAssignment);
                    assignmentList.set(index, assignment);
                    assignments.setValue(assignmentList);
                }
            }
        }
    }

    public void addAssignment(Assignment assignment) {
        List<Assignment> assignmentsList = assignments.getValue();
        assignmentsList.add(assignment);
        assignments.setValue(assignmentsList);
    }
}

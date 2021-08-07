package id.kelompok04.doize.ui.fragment;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.Date;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.AssignmentViewModel;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.DateType;
import id.kelompok04.doize.helper.DoizeConstants;
import id.kelompok04.doize.helper.DoizeHelper;
import id.kelompok04.doize.helper.NotificationHelper;
import id.kelompok04.doize.model.Assignment;

public class AssignmentDialogFragment extends DialogFragment {

    public static final String TAG = "example_dialog";

    private Toolbar toolbar;
    private TextInputLayout tilAssignmentName;
    private TextInputLayout tilAssignmentSubject;
    private TextInputLayout tilDueDate;
    private TextInputLayout tilReminderDate;
    private CheckBox cbPriority;
    private TextInputLayout tilDescription;
    private Button btnSave;

    private Assignment mAssignment;
    private AssignmentViewModel mAssignmentViewModel;
    private CrudType mCrudType;


    public AssignmentDialogFragment(CrudType crudType, Assignment assignment) {
        mCrudType = crudType;
        this.mAssignment = assignment;
    }

    public static AssignmentDialogFragment display(CrudType crudType, Assignment assignment, FragmentManager fragmentManager) {
        AssignmentDialogFragment exampleDialog = new AssignmentDialogFragment(crudType, assignment);
        exampleDialog.show(fragmentManager, TAG);
        return exampleDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
        mAssignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialog_assignment_form, container, false);
        toolbar = view.findViewById(R.id.toolbar);

        tilAssignmentName = view.findViewById(R.id.til_assignment_name);
        tilAssignmentSubject = view.findViewById(R.id.til_subject);
        tilDueDate = view.findViewById(R.id.til_duedate);
        tilReminderDate = view.findViewById(R.id.til_reminder_date);
        tilDescription = view.findViewById(R.id.til_assignment_desc);
        cbPriority = view.findViewById(R.id.cb_priority);
        btnSave = view.findViewById(R.id.btn_save_assignment);

//        String assignmentName = "", assignmentSubject = "", dueDate = "", reminderDate = "", description = "";

        if (mAssignment != null) {
            String assignmentName = DoizeHelper.getString(mAssignment.getNameAssignment());
            String assignmentSubject = DoizeHelper.getString(mAssignment.getCourse());
            String dueDate = DoizeHelper.getString(mAssignment.getDuedateAssignment());
            String reminderDate = DoizeHelper.getString(mAssignment.getReminderAt());
            String description = DoizeHelper.getString(mAssignment.getDescriptionAssignment());

            tilAssignmentName.getEditText().setText(assignmentName);
            tilAssignmentSubject.getEditText().setText(assignmentSubject);
            tilDueDate.getEditText().setText(dueDate.equals("") ? "" : DateConverter.fromDbDateTimeTo(DoizeConstants.FULL_FORMAT, dueDate));
            tilReminderDate.getEditText().setText(reminderDate.equals("") ? "" : DateConverter.fromDbDateTimeTo(DoizeConstants.FULL_FORMAT, reminderDate));
            cbPriority.setChecked(mAssignment.getPriority() != 0);
            tilDescription.getEditText().setText(description);
        } else {
            mAssignment = new Assignment();
        }

        FragmentManager fragmentManager = getParentFragmentManager();

        tilDueDate.getEditText().setOnClickListener(v -> {
            Date dateDueDate = DateConverter.fromDbToDate(DateType.DATETIME, DoizeHelper.getString(mAssignment.getDuedateAssignment()));
            DatePickerFragment dialog = DatePickerFragment.newInstance(DateType.DATETIME, tilDueDate.getEditText(), dateDueDate);
            dialog.setTargetFragment(AssignmentDialogFragment.this, 0);
            dialog.show(fragmentManager, "DialogTime");
        });

        tilReminderDate.getEditText().setOnClickListener(v -> {
            Date dateReminderDate = DateConverter.fromDbToDate(DateType.DATETIME, DoizeHelper.getString(mAssignment.getReminderAt()));
            DatePickerFragment dialog = DatePickerFragment.newInstance(DateType.DATETIME, tilReminderDate.getEditText(), dateReminderDate);
            dialog.setTargetFragment(AssignmentDialogFragment.this, 0);
            dialog.show(fragmentManager, "DialogTime");
        });

        btnSave.setOnClickListener(v -> {
            Assignment assignment = getAssignment();

            if (mCrudType == CrudType.ADD) {
                mAssignmentViewModel.addAssignment(assignment).observe(getViewLifecycleOwner(), assignmentResponse -> {
                    if (assignmentResponse.getStatus() == 200) {
                        FancyToast.makeText(getActivity(), assignmentResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                        dismiss();
                    } else {
                        FancyToast.makeText(getActivity(), assignmentResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                    }
                });
            } else {
                mAssignmentViewModel.updateAssignment(-1, assignment).observe(getViewLifecycleOwner(), assignmentResponse -> {
                    if (assignmentResponse.getStatus() == 200) {
                        FancyToast.makeText(getActivity(), assignmentResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                        dismiss();
                    } else {
                        FancyToast.makeText(getActivity(), assignmentResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                    }
                });
            }
        });

        return view;
    }

    private Assignment getAssignment() {
        Assignment assignment = mAssignment == null ? new Assignment() : mAssignment;
        assignment.setNameAssignment(DoizeHelper.getString(tilAssignmentName.getEditText().getText().toString()));
        assignment.setCourse(DoizeHelper.getString(tilAssignmentSubject.getEditText().getText().toString()));
        assignment.setDuedateAssignment(DateConverter.toDbDatetimeFrom(
                DoizeConstants.FULL_FORMAT,
                tilDueDate.getEditText().getText().toString()));
        assignment.setReminderAt(DateConverter.toDbDatetimeFrom(
                DoizeConstants.FULL_FORMAT,
                tilReminderDate.getEditText().getText().toString()));
        assignment.setPriority(cbPriority.isChecked() ? 1 : 0);
        assignment.setDescriptionAssignment(DoizeHelper.getString(tilDescription.getEditText().getText().toString()));
        assignment.setIdUser(1);

        return assignment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle(mCrudType == CrudType.ADD ? "Add Assignment" : "Edit Assignment");
        toolbar.inflateMenu(R.menu.assignment_dialog_menu);
        toolbar.setOnMenuItemClickListener(item -> true);
    }

}

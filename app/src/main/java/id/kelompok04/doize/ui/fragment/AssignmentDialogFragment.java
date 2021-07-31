package id.kelompok04.doize.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import id.kelompok04.doize.R;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.DateType;
import id.kelompok04.doize.helper.DoizeConstants;
import id.kelompok04.doize.helper.DoizeHelper;
import id.kelompok04.doize.model.Assignment;

public class AssignmentDialogFragment extends DialogFragment {

    public static final String TAG = "example_dialog";

    private Toolbar toolbar;
    private String title;
    private TextInputLayout tilAssignmentName;
    private TextInputLayout tilAssignmentSubject;
    private TextInputLayout tilDueDate;
    private TextInputLayout tilDueTime;
    private TextInputLayout tilReminderDate;
    private TextInputLayout tilReminderTime;
    private CheckBox cbPriority;
    private TextInputLayout tilDescription;

    private Assignment mAssignment;


    public AssignmentDialogFragment(CrudType crudType, Assignment assignment) {
        this.title = crudType == CrudType.ADD ? "Add Assignment" : "Edit Assignment";
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
        tilDueTime = view.findViewById(R.id.til_duetime);
        tilReminderDate = view.findViewById(R.id.til_reminder_date);
        tilReminderTime = view.findViewById(R.id.til_reminder_time);
        tilDescription = view.findViewById(R.id.til_assignment_desc);
        cbPriority = view.findViewById(R.id.cb_priority);

        if (mAssignment != null) {
            String assignmentName = DoizeHelper.getString(mAssignment.getNameAssignment());
            String assignmentSubject = DoizeHelper.getString(mAssignment.getCourse());
            String dueDate = DoizeHelper.getString(mAssignment.getDuedateAssignment());
            String reminderDate = DoizeHelper.getString(mAssignment.getReminderAt());

            tilAssignmentName.getEditText().setText(assignmentName);
            tilAssignmentSubject.getEditText().setText(assignmentSubject);
            tilDueDate.getEditText().setText(dueDate.equals("") ? "" : DateConverter.fromDbDateTimeTo(DoizeConstants.DATE_FORMAT, dueDate));
            tilDueTime.getEditText().setText(dueDate.equals("") ? "" : DateConverter.fromDbDateTimeTo(DoizeConstants.TIME_FORMAT, dueDate));
            tilReminderDate.getEditText().setText(reminderDate.equals("") ? "" : DateConverter.fromDbDateTimeTo(DoizeConstants.DATE_FORMAT, reminderDate));
            tilReminderTime.getEditText().setText(reminderDate.equals("") ? "" : DateConverter.fromDbDateTimeTo(DoizeConstants.TIME_FORMAT, reminderDate));
        }

        FragmentManager fragmentManager = getParentFragmentManager();

        tilDueDate.getEditText().setOnClickListener(v -> {
            DatePickerFragment dialog = DatePickerFragment.newInstance(DateType.DATETIME, tilDueDate.getEditText(), new Date());
            dialog.setTargetFragment(AssignmentDialogFragment.this, 0);
            dialog.show(fragmentManager, "DialogTime");
        });

        tilDueTime.getEditText().setOnClickListener(v -> {
            TimePickerFragment dialog = TimePickerFragment.newInstance(DateType.TIME, tilDueTime.getEditText(), new Date());
            dialog.setTargetFragment(AssignmentDialogFragment.this, 0);
            dialog.show(fragmentManager, "DialogTime");
        });

        tilReminderDate.getEditText().setOnClickListener(v -> {
            DatePickerFragment dialog = DatePickerFragment.newInstance(DateType.DATETIME, tilReminderDate.getEditText(), new Date());
            dialog.setTargetFragment(AssignmentDialogFragment.this, 0);
            dialog.show(fragmentManager, "DialogTime");
        });

        tilReminderTime.getEditText().setOnClickListener(v -> {
            TimePickerFragment dialog = TimePickerFragment.newInstance(DateType.TIME, tilReminderTime.getEditText(), new Date());
            dialog.setTargetFragment(AssignmentDialogFragment.this, 0);
            dialog.show(fragmentManager, "DialogTime");
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle(title);
        toolbar.inflateMenu(R.menu.assignment_dialog_menu);
        toolbar.setOnMenuItemClickListener(item -> true);
    }

}

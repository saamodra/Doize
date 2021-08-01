package id.kelompok04.doize.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputLayout;

import id.kelompok04.doize.R;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.DoizeConstants;
import id.kelompok04.doize.helper.DoizeHelper;
import id.kelompok04.doize.model.Assignment;
import id.kelompok04.doize.model.DailyActivity;

public class DailyActivityDialogFragment extends DialogFragment {

    public static final String TAG = "example_dialog";

    // Component
    private Toolbar toolbar;
    private String title;
    private TextInputLayout tilDailyActivityName;
    private TextInputLayout tilDueDate;
    private TextInputLayout tilDueTime;
    private TextInputLayout tilReminderDate;
    private TextInputLayout tilReminderTime;
    private CheckBox cbPriority;
    private TextInputLayout tilDailyActivityDescription;

    // Data
    private DailyActivity mDailyActivity;

    public DailyActivityDialogFragment(CrudType crudType, DailyActivity dailyActivity) {
        this.title = crudType == CrudType.ADD ? "Add Daily Activity" : "Edit Daily Activity";
        this.mDailyActivity = dailyActivity;
    }

    public static DailyActivityDialogFragment display(CrudType crudType, DailyActivity dailyActivity, FragmentManager fragmentManager) {
        DailyActivityDialogFragment exampleDialog = new DailyActivityDialogFragment(crudType, dailyActivity);
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
        View view = inflater.inflate(R.layout.dialog_daily_activity_form, container, false);
        toolbar = view.findViewById(R.id.toolbar);

        tilDailyActivityName = view.findViewById(R.id.til_daily_activity_name);
        tilDueDate = view.findViewById(R.id.til_duedate);
        tilDueTime = view.findViewById(R.id.til_duetime);
        tilReminderDate = view.findViewById(R.id.til_reminder_date);
        tilReminderTime = view.findViewById(R.id.til_reminder_time);
        tilDailyActivityDescription = view.findViewById(R.id.til_daily_activity_desc);
        cbPriority = view.findViewById(R.id.cb_priority);

        if (mDailyActivity != null) {
            String dailyActivityName = DoizeHelper.getString(mDailyActivity.getNameDailyActivity());
            String dueDate = DoizeHelper.getString(mDailyActivity.getDuedateDailyActivity());
            String reminderDate = DoizeHelper.getString(mDailyActivity.getReminderAt());

            tilDailyActivityName.getEditText().setText(dailyActivityName);
            tilDueDate.getEditText().setText(dueDate.equals("") ? "" : DateConverter.fromDbDateTimeTo(DoizeConstants.dateFormat, dueDate));
            tilDueTime.getEditText().setText(dueDate.equals("") ? "" : DateConverter.fromDbDateTimeTo(DoizeConstants.timeFormat, dueDate));
            tilReminderDate.getEditText().setText(reminderDate.equals("") ? "" : DateConverter.fromDbDateTimeTo(DoizeConstants.dateFormat, reminderDate));
            tilReminderTime.getEditText().setText(reminderDate.equals("") ? "" : DateConverter.fromDbDateTimeTo(DoizeConstants.timeFormat, reminderDate));
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle(title);
        toolbar.inflateMenu(R.menu.assignment_dialog_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            dismiss();
            return true;
        });
    }
}

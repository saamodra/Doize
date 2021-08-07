package id.kelompok04.doize.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Date;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.AssignmentViewModel;
import id.kelompok04.doize.architecture.viewmodel.DailyActivityViewModel;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.DateType;
import id.kelompok04.doize.helper.DoizeConstants;
import id.kelompok04.doize.helper.DoizeHelper;
import id.kelompok04.doize.model.Assignment;
import id.kelompok04.doize.model.DailyActivity;

public class DailyActivityDialogFragment extends DialogFragment {

    public static final String TAG = "example_dialog";

    // Component
    private Toolbar toolbar;
    private TextInputLayout tilDailyActivityName;
    private TextInputLayout tilDueDate;
    private TextInputLayout tilReminderDate;
    private CheckBox cbPriority;
    private TextInputLayout tilDailyActivityDescription;
    private Button btnSaveDailyActivity;

    // Data
    private DailyActivity mDailyActivity;
    private DailyActivityViewModel mDailyActivityViewModel;
    private CrudType mCrudType;

    public DailyActivityDialogFragment(CrudType crudType, DailyActivity dailyActivity) {
        mCrudType = crudType;
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
        mDailyActivityViewModel = new ViewModelProvider(this).get(DailyActivityViewModel.class);
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
        tilReminderDate = view.findViewById(R.id.til_reminder_date);
        tilDailyActivityDescription = view.findViewById(R.id.til_daily_activity_desc);
        cbPriority = view.findViewById(R.id.cb_priority);
        btnSaveDailyActivity = view.findViewById(R.id.btn_save_daily_activity);

        if (mDailyActivity != null) {
            String dailyActivityName = DoizeHelper.getString(mDailyActivity.getNameDailyActivity());
            String dueDate = DoizeHelper.getString(mDailyActivity.getDuedateDailyActivity());
            String reminderDate = DoizeHelper.getString(mDailyActivity.getReminderAt());
            String dailyActivityDesc = DoizeHelper.getString(mDailyActivity.getDescriptionDailyActivity());

            tilDailyActivityName.getEditText().setText(dailyActivityName);
            tilDueDate.getEditText().setText(dueDate.equals("") ? "" : DateConverter.fromDbDateTimeTo(DoizeConstants.FULL_FORMAT, dueDate));
            tilReminderDate.getEditText().setText(reminderDate.equals("") ? "" : DateConverter.fromDbDateTimeTo(DoizeConstants.FULL_FORMAT, reminderDate));
            cbPriority.setChecked(mDailyActivity.getPriority() != 0);
            tilDailyActivityDescription.getEditText().setText(dailyActivityDesc);

        } else {
            mDailyActivity = new DailyActivity();
        }

        FragmentManager fragmentManager = getParentFragmentManager();

        tilDueDate.getEditText().setOnClickListener(v -> {
            Date dateDueDate = DateConverter.fromDbToDate(DateType.DATETIME, DoizeHelper.getString(mDailyActivity.getDuedateDailyActivity()));
            DatePickerFragment dialog = DatePickerFragment.newInstance(DateType.DATETIME, tilDueDate.getEditText(), dateDueDate);
            dialog.setTargetFragment(DailyActivityDialogFragment.this, 0);
            dialog.show(fragmentManager, "DialogTime");
        });

        tilReminderDate.getEditText().setOnClickListener(v -> {
            Date dateReminderDate = DateConverter.fromDbToDate(DateType.DATETIME, DoizeHelper.getString(mDailyActivity.getReminderAt()));
            DatePickerFragment dialog = DatePickerFragment.newInstance(DateType.DATETIME, tilReminderDate.getEditText(), dateReminderDate);
            dialog.setTargetFragment(DailyActivityDialogFragment.this, 0);
            dialog.show(fragmentManager, "DialogTime");
        });

        btnSaveDailyActivity.setOnClickListener(v -> {
            DailyActivity dailyActivity = getDailyActivity();

            if (mCrudType == CrudType.ADD) {
                mDailyActivityViewModel.addDailyActivity(dailyActivity).observe(getViewLifecycleOwner(), dailyActivityResponse -> {
                    if (dailyActivityResponse.getStatus() == 200) {
                        FancyToast.makeText(getActivity(), dailyActivityResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                        dismiss();
                    } else {
                        FancyToast.makeText(getActivity(), dailyActivityResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                    }
                });
            } else {
                mDailyActivityViewModel.updateDailyActivity(-1, dailyActivity).observe(getViewLifecycleOwner(), dailyActivityResponse -> {
                    if (dailyActivityResponse.getStatus() == 200) {
                        FancyToast.makeText(getActivity(), dailyActivityResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                        dismiss();
                    } else {
                        FancyToast.makeText(getActivity(), dailyActivityResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                    }
                });
            }
        });

        return view;
    }

    private DailyActivity getDailyActivity() {
        DailyActivity dailyActivity = mDailyActivity == null ? new DailyActivity() : mDailyActivity;
        dailyActivity.setNameDailyActivity(DoizeHelper.getString(tilDailyActivityName.getEditText().getText().toString()));
        dailyActivity.setDuedateDailyActivity(DateConverter.toDbDatetimeFrom(
                DoizeConstants.FULL_FORMAT,
                tilDueDate.getEditText().getText().toString()));
        dailyActivity.setReminderAt(DateConverter.toDbDatetimeFrom(
                DoizeConstants.FULL_FORMAT,
                tilReminderDate.getEditText().getText().toString()));
        dailyActivity.setPriority(cbPriority.isChecked() ? 1 : 0);
        dailyActivity.setDescriptionDailyActivity(DoizeHelper.getString(tilDailyActivityDescription.getEditText().getText().toString()));
        dailyActivity.setStatus(1);
        dailyActivity.setIdUser(DoizeHelper.getIdUserPref(requireActivity()));

        return dailyActivity;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.setTitle(mCrudType == CrudType.ADD ? "Add Daily Activity" : "Edit Daily Activity");
        toolbar.inflateMenu(R.menu.assignment_dialog_menu);
        toolbar.setOnMenuItemClickListener(item -> true);
    }
}

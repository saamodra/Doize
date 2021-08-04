package id.kelompok04.doize.ui.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.AssignmentViewModel;
import id.kelompok04.doize.architecture.viewmodel.PomodoroViewModel;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.DateType;
import id.kelompok04.doize.helper.TimeConverter;
import id.kelompok04.doize.model.Assignment;
import id.kelompok04.doize.model.Pomodoro;
import id.kelompok04.doize.model.Schedule;

public class PomodoroFragment extends Fragment {
    private static final String TAG = "PomodoroFragment";

    // Components
    FloatingActionButton btnStart, btnAdd, btnReset;
    TextView tvTimer;
    Drawable startIcon, pauseIcon;

    // Timer
    long currentTime, userTime;
    CountDownTimer mCountDownTimer;
    boolean timerStarted = false;

    // Data
    PomodoroViewModel mPomodoroViewModel;
    Pomodoro mPomodoroFragmentData;


    // Pomodoro Dialog Form
    private View customAlertDialogView;
    private AlertDialog mAlertDialog;
    private MaterialAlertDialogBuilder mMaterialAlertDialogBuilder;
    private TextInputLayout tilPomodoroTime;
    private TextInputLayout tilShortBreak;
    private TextInputLayout tilLongBreak;


    public PomodoroFragment() { }

    public static PomodoroFragment newInstance(String param1, String param2) {
        PomodoroFragment fragment = new PomodoroFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPomodoroViewModel = new ViewModelProvider(this).get(PomodoroViewModel.class);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.pomodoro_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                launchCustomAlertDialog(CrudType.ADD, null, null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pomodoro, container, false);
        startIcon = getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24);
        pauseIcon = getResources().getDrawable(R.drawable.ic_baseline_pause_24);

        mMaterialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());

        tvTimer = view.findViewById(R.id.tv_timer);
        btnStart = view.findViewById(R.id.fab_toggle_timer);
        btnReset = view.findViewById(R.id.fab_reset_timer);
        btnAdd = view.findViewById(R.id.fab_add_task);

        newTimer(userTime);

        btnStart.setOnClickListener(v -> {
            timerStarted = !timerStarted;
            btnStart.setImageDrawable(timerStarted ? pauseIcon : startIcon);

            if (timerStarted) {
                newTimer(currentTime);
                mCountDownTimer.start();
            } else {
                mCountDownTimer.cancel();
            }
        });

        btnReset.setOnClickListener(v -> {
            resetTapped();
        });

        btnAdd.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
            bottomSheetDialog.setContentView(R.layout.dialog_add_task_pomodoro);
            bottomSheetDialog.setCanceledOnTouchOutside(false);

            bottomSheetDialog.show();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPomodoroViewModel.getPomodoro(1).observe(getViewLifecycleOwner(), this::updateUI);
    }

    private void updateUI(Pomodoro pomodoro) {
        mPomodoroFragmentData = pomodoro;
        userTime = TimeConverter.fromDbToMilliseconds(pomodoro.getProductivityTime());
        Log.d(TAG, "updateUI: " + userTime + " " + pomodoro);
        currentTime = userTime;
        tvTimer.setText(TimeConverter.formatTime(userTime));
    }

    private void launchCustomAlertDialog(CrudType crudType, Schedule schedule, View.OnClickListener onClickListener) {
        LayoutInflater inflater = getLayoutInflater();
        customAlertDialogView = inflater.inflate(R.layout.dialog_pomodoro_settings, null);

        Log.d(TAG, "launchCustomAlertDialog: " + " Started");
        tilPomodoroTime = customAlertDialogView.findViewById(R.id.til_pomodoro_time);
        tilShortBreak = customAlertDialogView.findViewById(R.id.til_pomodoro_short_break);
        tilLongBreak = customAlertDialogView.findViewById(R.id.til_pomodoro_long_break);

        if (crudType == CrudType.EDIT) {
//            mScheduleNameLayout.getEditText().setText(schedule.getNameSchedule());
//            mScheduleDescriptionLayout.getEditText().setText(schedule.getDescriptionSchedule());
        }

        tilPomodoroTime.getEditText().setOnClickListener(v -> {
            Log.d(TAG, "launchCustomAlertDialog: " + " Pomodoro time");
            FragmentManager fragmentManager = getParentFragmentManager();

            DurationPickerFragment dialog = DurationPickerFragment.newInstance(DateType.TIME, tilPomodoroTime.getEditText(), null);
            dialog.setTargetFragment(PomodoroFragment.this, 0);
            dialog.show(fragmentManager, "DialogDuration");
        });

        String title = crudType == CrudType.ADD ? "Add" : "Update";
        mMaterialAlertDialogBuilder.setView(customAlertDialogView)
                .setTitle(title + " Schedule")
                .setMessage("Enter your schedule details")
                .setPositiveButton(title, null)
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });

        mAlertDialog = mMaterialAlertDialogBuilder.show();
        Button positiveButton = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(onClickListener);
    }

    private void newTimer(long timeLengthMs) {
        mCountDownTimer = new CountDownTimer(timeLengthMs, 1000) { // adjust the milli seconds here
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            public void onTick(long millisUntilFinished) {
                currentTime = millisUntilFinished;
                tvTimer.setText(TimeConverter.formatTime(millisUntilFinished));
            }

            public void onFinish() {
                tvTimer.setText("done!");
                currentTime = userTime;
                btnStart.setImageDrawable(startIcon);
                timerStarted = false;
            }
        };
    }

    public void resetTapped()
    {
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(requireContext());
        resetAlert.setTitle("Reset Timer");
        resetAlert.setMessage("Are you sure you want to reset the timer?");
        resetAlert.setPositiveButton("Reset", (dialogInterface, i) -> {
            if(mCountDownTimer != null)
            {
                mCountDownTimer.cancel();
                btnStart.setImageDrawable(startIcon);
                currentTime = userTime;
                timerStarted = false;
                tvTimer.setText(TimeConverter.formatTime(userTime));
            }
        });

        resetAlert.setNeutralButton("Cancel", (dialogInterface, i) -> {});
        resetAlert.show();
    }


}
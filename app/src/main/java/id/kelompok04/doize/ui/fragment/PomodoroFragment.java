package id.kelompok04.doize.ui.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import id.kelompok04.doize.R;

public class PomodoroFragment extends Fragment {
    private static final String TAG = "PomodoroFragment";
    BottomSheetBehavior<LinearLayout> relativeLayout;

    FloatingActionButton btnStart, btnAdd, btnReset;
    TextView tvTimer;

    Drawable startIcon, pauseIcon;

    Timer mTimer;
    TimerTask mTimerTask;
    long currentTime, userTime;
    CountDownTimer mCountDownTimer;

    boolean timerStarted = false;

    public PomodoroFragment() {
        // Required empty public constructor
    }

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
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.pomodoro_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pomodoro, container, false);
        startIcon = getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24);
        pauseIcon = getResources().getDrawable(R.drawable.ic_baseline_pause_24);

        tvTimer = view.findViewById(R.id.tv_timer);
        btnStart = view.findViewById(R.id.fab_toggle_timer);
        btnReset = view.findViewById(R.id.fab_reset_timer);
        btnAdd = view.findViewById(R.id.fab_add_task);

        mTimer = new Timer();
        userTime = 5000;
        currentTime = userTime;
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

    private void newTimer(long timeLengthMs) {
        mCountDownTimer = new CountDownTimer(timeLengthMs, 1000) { // adjust the milli seconds here
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            public void onTick(long millisUntilFinished) {
                currentTime = millisUntilFinished;
                tvTimer.setText(formatTime(millisUntilFinished));
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
                tvTimer.setText(formatTime(userTime));
            }
        });

        resetAlert.setNeutralButton("Cancel", (dialogInterface, i) -> {});
        resetAlert.show();
    }

    @SuppressLint("DefaultLocale")
    private String formatTime(long milliseconds)
    {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }

}
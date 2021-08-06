package id.kelompok04.doize.ui.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.paris.Paris;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Collections;
import java.util.List;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.PomodoroActivityViewModel;
import id.kelompok04.doize.architecture.viewmodel.PomodoroViewModel;
import id.kelompok04.doize.helper.CustomTime;
import id.kelompok04.doize.helper.TimeConverter;
import id.kelompok04.doize.model.Pomodoro;
import id.kelompok04.doize.model.PomodoroActivity;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class PomodoroFragment extends Fragment {
    private static final String TAG = "PomodoroFragment";

    // Components
    FloatingActionButton btnStart, btnAdd, btnReset;
    TextView tvTimer;
    Drawable startIcon, pauseIcon;
    RecyclerView rvTask;
    View taskAddDialog;
    ImageButton btnAddTask;
    Button btnPomodoro, btnShortBreak, btnLongBreak;

    // Timer
    long currentTime, userTime;
    CountDownTimer mCountDownTimer;
    boolean timerStarted = false;

    // Data
    PomodoroViewModel mPomodoroViewModel;
    PomodoroActivityViewModel mPomodoroActivityViewModel;
    Pomodoro mPomodoroFragmentData;
    List<PomodoroActivity> mPomodoroActivitiesFragmentData;
    PomodoroActivityAdapter mPomodoroActivityAdapter = new PomodoroActivityAdapter(Collections.emptyList());


    // Pomodoro Dialog Form
    private View customAlertDialogView;
    private AlertDialog mAlertDialog;
    private MaterialAlertDialogBuilder mMaterialAlertDialogBuilder;
    private TextInputLayout tilPomodoroTime, tilShortBreak, tilLongBreak, tilTask;


    public PomodoroFragment() { }

    public static PomodoroFragment newInstance() {
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
        mPomodoroActivityViewModel = new ViewModelProvider(this).get(PomodoroActivityViewModel.class);
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
                launchCustomAlertDialog(updateSettings);
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
        rvTask = view.findViewById(R.id.rv_task);
        rvTask.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTask.setAdapter(mPomodoroActivityAdapter);

        tvTimer = view.findViewById(R.id.tv_timer);
        btnStart = view.findViewById(R.id.fab_toggle_timer);
        btnReset = view.findViewById(R.id.fab_reset_timer);
        btnAdd = view.findViewById(R.id.fab_add_task);
        btnPomodoro = view.findViewById(R.id.btn_pomodoro);
        btnShortBreak = view.findViewById(R.id.btn_short_break);
        btnLongBreak = view.findViewById(R.id.btn_long_break);

        btnPomodoro.setOnClickListener(v -> {
            resetDialogTab(btnPomodoro, mPomodoroFragmentData.getProductivityTime());
        });

        btnShortBreak.setOnClickListener(v -> {
            resetDialogTab(btnShortBreak, mPomodoroFragmentData.getShortBreak());
        });

        btnLongBreak.setOnClickListener(v -> {
            resetDialogTab(btnLongBreak, mPomodoroFragmentData.getLongBreak());
        });

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
            taskAddDialog = inflater.inflate(R.layout.dialog_add_task_pomodoro, null);
            btnAddTask = taskAddDialog.findViewById(R.id.ib_add_task);
            tilTask = taskAddDialog.findViewById(R.id.til_task);

            bottomSheetDialog.setContentView(taskAddDialog);
            bottomSheetDialog.setCanceledOnTouchOutside(false);
            btnAddTask.setOnClickListener(v1 -> {
                String activityName = tilTask.getEditText().getText().toString();
                PomodoroActivity pomodoroActivity = new PomodoroActivity(activityName, mPomodoroFragmentData.getIdPomodoro(), 1);
                ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "Task", "Adding task...");

                mPomodoroActivityViewModel.addPomodoroActivity(pomodoroActivity).observe(getViewLifecycleOwner(), pomodoroActivityResponse -> {
                    if (pomodoroActivityResponse.getStatus() == 200) {
                        FancyToast.makeText(getActivity(), pomodoroActivityResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                        bottomSheetDialog.dismiss();
                    } else {
                        FancyToast.makeText(getActivity(), pomodoroActivityResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                    }

                    progressDialog.dismiss();
                });
            });

            bottomSheetDialog.show();
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mSimpleCallback);
        itemTouchHelper.attachToRecyclerView(rvTask);

        return view;
    }

    private void changeTabStyle(Button button) {
        Paris.style(btnShortBreak).apply(R.style.PomodoroTab_ButtonOutlined);
        Paris.style(btnLongBreak).apply(R.style.PomodoroTab_ButtonOutlined);
        Paris.style(btnPomodoro).apply(R.style.PomodoroTab_ButtonOutlined);
        Paris.style(button).apply(R.style.PomodoroTab_Button);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProgressDialog progressDialog = ProgressDialog.show(requireContext(), null, "Loading...");
        mPomodoroViewModel.getPomodoro(8).observe(getViewLifecycleOwner(), pomodoro -> {
            if (pomodoro != null) {
                updateUI(pomodoro);
                mPomodoroActivityViewModel.getPomodoroActivites(pomodoro.getIdPomodoro()).observe(getViewLifecycleOwner(), this::updateUITask);
            } else {
                mPomodoroViewModel.addPomodoro(8);
            }
            progressDialog.dismiss();
        });

    }

    private void updateUI(Pomodoro pomodoro) {
        mPomodoroFragmentData = pomodoro;
        userTime = TimeConverter.fromDbToMilliseconds(pomodoro.getProductivityTime());
        currentTime = userTime;
        tvTimer.setText(TimeConverter.formatTime(userTime));
    }

    private void updateUITask(List<PomodoroActivity> pomodoroActivities) {
        mPomodoroActivitiesFragmentData = pomodoroActivities;
        mPomodoroActivityAdapter = new PomodoroActivityAdapter(pomodoroActivities);
        rvTask.setAdapter(mPomodoroActivityAdapter);
    }

    private View.OnClickListener updateSettings = v -> {
        String productivityTime = TimeConverter.toDbFromStringFormatted(tilPomodoroTime.getEditText().getText().toString());
        String shortBreak = TimeConverter.toDbFromStringFormatted(tilShortBreak.getEditText().getText().toString());
        String longBreak = TimeConverter.toDbFromStringFormatted(tilLongBreak.getEditText().getText().toString());

        Pomodoro pomodoro = mPomodoroFragmentData;
        pomodoro.setProductivityTime(productivityTime);
        pomodoro.setShortBreak(shortBreak);
        pomodoro.setLongBreak(longBreak);


        ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "Settings", "Updating settings...");
        mPomodoroViewModel.updatePomodoro(pomodoro).observe(getViewLifecycleOwner(), detailPomodoroActivityResponse -> {
            if (detailPomodoroActivityResponse.getStatus() == 200) {
                FancyToast.makeText(getActivity(), detailPomodoroActivityResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                mAlertDialog.dismiss();
            } else {
                FancyToast.makeText(getActivity(), detailPomodoroActivityResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
            }

            progressDialog.dismiss();
        });
    };

    private void launchCustomAlertDialog(View.OnClickListener onClickListener) {
        LayoutInflater inflater = getLayoutInflater();
        customAlertDialogView = inflater.inflate(R.layout.dialog_pomodoro_settings, null);
        FragmentManager fragmentManager = getParentFragmentManager();

        Log.d(TAG, "launchCustomAlertDialog: " + " Started");
        tilPomodoroTime = customAlertDialogView.findViewById(R.id.til_pomodoro_time);
        tilShortBreak = customAlertDialogView.findViewById(R.id.til_pomodoro_short_break);
        tilLongBreak = customAlertDialogView.findViewById(R.id.til_pomodoro_long_break);

        String productivityTime = TimeConverter.fromDbToString(mPomodoroFragmentData.getProductivityTime());
        String shortBreak = TimeConverter.fromDbToString(mPomodoroFragmentData.getShortBreak());
        String longBreak = TimeConverter.fromDbToString(mPomodoroFragmentData.getLongBreak());

        tilPomodoroTime.getEditText().setText(productivityTime);
        tilShortBreak.getEditText().setText(shortBreak);
        tilLongBreak.getEditText().setText(longBreak);

        tilPomodoroTime.getEditText().setOnClickListener(v -> {
            CustomTime customTime = TimeConverter.fromDbToCustomTime(mPomodoroFragmentData.getProductivityTime());

            DurationPickerFragment dialog = DurationPickerFragment.newInstance(tilPomodoroTime.getEditText(), customTime);
            dialog.setTargetFragment(PomodoroFragment.this, 0);
            dialog.show(fragmentManager, "DialogDuration");
        });

        tilShortBreak.getEditText().setOnClickListener(v -> {
            CustomTime customTime = TimeConverter.fromDbToCustomTime(mPomodoroFragmentData.getShortBreak());

            DurationPickerFragment dialog = DurationPickerFragment.newInstance(tilShortBreak.getEditText(), customTime);
            dialog.setTargetFragment(PomodoroFragment.this, 0);
            dialog.show(fragmentManager, "DialogDuration");
        });

        tilLongBreak.getEditText().setOnClickListener(v -> {
            CustomTime customTime = TimeConverter.fromDbToCustomTime(mPomodoroFragmentData.getLongBreak());

            DurationPickerFragment dialog = DurationPickerFragment.newInstance(tilLongBreak.getEditText(), customTime);
            dialog.setTargetFragment(PomodoroFragment.this, 0);
            dialog.show(fragmentManager, "DialogDuration");
        });

        mMaterialAlertDialogBuilder.setView(customAlertDialogView)
                .setTitle("Pomodoro Settings")
                .setMessage("Display your pomodoro timer preferences")
                .setPositiveButton("Update", null)
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

    private void resetDialogTab(Button button, String time) {
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(requireContext());
        resetAlert.setTitle("Reset Timer");
        resetAlert.setMessage("Are you sure you want to reset the timer?");
        resetAlert.setPositiveButton("Reset", (dialog, which) -> {
            changeTabStyle(button);
            userTime = TimeConverter.fromDbToMilliseconds(time);
            btnStart.setImageDrawable(startIcon);
            currentTime = userTime;
            timerStarted = false;
            tvTimer.setText(TimeConverter.formatTime(userTime));

            if(mCountDownTimer != null)
            {
                mCountDownTimer.cancel();
            }
        });

        resetAlert.setNeutralButton("Cancel", (dialogInterface, i) -> {});
        resetAlert.show();
    }


    private void resetTapped() {
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(requireContext());
        resetAlert.setTitle("Reset Timer");
        resetAlert.setMessage("Are you sure you want to reset the timer?");
        resetAlert.setPositiveButton("Reset", (dialog, which) -> {
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

    ItemTouchHelper.SimpleCallback mSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getBindingAdapterPosition();
            PomodoroActivity pomodoroActivity = mPomodoroActivityAdapter.mPomodoroActivities.get(position);

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    mPomodoroActivityViewModel.deletePomodoroActivity(pomodoroActivity.getIdPomodoroActivity()).observe(getViewLifecycleOwner(), pomodoroActivityResponse -> {
                        if (pomodoroActivityResponse.getStatus() == 200) {
                            mPomodoroActivityAdapter.notifyItemRemoved(position);
                            Snackbar.make(rvTask, pomodoroActivity.getActivityName() + " was deleted.", Snackbar.LENGTH_LONG)
                                    .setAction("Undo", v -> {
                                        PomodoroActivity updatedPomodoroActivity = pomodoroActivityResponse.getData();
                                        updatedPomodoroActivity.setStatus(1);
                                        mPomodoroActivityViewModel.addToPosition(position, updatedPomodoroActivity);
                                    }).show();
                        } else {
                            FancyToast.makeText(getActivity(), pomodoroActivityResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false)
                                    .show();
                        }
                    });
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.pink))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private class PomodoroActivityAdapter extends RecyclerView.Adapter<PomodoroActivityAdapter.PomodoroActivityHolder> {

        private List<PomodoroActivity> mPomodoroActivities;

        public PomodoroActivityAdapter(List<PomodoroActivity> pomodoroActivitys) {
            mPomodoroActivities = pomodoroActivitys;
        }

        @NonNull
        @Override
        public PomodoroActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new PomodoroActivityHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PomodoroActivityHolder holder, int position) {
            PomodoroActivity pomodoroActivity = mPomodoroActivities.get(position);
            holder.bind(pomodoroActivity);
        }

        @Override
        public int getItemCount() {
            return mPomodoroActivities.size();
        }

        private class PomodoroActivityHolder extends RecyclerView.ViewHolder {

            private TextView mPomodoroTaskName;
            private ImageView ivCheckPomodoroTask;
            private PomodoroActivity mPomodoroActivity;
            private FrameLayout borderLeft;

            public PomodoroActivityHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_row_pomodoro_task, parent, false));

                mPomodoroTaskName = itemView.findViewById(R.id.tv_pomodoro_task_name);
                ivCheckPomodoroTask = itemView.findViewById(R.id.iv_check_pomodoro_task);
                borderLeft = itemView.findViewById(R.id.border_left_card_pomodoro);

                ivCheckPomodoroTask.setOnClickListener(v -> {
                    int workingStatus = mPomodoroActivity.getWorkingStatus() == 0 ? 1 : 0;
                    mPomodoroActivity.setWorkingStatus(workingStatus);
                    mPomodoroActivityViewModel.updatePomodoroActivity(mPomodoroActivity);
                });
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            public void bind(PomodoroActivity pomodoroActivity) {
                mPomodoroActivity = pomodoroActivity;
                mPomodoroTaskName.setText(pomodoroActivity.getActivityName());

                Drawable borderLeftSrc = getResources()
                        .getDrawable((mPomodoroActivity.getWorkingStatus() == 1) ? R.drawable.border_left_purple : R.drawable.border_left_green);
                Drawable checkSrc = getResources()
                        .getDrawable((mPomodoroActivity.getWorkingStatus() == 1) ? R.drawable.ic_checked_false : R.drawable.ic_checked_true);

                mPomodoroTaskName.setPaintFlags(mPomodoroActivity.getWorkingStatus() == 1 ? 0 : (mPomodoroTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG));

                borderLeft.setBackground(borderLeftSrc);
                ivCheckPomodoroTask.setImageDrawable(checkSrc);
            }
        }
    }
}
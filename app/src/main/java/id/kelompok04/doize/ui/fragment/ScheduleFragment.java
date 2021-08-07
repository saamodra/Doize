package id.kelompok04.doize.ui.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.ScheduleViewModel;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.DoizeHelper;
import id.kelompok04.doize.helper.ValidationHelper;
import id.kelompok04.doize.model.Schedule;
import id.kelompok04.doize.model.response.ScheduleResponse;
import id.kelompok04.doize.ui.activity.MainActivity;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ScheduleFragment extends Fragment {
    private static final String TAG = "ScheduleFragment";

    private ScheduleViewModel mScheduleViewModel;
    private RecyclerView mScheduleRecyclerView;
    private MaterialAlertDialogBuilder mMaterialAlertDialogBuilder;
    private FloatingActionButton mFloatingAddScheduleButton;
    private View mLayoutEmpty;
    private View customAlertDialogView;
    private ScheduleAdapter mAdapter = new ScheduleAdapter(Collections.emptyList());

    // Schedule Dialog Form
    private AlertDialog mAlertDialog;
    private TextInputLayout mScheduleNameLayout;
    private TextInputLayout mScheduleDescriptionLayout;
    private TextInputLayout mScheduleSearchLayout;

    // Data
    private List<Schedule> mSchedulesFragment;
    private Schedule globalSchedule;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mScheduleViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        mScheduleRecyclerView = view.findViewById(R.id.rv_schedule);
        mScheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mScheduleRecyclerView.setAdapter(mAdapter);

        mLayoutEmpty = view.findViewById(R.id.layout_empty_data);

        mMaterialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
        mFloatingAddScheduleButton = view.findViewById(R.id.fab_add_schedule);
        mFloatingAddScheduleButton.setOnClickListener(v -> {
            launchCustomAlertDialog(CrudType.ADD, null, saveSchedule);
        });

        mScheduleSearchLayout = view.findViewById(R.id.til_schedule_search);
        mScheduleSearchLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mSimpleCallback);
        itemTouchHelper.attachToRecyclerView(mScheduleRecyclerView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScheduleViewModel.getSchedules().observe(getViewLifecycleOwner(), new Observer<List<Schedule>>() {
            @Override
            public void onChanged(List<Schedule> schedules) {
                updateUI(schedules);
            }
        });
    }

    private View.OnClickListener saveSchedule = v -> {
        if (validate()) {
            String name = mScheduleNameLayout.getEditText().getText().toString();
            String desc = mScheduleDescriptionLayout.getEditText().getText().toString();

            Schedule schedule = new Schedule(name, desc, DoizeHelper.getIdUserPref(requireActivity()));
            ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "Schedule", "Adding schedule ...");
            mScheduleViewModel.addSchedule(schedule).observe(getViewLifecycleOwner(), scheduleResponse -> {
                if (scheduleResponse.getStatus() == 200) {
                    FancyToast.makeText(getActivity(), scheduleResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                    mAlertDialog.dismiss();
                } else {
                    FancyToast.makeText(getActivity(), scheduleResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                }

                progressDialog.dismiss();
            });

        } else {
            FancyToast.makeText(getActivity(),"Lengkapi semua data!", FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
        }
    };

    private View.OnClickListener editSchedule = v -> {
        if (validate()) {
            String name = mScheduleNameLayout.getEditText().getText().toString();
            String desc = mScheduleDescriptionLayout.getEditText().getText().toString();

            globalSchedule.setNameSchedule(name);
            globalSchedule.setDescriptionSchedule(desc);

            ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "Schedule", "Updating schedule ...");
            mScheduleViewModel.updateSchedule(globalSchedule).observe(getViewLifecycleOwner(), scheduleResponse -> {
                if (scheduleResponse.getStatus() == 200) {
                    FancyToast.makeText(getActivity(), scheduleResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                    mAlertDialog.dismiss();
                } else {
                    FancyToast.makeText(getActivity(), scheduleResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                }

                progressDialog.dismiss();
            });

        } else {
            FancyToast.makeText(getActivity(),"Lengkapi semua data!", FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
        }
    };

    private void updateUI(List<Schedule> schedules) {
        mAdapter = new ScheduleAdapter(schedules);
        mScheduleRecyclerView.setAdapter(mAdapter);
        mSchedulesFragment = schedules;
    }

    private void launchCustomAlertDialog(CrudType crudType, Schedule schedule, View.OnClickListener onClickListener) {
        LayoutInflater inflater = getLayoutInflater();
        customAlertDialogView = inflater.inflate(R.layout.dialog_schedule_form, null);

        mScheduleNameLayout = customAlertDialogView.findViewById(R.id.til_schedule_name);
        mScheduleDescriptionLayout = customAlertDialogView.findViewById(R.id.til_schedule_desc);

        if (crudType == CrudType.EDIT) {
            mScheduleNameLayout.getEditText().setText(schedule.getNameSchedule());
            mScheduleDescriptionLayout.getEditText().setText(schedule.getDescriptionSchedule());
        }

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

    public boolean validate() {
        boolean nameValidation = ValidationHelper.requiredTextInputValidation(mScheduleNameLayout);
        boolean descValidation = ValidationHelper.requiredTextInputValidation(mScheduleDescriptionLayout);

        return nameValidation && descValidation;
    }

    public void filter(String text) {
        List<Schedule> filtered = new ArrayList<>();

        for (Schedule s : mSchedulesFragment) {
            if (s.getNameSchedule().toLowerCase().contains(text.toLowerCase())
                    || s.getDescriptionSchedule().toLowerCase().contains(text.toLowerCase())) {
                filtered.add(s);
            }
        }

        mAdapter.filterList(filtered);
    }

    ItemTouchHelper.SimpleCallback mSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Schedule schedule = mSchedulesFragment.get(position);

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    mScheduleViewModel.deleteSchedule(schedule.getIdSchedule()).observe(getViewLifecycleOwner(), scheduleResponse -> {
                        if (scheduleResponse.getStatus() == 200) {
                            mAdapter.notifyItemRemoved(position);
                            Snackbar.make(mScheduleRecyclerView, schedule.getNameSchedule() + " was deleted.", Snackbar.LENGTH_LONG)
                                    .setAction("Undo", v -> {
                                        Schedule updatedSchedule = scheduleResponse.getData();
                                        updatedSchedule.setStatus(1);
                                        mScheduleViewModel.addToPosition(position, updatedSchedule);
                                    }).show();
                        } else {
                            FancyToast.makeText(getActivity(), scheduleResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false)
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

    private class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder> {

        private List<Schedule> mSchedules;

        public ScheduleAdapter(List<Schedule> schedules) {
            mSchedules = schedules;
        }

        public void filterList(List<Schedule> filtered) {
            if (filtered.size() == 0) {
                mLayoutEmpty.setVisibility(View.VISIBLE);
                mScheduleRecyclerView.setVisibility(View.GONE);
            } else {
                mLayoutEmpty.setVisibility(View.GONE);
                mScheduleRecyclerView.setVisibility(View.VISIBLE);
            }
            this.mSchedules = filtered;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ScheduleHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
            Schedule schedule = mSchedules.get(position);
            holder.bind(schedule);
        }

        @Override
        public int getItemCount() {
            return mSchedules.size();
        }

        private class ScheduleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView mScheduleName;
            private TextView mScheduleDesc;
            private ImageButton editButton;
            private Schedule mSchedule;

            public ScheduleHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_row_schedule, parent, false));
                itemView.setOnClickListener(this);

                mScheduleName = itemView.findViewById(R.id.tv_schedule_name);
                mScheduleDesc = itemView.findViewById(R.id.tv_schedule_description);
                editButton = itemView.findViewById(R.id.btn_edit_schedule);

                editButton.setOnClickListener(v -> {
                    globalSchedule = mSchedule;
                    launchCustomAlertDialog(CrudType.EDIT, mSchedule, editSchedule);
                });
            }

            public void bind(Schedule schedule) {
                mSchedule = schedule;
                mScheduleName.setText(schedule.getNameSchedule());
                mScheduleDesc.setText(schedule.getDescriptionSchedule());
            }

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("scheduleId", Integer.toString(mSchedule.getIdSchedule()));
                bundle.putString("scheduleName", mSchedule.getNameSchedule());
                bundle.putString("scheduleDesc", mSchedule.getDescriptionSchedule());
                Navigation.findNavController(getActivity(), R.id.fragment_container).navigate(R.id.action_scheduleFragment_to_scheduleDetailFragment, bundle);

            }
        }
    }
}
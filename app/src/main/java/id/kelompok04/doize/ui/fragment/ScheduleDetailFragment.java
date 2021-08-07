package id.kelompok04.doize.ui.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.DetailScheduleViewModel;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.DateType;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.helper.DoizeConstants;
import id.kelompok04.doize.helper.ValidationHelper;
import id.kelompok04.doize.model.DetailSchedule;

public class ScheduleDetailFragment extends Fragment {
    private static final String TAG = "ScheduleDetailFragment";

    private DetailScheduleViewModel mDetailScheduleViewModel;
    private ScheduleDetailAdapter mScheduleDetailAdapter = new ScheduleDetailAdapter(Collections.emptyList());

    // Components
    private TextView mTvScheduleName;
    private TextView mTvScheduleDesc;
    private RecyclerView rvScheduleDetail;
    private MaterialAlertDialogBuilder mMaterialAlertDialogBuilder;
    private FloatingActionButton fabAddDetailSchedule;
    private View customAlertDialogView;
    private TextInputLayout tilScheduleDetailName;
    private TextInputLayout tilScheduleDetailDay;
    private TextInputLayout tilScheduleDetailStartTime;
    private TextInputLayout tilScheduleDetailEndTime;
    private AutoCompleteTextView actvScheduleDetailDay;
    private AlertDialog alertDialog;

    private SimpleDateFormat mSimpleTimeFormat = new SimpleDateFormat("HH:mm");
    private DetailSchedule globalDetailSchedule = new DetailSchedule();

    public ScheduleDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailScheduleViewModel = new ViewModelProvider(this).get(DetailScheduleViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_detail, container, false);

        rvScheduleDetail = view.findViewById(R.id.rv_schedule_detail);
        rvScheduleDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvScheduleDetail.setAdapter(mScheduleDetailAdapter);

        mTvScheduleName = view.findViewById(R.id.tv_schedule_name);
        mTvScheduleDesc = view.findViewById(R.id.tv_schedule_description);

        String name = getArguments().getString("scheduleName");
        String desc = getArguments().getString("scheduleDesc");

        mTvScheduleName.setText(name);
        mTvScheduleDesc.setText(desc);

        fabAddDetailSchedule = view.findViewById(R.id.fab_add_detail_schedule);
        fabAddDetailSchedule.setOnClickListener(v -> {
            launchCustomAlertDialog(CrudType.ADD, null, saveDetailSchedule);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String idSchedule = getArguments().getString("scheduleId");

        mDetailScheduleViewModel.getDetailSchedules(idSchedule).observe(getViewLifecycleOwner(), new Observer<List<List<DetailSchedule>>>() {
            @Override
            public void onChanged(List<List<DetailSchedule>> lists) {
                Log.d(TAG, "onChanged: " + lists);
                updateUI(lists);
            }
        });
    }

    private View.OnClickListener saveDetailSchedule = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (validate()) {
                String name = tilScheduleDetailName.getEditText().getText().toString();
                String day = tilScheduleDetailDay.getEditText().getText().toString();
                String startTimeText = tilScheduleDetailStartTime.getEditText().getText().toString();
                String endTimeText = tilScheduleDetailEndTime.getEditText().getText().toString();
                String startTime = DateConverter.toDbTimeFrom(mSimpleTimeFormat, startTimeText);
                String endTime = DateConverter.toDbTimeFrom(mSimpleTimeFormat, endTimeText);
                int idSchedule = Integer.parseInt(getArguments().getString("scheduleId"));

                DetailSchedule detailSchedule = new DetailSchedule(name, day, idSchedule, startTime, endTime);
                mDetailScheduleViewModel.addDetailSchedule(detailSchedule).observe(getViewLifecycleOwner(), detailScheduleResponse -> {
                    if (detailScheduleResponse.getStatus() == 200) {
                        FancyToast.makeText(getActivity(), detailScheduleResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                        alertDialog.dismiss();
                    } else {
                        FancyToast.makeText(getActivity(), detailScheduleResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                    }
                });

            } else {
                FancyToast.makeText(getActivity(),"Lengkapi semua data!", FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
            }
        }
    };

    private View.OnClickListener editDetailSchedule = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (validate()) {
                String name = tilScheduleDetailName.getEditText().getText().toString();
                String day = tilScheduleDetailDay.getEditText().getText().toString();
                String startTimeText = tilScheduleDetailStartTime.getEditText().getText().toString();
                String endTimeText = tilScheduleDetailEndTime.getEditText().getText().toString();
                String startTime = DateConverter.toDbTimeFrom(mSimpleTimeFormat, startTimeText);
                String endTime = DateConverter.toDbTimeFrom(mSimpleTimeFormat, endTimeText);
                int idSchedule = Integer.parseInt(getArguments().getString("scheduleId"));
                int dayBefore = DoizeConstants.DAY_LIST.indexOf(globalDetailSchedule.getDaySchedule());

                globalDetailSchedule.setNameDetailSchedule(name);
                globalDetailSchedule.setDaySchedule(day);
                globalDetailSchedule.setIdSchedule(idSchedule);
                globalDetailSchedule.setStartTime(startTime);
                globalDetailSchedule.setEndTime(endTime);

                ProgressDialog progressDialog = ProgressDialog.show(requireContext(), "Schedule Detail", "Adding schedule detail...");
                mDetailScheduleViewModel.updateDetailSchedule(dayBefore, globalDetailSchedule).observe(getViewLifecycleOwner(), detailScheduleResponse -> {
                    if (detailScheduleResponse.getStatus() == 200) {
                        FancyToast.makeText(getActivity(), detailScheduleResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                        globalDetailSchedule = new DetailSchedule();
                        alertDialog.dismiss();
                    } else {
                        FancyToast.makeText(getActivity(), detailScheduleResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                    }

                    progressDialog.dismiss();
                });

            } else {
                FancyToast.makeText(getActivity(),"Lengkapi semua data!", FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
            }
        }
    };

    private void updateUI(List<List<DetailSchedule>> detailSchedule) {
        mScheduleDetailAdapter = new ScheduleDetailAdapter(detailSchedule);
        rvScheduleDetail.setAdapter(mScheduleDetailAdapter);
    }

    private void launchCustomAlertDialog(CrudType dialogType, DetailSchedule detailScheduleParam, View.OnClickListener onClickListener) {
        LayoutInflater inflater = getLayoutInflater();
        customAlertDialogView = inflater.inflate(R.layout.dialog_detail_schedule_form, null);

        tilScheduleDetailName = customAlertDialogView.findViewById(R.id.til_schedule_detail_name);
        tilScheduleDetailDay = customAlertDialogView.findViewById(R.id.til_schedule_detail_day);
        tilScheduleDetailStartTime = customAlertDialogView.findViewById(R.id.til_schedule_detail_start_time);
        tilScheduleDetailEndTime = customAlertDialogView.findViewById(R.id.til_schedule_detail_end_time);

        // Set day adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.layout_dropdown_item, DoizeConstants.DAY_LIST);
        actvScheduleDetailDay = customAlertDialogView.findViewById(R.id.actv_schedule_detail_day);
        actvScheduleDetailDay.setThreshold(1);
        actvScheduleDetailDay.setAdapter(adapter);

        if (dialogType == CrudType.EDIT) {
            String startTime = DateConverter.fromDbTimeTo(mSimpleTimeFormat, detailScheduleParam.getStartTime());
            String endTime = DateConverter.fromDbTimeTo(mSimpleTimeFormat, detailScheduleParam.getEndTime());

            tilScheduleDetailName.getEditText().setText(detailScheduleParam.getNameDetailSchedule());
            actvScheduleDetailDay.setText(detailScheduleParam.getDaySchedule(), false);
            tilScheduleDetailStartTime.getEditText().setText(startTime);
            tilScheduleDetailEndTime.getEditText().setText(endTime);
        }

        tilScheduleDetailStartTime.getEditText().setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            // get time from database
            Date time = (dialogType == CrudType.EDIT ? DateConverter.fromDbToDate(DateType.TIME, detailScheduleParam.getStartTime()) : new Date());

            TimePickerFragment dialog = TimePickerFragment.newInstance(DateType.TIME, tilScheduleDetailStartTime.getEditText(), time);
            dialog.setTargetFragment(ScheduleDetailFragment.this, 0);
            dialog.show(fragmentManager, "DialogTime");
        });

        tilScheduleDetailEndTime.getEditText().setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            // get time from database
            Date time = (dialogType == CrudType.EDIT ? DateConverter.fromDbToDate(DateType.TIME, detailScheduleParam.getStartTime()) : new Date());

            TimePickerFragment dialog = TimePickerFragment.newInstance(DateType.TIME, tilScheduleDetailEndTime.getEditText(), time);
            dialog.setTargetFragment(ScheduleDetailFragment.this, 0);
            dialog.show(fragmentManager, "DialogTime");
        });

        String title = dialogType == CrudType.ADD ? "Add" : "Update";

        mMaterialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
        mMaterialAlertDialogBuilder.setView(customAlertDialogView)
                .setTitle(title + " Detail Schedule")
                .setMessage("Enter your schedule details")
                .setPositiveButton(title, null)
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });

        alertDialog = mMaterialAlertDialogBuilder.show();
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(onClickListener);
    }

    public class ScheduleDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<List<DetailSchedule>> mDetailSchedules;

        public ScheduleDetailAdapter(List<List<DetailSchedule>> mDetailSchedules) {
            this.mDetailSchedules = mDetailSchedules;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            return new ScheduleDetailHolder(layoutInflater, parent);
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            List<DetailSchedule> detailSchedule = mDetailSchedules.get(position);
            String day = DoizeConstants.DAY_LIST.get(position);
            ((ScheduleDetailHolder) holder).bind(day, detailSchedule);
        }

        @Override
        public int getItemCount() {
            return mDetailSchedules.size();
        }

        private class ScheduleDetailHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView mDay;
            private RecyclerView rvItem;
            private List<DetailSchedule> listSchedule;
            private ScheduleDetailDayItemAdapter mScheduleDetailDayItemAdapter;

            public ScheduleDetailHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_row_schedule_detail, parent, false));
                itemView.setOnClickListener(this);

                mDay = itemView.findViewById(R.id.tv_schedule_day);
                rvItem = itemView.findViewById(R.id.rv_schedule_detail_item);
                rvItem.setLayoutManager(new LinearLayoutManager(parent.getContext()));

            }

            public void bind(String day, List<DetailSchedule> listSchedule) {
                this.listSchedule = listSchedule;
                mDay.setText(day);
                mScheduleDetailDayItemAdapter = new ScheduleDetailDayItemAdapter(listSchedule);
                rvItem.setAdapter(mScheduleDetailDayItemAdapter);
            }

            @Override
            public void onClick(View v) {

            }
        }
    }

    public class ScheduleDetailDayItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<DetailSchedule> mDetailSchedules;

        @SuppressLint("SimpleDateFormat")
        private final SimpleDateFormat mSimpleTimeFormat = new SimpleDateFormat("HH:mm");

        public ScheduleDetailDayItemAdapter(List<DetailSchedule> mDetailSchedules) {
            this.mDetailSchedules = mDetailSchedules;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            return new ScheduleDetailDayItemHolder(layoutInflater, parent);
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            DetailSchedule detailSchedule = mDetailSchedules.get(position);
            ((ScheduleDetailDayItemHolder) holder).bind(detailSchedule);
        }

        @Override
        public int getItemCount() {
            return mDetailSchedules.size();
        }

        private class ScheduleDetailDayItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView tvScheduleDetailName;
            private TextView tvScheduleDetailTime;
            private ImageButton editButton;
            private ImageButton deleteButton;

            private DetailSchedule mDetailSchedule;

            public ScheduleDetailDayItemHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_row_schedule_detail_item, parent, false));
                itemView.setOnClickListener(this);

                tvScheduleDetailName = itemView.findViewById(R.id.tv_schedule_detail_name);
                tvScheduleDetailTime = itemView.findViewById(R.id.tv_schedule_detail_time);
                editButton = itemView.findViewById(R.id.edit_detail_schedule);
                deleteButton = itemView.findViewById(R.id.del_detail_schedule);

                editButton.setOnClickListener(v -> {
                    globalDetailSchedule = mDetailSchedule;
                    launchCustomAlertDialog(CrudType.EDIT, mDetailSchedule, editDetailSchedule);
                });

                deleteButton.setOnClickListener(v -> {
                    new AlertDialog.Builder(getActivity())
                        .setTitle("Confirmation")
                        .setMessage("Do you really want to delete this item?")
                        .setIcon(R.drawable.ic_baseline_warning_amber_24)
                        .setPositiveButton("Yes", (dialog, whichButton) -> {
                            mDetailScheduleViewModel.deleteDetailSchedule(mDetailSchedule.getIdDetailSchedule()).observe(getViewLifecycleOwner(), detailScheduleResponse -> {
                                if (detailScheduleResponse.getStatus() == 200) {
                                    FancyToast.makeText(getActivity(), detailScheduleResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS,false).show();
                                } else {
                                    FancyToast.makeText(getActivity(), detailScheduleResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                                }
                            });

                        })
                        .setNegativeButton("No", null).show();
                });
            }

            @SuppressLint("SetTextI18n")
            public void bind(DetailSchedule detailSchedule) {
                mDetailSchedule = detailSchedule;
                tvScheduleDetailName.setText(mDetailSchedule.getNameDetailSchedule());

                String startTime = DateConverter.fromDbTimeTo(mSimpleTimeFormat, mDetailSchedule.getStartTime());
                String endTime = DateConverter.fromDbTimeTo(mSimpleTimeFormat, mDetailSchedule.getEndTime());
                tvScheduleDetailTime.setText(startTime + " - " + endTime);
            }

            @Override
            public void onClick(View v) {

            }
        }
    }

    public boolean validate() {
        boolean detailNameValidation = ValidationHelper.requiredTextInputValidation(tilScheduleDetailName);
        boolean detailDayValidation = ValidationHelper.requiredTextInputValidation(tilScheduleDetailDay);
        boolean detailStartTimeValidation = ValidationHelper.requiredTextInputValidation(tilScheduleDetailStartTime);
        boolean detailEndTimeValidation = ValidationHelper.requiredTextInputValidation(tilScheduleDetailEndTime);

        return detailNameValidation && detailDayValidation && detailStartTimeValidation && detailEndTimeValidation;
    }
}
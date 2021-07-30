package id.kelompok04.doize.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.adapter.ScheduleDetailAdapter;
import id.kelompok04.doize.architecture.viewmodel.DetailScheduleViewModel;
import id.kelompok04.doize.architecture.viewmodel.ScheduleViewModel;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.DoizeConstants;
import id.kelompok04.doize.model.DetailSchedule;
import id.kelompok04.doize.model.Schedule;

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

    private SimpleDateFormat mSimpleTimeFormat = new SimpleDateFormat("HH:mm");

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
        customAlertDialogView = inflater.inflate(R.layout.dialog_detail_schedule_form, container, false);

        rvScheduleDetail = view.findViewById(R.id.rv_schedule_detail);
        rvScheduleDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvScheduleDetail.setAdapter(mScheduleDetailAdapter);

        mTvScheduleName = view.findViewById(R.id.tv_schedule_name);
        mTvScheduleDesc = view.findViewById(R.id.tv_schedule_description);

        tilScheduleDetailName = customAlertDialogView.findViewById(R.id.til_schedule_detail_name);
        tilScheduleDetailDay = customAlertDialogView.findViewById(R.id.til_schedule_detail_day);
        tilScheduleDetailStartTime = customAlertDialogView.findViewById(R.id.til_schedule_detail_start_time);
        tilScheduleDetailEndTime = customAlertDialogView.findViewById(R.id.til_schedule_detail_end_time);

        mMaterialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.layout_dropdown_item, DoizeConstants.DAY_LIST);
        actvScheduleDetailDay = customAlertDialogView.findViewById(R.id.actv_schedule_detail_day);
        actvScheduleDetailDay.setThreshold(1);
        actvScheduleDetailDay.setAdapter(adapter);

        fabAddDetailSchedule = view.findViewById(R.id.fab_add_detail_schedule);
        fabAddDetailSchedule.setOnClickListener(v -> {
            launchCustomAlertDialog(v);
        });

        String name = getArguments().getString("scheduleName");
        String desc = getArguments().getString("scheduleDesc");

        mTvScheduleName.setText(name);
        mTvScheduleDesc.setText(desc);

        tilScheduleDetailStartTime.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(tilScheduleDetailStartTime.getEditText(), getContext(), new Date());
                dialog.setTargetFragment(ScheduleDetailFragment.this, 0);
                dialog.show(fragmentManager, "DialogTime");
            }
        });

        tilScheduleDetailEndTime.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(tilScheduleDetailEndTime.getEditText(), getContext(), new Date());
                dialog.setTargetFragment(ScheduleDetailFragment.this, 0);
                dialog.show(fragmentManager, "DialogTime");
            }
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

    private void updateUI(List<List<DetailSchedule>> detailSchedule) {
        mScheduleDetailAdapter = new ScheduleDetailAdapter(detailSchedule);
        rvScheduleDetail.setAdapter(mScheduleDetailAdapter);
    }


    private void launchCustomAlertDialog(View view) {
//        mScheduleNameLayout = customAlertDialogView.findViewById(R.id.til_schedule_name);
//        mScheduleDescriptionLayout = customAlertDialogView.findViewById(R.id.til_schedule_desc);
//        String[] days = new String[DoizeConstants.DAY_LIST.size()];
//        DoizeConstants.DAY_LIST.toArray(days);


        mMaterialAlertDialogBuilder.setView(customAlertDialogView)
                .setTitle("Add Detail Schedule")
                .setMessage("Enter your schedule details")
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog alertDialog = mMaterialAlertDialogBuilder.show();
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v -> {

        });
    }

}
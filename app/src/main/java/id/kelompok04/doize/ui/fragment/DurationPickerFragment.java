package id.kelompok04.doize.ui.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.Format;
import java.util.Calendar;
import java.util.Date;

import id.kelompok04.doize.R;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.helper.DateType;
import id.kelompok04.doize.helper.DoizeConstants;

public class DurationPickerFragment extends DialogFragment {
    private static final String ARG_TIME = "time";
    private EditText textInputEditText;
    private DateType mDateType;
    private View customAlertDialogView;
    private MaterialAlertDialogBuilder mMaterialAlertDialogBuilder;
    private AlertDialog mAlertDialog;

    public DurationPickerFragment(DateType dateType, EditText textInputEditText) {
        this.textInputEditText = textInputEditText;
        mDateType = dateType;
    }

    public static DurationPickerFragment newInstance(DateType dateType, EditText textInputEditText, Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);
        DurationPickerFragment fragment = new DurationPickerFragment(dateType, textInputEditText);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getLayoutInflater();
        customAlertDialogView = inflater.inflate(R.layout.dialog_duration_picker, null);

//        if (crudType == CrudType.EDIT) {
//            mScheduleNameLayout.getEditText().setText(schedule.getNameSchedule());
//            mScheduleDescriptionLayout.getEditText().setText(schedule.getDescriptionSchedule());
//        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(customAlertDialogView);
        NumberPicker npMinutes = customAlertDialogView.findViewById(R.id.number_picker_minutes);
        npMinutes.setMinValue(0);
        npMinutes.setMaxValue(60);
//        mMaterialAlertDialogBuilder.setView(customAlertDialogView)
//                .setTitle(title + " Time")
//                .setPositiveButton(title, null)
//                .setNegativeButton("Cancel", (dialog, which) -> {
//                    dialog.dismiss();
//                });

//        positiveButton.setOnClickListener(nu);

//        TimePickerDialog.OnTimeSetListener timeListener;
//
//        Date date = (Date) getArguments().getSerializable(ARG_TIME);
//
//        timeListener = (view, hourOfDay, minute) -> {
//            Calendar time = Calendar.getInstance();
//            time.setTime(date);
//            time.set(Calendar.HOUR_OF_DAY, hourOfDay);
//            time.set(Calendar.MINUTE, minute);
//
//            Date resultTime = time.getTime();
//            Format formatter = (mDateType == DateType.TIME ? DoizeConstants.TIME_FORMAT : DoizeConstants.FULL_FORMAT);
//
//            String resultTimeString = formatter.format(resultTime);
//            textInputEditText.setText(resultTimeString);
//        };
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        int initialHour = calendar.get(Calendar.HOUR);
//        int initialMinute = calendar.get(Calendar.MINUTE);

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

//        mAlertDialog = mMaterialAlertDialogBuilder.show();
//        Button positiveButton = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
//        int darkPurple = getResources().getColor(R.color.darkPurple);
//
//        Button positiveButton = ((TimePickerDialog) getDialog()).getButton(TimePickerDialog.BUTTON_POSITIVE);
//        positiveButton.setTextColor(darkPurple);
//
//        Button negativeButton = ((TimePickerDialog) getDialog()).getButton(TimePickerDialog.BUTTON_NEGATIVE);
//        negativeButton.setTextColor(darkPurple);
    }
}

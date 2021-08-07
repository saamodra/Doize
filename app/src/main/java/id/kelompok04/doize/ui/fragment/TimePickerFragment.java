package id.kelompok04.doize.ui.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.Format;
import java.util.Calendar;
import java.util.Date;

import id.kelompok04.doize.R;
import id.kelompok04.doize.helper.DateType;
import id.kelompok04.doize.helper.DoizeConstants;

public class TimePickerFragment extends DialogFragment {
    private static final String ARG_TIME = "time";
    private EditText textInputEditText;
    private DateType mDateType;

    public TimePickerFragment(DateType dateType, EditText textInputEditText) {
        this.textInputEditText = textInputEditText;
        mDateType = dateType;
    }

    public static TimePickerFragment newInstance(DateType dateType, EditText textInputEditText, Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);
        TimePickerFragment fragment = new TimePickerFragment(dateType, textInputEditText);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        TimePickerDialog.OnTimeSetListener timeListener;

        Date date = (Date) getArguments().getSerializable(ARG_TIME);

        timeListener = (view, hourOfDay, minute) -> {
            Calendar time = Calendar.getInstance();
            time.setTime(date);
            time.set(Calendar.HOUR_OF_DAY, hourOfDay);
            time.set(Calendar.MINUTE, minute);

            Date resultTime = time.getTime();
            Format formatter = (mDateType == DateType.TIME ? DoizeConstants.TIME_FORMAT : DoizeConstants.FULL_FORMAT);

            String resultTimeString = formatter.format(resultTime);
            textInputEditText.setText(resultTimeString);
        };

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int initialHour = calendar.get(Calendar.HOUR_OF_DAY);
        int initialMinute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(
                requireContext(),
                R.style.DialogTheme,
                timeListener,
                initialHour,
                initialMinute,
                true
        );
    }

    @Override
    public void onStart() {
        super.onStart();

        int darkPurple = getResources().getColor(R.color.darkPurple);

        Button positiveButton = ((TimePickerDialog) getDialog()).getButton(TimePickerDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(darkPurple);

        Button negativeButton = ((TimePickerDialog) getDialog()).getButton(TimePickerDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(darkPurple);
    }
}

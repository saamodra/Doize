package id.kelompok04.doize.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import id.kelompok04.doize.R;
import id.kelompok04.doize.helper.DateType;
import id.kelompok04.doize.helper.DoizeConstants;

public class DatePickerFragment extends DialogFragment {
    private static final String TAG = "DatePickerFragment";
    private static final String ARG_DATE = "date";

    private EditText textInputEditText;
    private DateType mDateType;

    public DatePickerFragment(DateType dateType, EditText textInputEditText) {
        this.textInputEditText = textInputEditText;
        mDateType = dateType;
    }

    public static DatePickerFragment newInstance(DateType dateType, EditText textInputEditText, Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment(dateType, textInputEditText);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int initialYear = calendar.get(Calendar.YEAR);
        int initialMonth = calendar.get(Calendar.MONTH);
        int initialDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateListener;

        switch (mDateType) {
            case TIME:
                TimePickerDialog.OnTimeSetListener timeListener = (view, hourOfDay, minute) -> {
                    Calendar time = Calendar.getInstance();
                    time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    time.set(Calendar.MINUTE, minute);

                    Date resultTime = time.getTime();

                    String resultTimeString = DoizeConstants.TIME_FORMAT.format(resultTime);
                    textInputEditText.setText(resultTimeString);
                };

                return new TimePickerDialog(requireContext(), R.style.DialogTheme, timeListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

            case DATETIME:
                Log.d(TAG, "onCreateDialog: " + "DATE TIME");
                dateListener = (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    TimePickerFragment.newInstance(DateType.DATETIME, textInputEditText, calendar.getTime()).show(getParentFragmentManager(), "time");
                };

                return new DatePickerDialog(
                        requireContext(),
                        R.style.DialogTheme,
                        dateListener,
                        initialYear,
                        initialMonth,
                        initialDay);
        }

        dateListener = (view, year, month, dayOfMonth) -> {
            Date resultDate = new GregorianCalendar(year, month, dayOfMonth).getTime();

            Format formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            String resultDateString = formatter.format(resultDate);
            textInputEditText.setText(resultDateString);
        };


        return new DatePickerDialog(
                requireContext(),
                R.style.DialogTheme,
                dateListener,
                initialYear,
                initialMonth,
                initialDay);
    }

    @Override
    public void onStart() {
        super.onStart();

        int darkPurple = getResources().getColor(R.color.darkPurple);

        Button positiveButton = ((DatePickerDialog) getDialog()).getButton(DatePickerDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(darkPurple);

        Button negativeButton = ((DatePickerDialog) getDialog()).getButton(DatePickerDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(darkPurple);
    }
}

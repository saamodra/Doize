package id.kelompok04.doize.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.kelompok04.doize.R;

public class TimePickerFragment extends DialogFragment {
    private static final String ARG_TIME = "time";
    private EditText textInputEditText;
    private Context context;


    public TimePickerFragment(EditText textInputEditText, Context context) {
        this.textInputEditText = textInputEditText;
        this.context = context;
    }

    public static TimePickerFragment newInstance(EditText textInputEditText, Context context, Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);
        TimePickerFragment fragment = new TimePickerFragment(textInputEditText, context);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        TimePickerDialog.OnTimeSetListener timeListener;

        timeListener = (view, hourOfDay, minute) -> {
            Calendar time = Calendar.getInstance();
            time.set(Calendar.HOUR_OF_DAY, hourOfDay);
            time.set(Calendar.MINUTE, minute);

            Date resultTime = time.getTime();
            Format formatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

            String resultTimeString = formatter.format(resultTime);
            textInputEditText.setText(resultTimeString);
        };

        Date date = (Date) getArguments().getSerializable(ARG_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int initialHour = calendar.get(Calendar.HOUR);
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

        Button positiveButton = ((DatePickerDialog) getDialog()).getButton(DatePickerDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(darkPurple);

        Button negativeButton = ((DatePickerDialog) getDialog()).getButton(DatePickerDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(darkPurple);
    }
}

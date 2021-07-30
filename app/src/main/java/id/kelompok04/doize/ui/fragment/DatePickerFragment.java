package id.kelompok04.doize.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

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

public class DatePickerFragment extends DialogFragment {
    private static final String TAG = "DatePickerFragment";
    private static final String ARG_DATE = "date";

    private EditText textInputEditText;
    private Context mContext;

    public DatePickerFragment(EditText textInputEditText, Context context) {
        this.textInputEditText = textInputEditText;
        this.mContext = context;
    }

    public static DatePickerFragment newInstance(EditText textInputEditText, Context context, Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment(textInputEditText, context);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        DatePickerDialog.OnDateSetListener dateListener;

        dateListener = (view, year, month, dayOfMonth) -> {
            Date resultDate = new GregorianCalendar(year, month, dayOfMonth).getTime();

            Format formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            String resultDateString = formatter.format(resultDate);
            textInputEditText.setText(resultDateString);
        };


        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int initialYear = calendar.get(Calendar.YEAR);
        int initialMonth = calendar.get(Calendar.MONTH);
        int initialDay = calendar.get(Calendar.DAY_OF_MONTH);

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

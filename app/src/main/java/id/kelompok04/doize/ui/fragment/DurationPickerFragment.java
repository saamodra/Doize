package id.kelompok04.doize.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
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
import java.util.StringTokenizer;

import id.kelompok04.doize.R;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.helper.CustomTime;
import id.kelompok04.doize.helper.DateType;
import id.kelompok04.doize.helper.DoizeConstants;

public class DurationPickerFragment extends DialogFragment {
    private static final String TAG = "DurationPickerFragment";
    private static final String ARG_TIME = "time";

    private EditText textInputEditText;
    private CustomTime mCustomTime;
    private View customAlertDialogView;
    private AlertDialog mAlertDialog;
    private NumberPicker npMinutes, npSeconds;
    private Button saveButton, cancelButton;


    public DurationPickerFragment(EditText textInputEditText, CustomTime customTime) {
        this.textInputEditText = textInputEditText;
        mCustomTime = customTime;
    }

    public static DurationPickerFragment newInstance(EditText textInputEditText, CustomTime date) {
        return new DurationPickerFragment(textInputEditText, date);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getLayoutInflater();
        customAlertDialogView = inflater.inflate(R.layout.dialog_duration_picker, null);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(customAlertDialogView);
        npMinutes = customAlertDialogView.findViewById(R.id.number_picker_minutes);
        npSeconds = customAlertDialogView.findViewById(R.id.number_picker_seconds);
        npMinutes.setMinValue(0);
        npMinutes.setMaxValue(60);
        npMinutes.setValue(mCustomTime.getMinutes());

        npSeconds.setMinValue(0);
        npSeconds.setMaxValue(60);
        npSeconds.setValue(mCustomTime.getSeconds());

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        mAlertDialog = (AlertDialog) getDialog();
        saveButton = customAlertDialogView.findViewById(R.id.action_save);
        cancelButton = customAlertDialogView.findViewById(R.id.cancel_button);

        saveButton.setOnClickListener(v -> {
            @SuppressLint("DefaultLocale") String time = String.format("%02d:%02d", npMinutes.getValue(), npSeconds.getValue());
            textInputEditText.setText(time);
            mAlertDialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> {
            mAlertDialog.dismiss();
        });

    }
}

package id.kelompok04.doize.helper;

import android.text.TextUtils;

import com.google.android.material.textfield.TextInputLayout;

public class ValidationHelper {
    public static boolean requiredTextInputValidation(TextInputLayout textInputLayout) {
        String value = textInputLayout.getEditText().getText().toString();

        boolean result = true;
        if(!TextUtils.isEmpty(value)) {
            textInputLayout.setErrorEnabled(false);
        } else {
            textInputLayout.setError("Input required!");
            textInputLayout.setErrorEnabled(true);
            result = false;
        }

        return result;
    }

    public static boolean confirmationValidation(TextInputLayout firstField, TextInputLayout secondField) {
        String firstFieldValue = firstField.getEditText().getText().toString();
        String secondFieldValue = secondField.getEditText().getText().toString();

        if (TextUtils.isEmpty(secondFieldValue)) {
            secondField.setError("Input required!");
            secondField.setErrorEnabled(true);

            return false;
        }

        if (!TextUtils.equals(firstFieldValue, secondFieldValue)) {
            secondField.setError("Confirmation password does not match!");
            secondField.setErrorEnabled(true);

            return false;
        }

        secondField.setErrorEnabled(false);

        return true;
    }
}

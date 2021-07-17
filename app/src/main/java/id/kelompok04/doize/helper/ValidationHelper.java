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
}

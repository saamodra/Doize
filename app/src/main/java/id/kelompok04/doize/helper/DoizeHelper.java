package id.kelompok04.doize.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class DoizeHelper {
    public static String getString(String text) {
        return text != null ? text : "";
    }

    public static int getIdUserPref(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE);

        return Integer.parseInt(preferences.getString("id", "0"));
    }
}

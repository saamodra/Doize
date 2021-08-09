package id.kelompok04.doize.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import id.kelompok04.doize.model.Assignment;

public class DoizeHelper {
    public static String getString(String text) {
        return text != null ? text : "";
    }

    public static int getIdUserPref(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE);

        return Integer.parseInt(preferences.getString("id", "0"));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<Assignment> getDashboardAssignments(List<Assignment> assignments) {
        return assignments == null ? Collections.emptyList() : assignments.stream().filter(assignment -> {
            String duedate = DateConverter.fromDbDateTimeTo(DoizeConstants.DATE_FORMAT, assignment.getDuedateAssignment());
            String dateNow = DoizeConstants.DATE_FORMAT.format(new Date());
            return duedate.equals(dateNow);
        }).limit(2).collect(Collectors.toList());
    }
}

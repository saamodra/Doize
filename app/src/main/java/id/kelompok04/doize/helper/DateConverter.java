package id.kelompok04.doize.helper;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat dbTimeFormat = new SimpleDateFormat("HH:mm:ss");

    public static String toDateString(SimpleDateFormat formatDateFrom, SimpleDateFormat formatDateTo, String date) {
        Date dateDb = new Date();
        try {
            dateDb =  formatDateFrom.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatDateTo.format(dateDb);
    }

    public static String fromDbTo(SimpleDateFormat formatDateTo, String date) {
        return toDateString(dbDateFormat, formatDateTo, date);
    }

    public static String toDbFrom(SimpleDateFormat formatDateFrom, String date) {
        return toDateString(formatDateFrom, dbDateFormat, date);
    }

    public static String fromDbTimeTo(SimpleDateFormat formatDateTo, String date) {
        return toDateString(dbTimeFormat, formatDateTo, date);
    }

    public static String toDbTimeFrom(SimpleDateFormat formatDateFrom, String date) {
        return toDateString(formatDateFrom, dbTimeFormat, date);
    }
}

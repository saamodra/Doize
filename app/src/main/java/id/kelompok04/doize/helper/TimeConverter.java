package id.kelompok04.doize.helper;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeConverter {
    private static final String TAG = "TimeConverter";

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat dbTimeFormat = new SimpleDateFormat("HH:mm:ss");

    public static Date fromDbToTime(String time) {
        Date dateDb = new Date();
        try {
            dateDb = dbTimeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return dateDb;
    }

    public static long fromDbToMilliseconds(String time) {
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(fromDbToTime(time));
        int minutes = timeCal.get(Calendar.MINUTE);
        int seconds = timeCal.get(Calendar.SECOND);

        return TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds);
    }

    @SuppressLint("DefaultLocale")
    public static String fromDbToString(String time) {
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(fromDbToTime(time));
        int minutes = timeCal.get(Calendar.MINUTE);
        int seconds = timeCal.get(Calendar.SECOND);

        return String.format("%02d:%02d", minutes, seconds);
    }

    public static String toDbFromMilliseconds(long milli) {
        return dbTimeFormat.format(new Date(milli));
    }

    public static String toDbFromStringFormatted(String time) {
        return "00:" + time;
    }

    public static CustomTime fromMilliToTime(long milli) {
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(milli);
        int seconds = (int) ((int) TimeUnit.MILLISECONDS.toSeconds(milli) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milli)));

        return new CustomTime(minutes, seconds);
    }

    public static CustomTime fromDbToCustomTime(String time) {
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(fromDbToTime(time));
        int minutes = timeCal.get(Calendar.MINUTE);
        int seconds = timeCal.get(Calendar.SECOND);

        return new CustomTime(minutes, seconds);
    }

    @SuppressLint("DefaultLocale")
    public static String formatTime(long milliseconds)
    {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }

    @SuppressLint("DefaultLocale")
    public static String formatTimeDb(long milliseconds)
    {
        return String.format("00:%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }
}

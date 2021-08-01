package id.kelompok04.doize.helper;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoizeConstants {
    public static final List<String> DAY_LIST = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");

    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat FULL_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
}

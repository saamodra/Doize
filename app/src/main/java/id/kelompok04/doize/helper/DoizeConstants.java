package id.kelompok04.doize.helper;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoizeConstants {
    public static final List<String> DAY_LIST = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");

    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat fullFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
}

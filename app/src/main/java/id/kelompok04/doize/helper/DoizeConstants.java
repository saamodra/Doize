package id.kelompok04.doize.helper;

import android.annotation.SuppressLint;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

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

    public static final String NOTIFICATION_CHANNEL_ID = "Doize Notification";

    public static final Animation BLINK() {
        Animation blink = new AlphaAnimation(0.0f, 1.0f);
        blink.setDuration(500); //You can manage the time of the blink with this parameter
        blink.setStartOffset(20);
        blink.setRepeatMode(Animation.REVERSE);
        blink.setRepeatCount(Animation.INFINITE);
        return blink;
    }

}

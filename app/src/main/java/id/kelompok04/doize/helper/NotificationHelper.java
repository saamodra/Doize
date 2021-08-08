package id.kelompok04.doize.helper;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import id.kelompok04.doize.R;

public class NotificationHelper {
    private static final String TAG = "NotificationHelper";

    public static void show(Context context, String channelID, String title, String content) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setColor(context.getResources().getColor(R.color.darkPurple));
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification));
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setAutoCancel(true);

        notificationManager.notify(1, builder.build());
    }

    public static void show(Context context, String channelID, PendingIntent intent, String title, String content) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setColor(context.getResources().getColor(R.color.darkPurple));
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification));
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setAutoCancel(true);
        builder.setContentIntent(intent);

        notificationManager.notify(1, builder.build());
    }

    public static void setAlarm(Context context, int type, int id, long time, String title, String content) {
        AlarmManager mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);

        String requestCode = type + String.valueOf(id);
        Log.d(TAG, "setAlarm: " + requestCode + " & " + title + " & " + content);
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(requestCode), intent, 0);
        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, time, mPendingIntent);
    }

    public static void cancelAlarm(Context context, int type, int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        String requestCode = type + String.valueOf(id);
        Log.d(TAG, "cancelAlarm: " + requestCode);
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(requestCode), intent, 0);
        mPendingIntent.cancel();
        if (alarmManager == null) {
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(mPendingIntent);
    }
}

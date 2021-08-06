package id.kelompok04.doize.helper;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import id.kelompok04.doize.R;

public class NotificationHelper {
    public static void show(Context context, String title, String content) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, DoizeConstants.NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_doize_foreground);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setAutoCancel(true);

        notificationManager.notify(1, builder.build());

    }
}

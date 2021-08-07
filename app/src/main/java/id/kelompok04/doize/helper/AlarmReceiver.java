package id.kelompok04.doize.helper;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import id.kelompok04.doize.ui.activity.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);

        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        Log.d(TAG, "onReceive: " + title + " & " + content);
        NotificationHelper.show(context, DoizeConstants.NOTIFICATION_CHANNEL_ID, pendingIntent, title, content);
    }
}

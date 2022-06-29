package pl.r.mmdd_pum_projekt.Helpers;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import pl.r.mmdd_pum_projekt.R;

public class NotifyHelper {
    private int notificationId;

    private  Context context;

    private  Activity activity;

    private static NotifyHelper notifyHelper_instance = null;


    public static NotifyHelper getInstance(Context context, Activity activity){
        if(notifyHelper_instance == null){
            notifyHelper_instance = new NotifyHelper(context,activity);
        }
        return notifyHelper_instance;
    }

    private NotifyHelper() {
    }

    private NotifyHelper(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "notify";
            String description = "Channel for notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = this.context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void sendNotification(String title, String text) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, "notify")
                .setSmallIcon(R.drawable.ic_baseline_language_24)
                .setContentText(text)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this.context);
        if (notificationId > 99) {
            notificationId = 0;
        }
        notificationId++;

        notificationManagerCompat.notify(notificationId, builder.build());
    }
}

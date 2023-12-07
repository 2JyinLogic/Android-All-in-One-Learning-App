package com.can301.gp.demos;

import android.app.ForegroundServiceStartNotAllowedException;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.can301.gp.Demonstration;
import com.can301.gp.R;

public class FGServiceExampleService extends Service {

    /*
     *  Static keys
     */
    // Used to pass the action performed on the notification via an intent to this.
    public static final String NOTIFICATION_ACTION_KET = "action key";
    // Used for the notification channel.
    public static final String NOTIFICATION_CHANNEL_ID = "Music Play";
    // Used for the action buttons on the notification.
    public static final String ACTION_AGREE = "action agree";
    public static final String ACTION_DISMISS = "action dismiss";
    // Used for ACTION_REPLY's message
    public static final String REPLY_MSG_KEY = "reply message";

    // The notification channel. As it is always the same,
    // it will be static.
    static NotificationChannel channel = null;

    // If the service is currently in foreground.
    // If it is not, it will be brought to foreground in onStartCommand()
    boolean in_fg = false;
    // The ID used to identify the notification so that it can be later modified or cancelled.
    int notification_id = 100;

    public FGServiceExampleService() {}

    /**
     * Create the notification channel on creation
     */
    @Override
    public void onCreate() {
        // Create the notification channel
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startForegroundService(),

        // if the service is not in foreground,
        // then bring itself to foreground by building a notification.
        if (!in_fg) {
            createNotificationAndGoFG();
            in_fg = true;
        }

        // The example service doesn't need to retain the intent.
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Don't need binding in this example
        return null;
    }

    @Override
    public void onDestroy() {
        // Don't forget to release resources or the service won't stop.
        super.onDestroy();
    }

    /**
     * Starting in Android 8.0 (API level 26), all notifications must be assigned to a channel.
     * Creating a channel with the same parameters more than once does nothing.
     * So it's safe to call it whenever the service first starts.
     * https://developer.android.com/develop/ui/views/notifications/channels
     */
    void createNotificationChannel() {
        // Skip if it's already created.
        if(channel != null) {
            return;
        }

        // Do this only when the API level >= 26
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        CharSequence name = getString(R.string.fg_example_channel_name);
        String description = getString(R.string.fg_example_channel_desc);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLightColor(Color.BLUE);
        // Register the channel with the system. Can't change the importance
        // or other notification behaviors after this.
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    /**
     * Create the notification that is to be displayed in foreground.
     * And go foreground
     */
    void createNotificationAndGoFG() {
        try
        {
            // Create the AGREE action
            PendingIntent agreePendingIntent = buildAgreeMessageIntent();
            // Create the DISMISS action
            PendingIntent dismissPendingIntent = buildDismissMessageIntent();
            // Create the Notification
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_fg_service)
                            .setContentTitle("Love Request")
                            .setContentText("Your secret admirer wants to ask you out.")
                            .addAction(R.drawable.ic_action_agree, "AGREE",
                                    agreePendingIntent)
                            .addAction(R.drawable.ic_action_dismiss, "DISMISS",
                                    dismissPendingIntent)
                            .setPriority(NotificationCompat.PRIORITY_HIGH);
            Notification notification = builder.build();

            // Needs to declare the FG service type starting with
            // API level 30.
            int type = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                // This service is imagined to fetch message from remote server
                // (although it does not in this example)
                type = ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC;
            }
            this.startForeground(notification_id, notification, type);
        } catch (Exception e) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                    e instanceof ForegroundServiceStartNotAllowedException
            ) {
                // App not in a valid state to start foreground service
                // (e.g started from bg)
                e.printStackTrace();
            }
            // ...
        }
    }

    /**
     * Builds the PendingIntent for the user's click on REPLY on the notification.
     * @return the PendingIntent built
     */
    PendingIntent buildAgreeMessageIntent() {
        // Go to the effect activity for the foreground service.
        Intent replyIntent = new Intent(this, FGServiceExample.class);
        replyIntent.setAction(ACTION_AGREE);
        replyIntent.putExtra(Demonstration.EFFECT_DEMO_TITLE_KEY, R.string.demo_title_fgservice);
        replyIntent.putExtra(Demonstration.EFFECT_DEMO_CODE_ID_KEY, "fgservice");
        return PendingIntent.getActivity(
                this, 0, replyIntent, PendingIntent.FLAG_IMMUTABLE);
    }

    /**
     * Builds the PendingIntent for the user's click on DISSMISS on the notification.
     * @return the PendingIntent built
     */
    PendingIntent buildDismissMessageIntent() {
        // Go to the effect activity for the foreground service.
        Intent replyIntent = new Intent(this, FGServiceExample.class);
        replyIntent.setAction(ACTION_DISMISS);
        replyIntent.putExtra(Demonstration.EFFECT_DEMO_TITLE_KEY, R.string.demo_title_fgservice);
        replyIntent.putExtra(Demonstration.EFFECT_DEMO_CODE_ID_KEY, "fgservice");
        return PendingIntent.getActivity(
                this, 0, replyIntent, PendingIntent.FLAG_IMMUTABLE);
    }

    /**
     * Stops the service (self).
     */
    void stop() {
        stopSelf();
    }
}
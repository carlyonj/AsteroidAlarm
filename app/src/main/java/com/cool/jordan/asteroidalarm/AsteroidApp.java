package com.cool.jordan.asteroidalarm;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import AsteroidData.Asteroid;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AsteroidApp extends Application {
    public static final String CHANNEL_ID = "ASTEROID_ALARM_0";
    public static final int DANGEROUS_ASTEROID = 4001;
    public static final int DAILY_ASTEROID = 4002;
    public static final String ASTEROID_PREF = "asteroid_pref";
    public static final String DAILY_KEY = "asteroid_daily";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        AsteroidJobService.initializeDangerousCheck(this);
        AsteroidJobService.initializeDailyCheck(this);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();

            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.setSound(Uri.parse("android.resource://" + this.getPackageName() +
                                        "/" + R.raw.alert), audioAttributes);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }

        }
    }

    public static void sendNotification(Context context, Asteroid asteroid) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(asteroid.getNasaJplUrl()));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        if (context != null) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Emergency Alert")
                    .setContentText("Asteroid Impact Warning")
                    .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.alert))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSmallIcon(R.drawable.ic_baseline_warning)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText("Asteroid Impact Warning in effect: potential impact on " + asteroid.getCloseApproachData().get(0).getCloseApproachDate()))
                    .setContentIntent(pendingIntent)
                    .setLights(Color.RED, 1000, 250);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(675, mBuilder.build());
        }
    }
}

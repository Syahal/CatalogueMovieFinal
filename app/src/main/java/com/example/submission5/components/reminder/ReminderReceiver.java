package com.example.submission5.components.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.submission5.BuildConfig;
import com.example.submission5.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ReminderReceiver extends BroadcastReceiver {
    private static final String API_KEY = BuildConfig.TMBD_API_KEY;
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private String title, message;

    public ReminderReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        message = intent.getStringExtra(EXTRA_MESSAGE);

        if (message.equalsIgnoreCase("EXTRA_MESSAGE")) {
            getNewRelease(context);
        } else {
            title = "Submission 5";
            showReminderNotification(context, title, message);
        }
    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    private void showReminderNotification(Context context, String title, String message) {
        String CHANNEL_ID = "channel_1";
        String CHANNEL_NAME = "remindermanager channel";

        int requestCode = message.equalsIgnoreCase("EXTRA_MESSAGE")? 102 : 101;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri uriReminderSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notif_movie)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000})
                .setSound(uriReminderSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[] {1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        Notification notification = builder.build();

        if (notificationManager != null) {
            notificationManager.notify(requestCode, notification);
        }
    }

    public void setDailyReminder(Context context, String time, String message) {
        String TIME_FORMAT = "HH:mm";

        if (isDateInvalid(time, TIME_FORMAT))
            return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, intent, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void setNewReleaseReminder(Context context, String time, String message) {
        String TIME_FORMAT = "HH:mm";

        if (isDateInvalid(time, TIME_FORMAT))
            return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 102, intent, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelDailyReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public void cancelNewReleaseReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 102, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private void getNewRelease(final Context context) {
        AsyncHttpClient httpClient = new AsyncHttpClient();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&primary_release_date.gte="
                + currentDate + "&primary_release_date.lte=" + currentDate;

        httpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("resutls");

                    title = "New Released Movie";
                    message = "";

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject releasedMovie = jsonArray.getJSONObject(i);
                        message = message + releasedMovie.getString("title") + ", ";
                    }
                    showReminderNotification(context, title, message);
                } catch (Exception e) {
                    Log.d("errorNewRelease", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("ConnectNewRelease", error.getMessage());
            }
        });
    }
}

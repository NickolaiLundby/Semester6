package com.runekeena.au297052movies.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.runekeena.au297052movies.R;
import com.runekeena.au297052movies.activities.DetailsActivity;
import com.runekeena.au297052movies.activities.Overview.OverviewActivity;
import com.runekeena.au297052movies.database.DatabaseApp;
import com.runekeena.au297052movies.database.MovieDatabase;
import com.runekeena.au297052movies.model.Movie;
import com.runekeena.au297052movies.utils.MovieHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotificationService extends Service {
    public NotificationService() {
    }

    //Variables
    boolean running=false;

    //Constants
    private static final String CHANNEL_ID = "notify_channel";
    private static final int NOTIFY_ID = 1;
    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";
    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent != null)
        {
            String action = intent.getAction();

            switch (action)
            {
                case ACTION_START_FOREGROUND_SERVICE:
                    if(!running){
                        Log.d("Notify Service", "Foreground service is started.");
                        running = true;
                        startForegroundService();
                    }
                    break;
                case ACTION_STOP_FOREGROUND_SERVICE:
                    if(running){
                        Log.d("Notify Service", "Foreground service is stopped.");
                        running = false;
                        stopForegroundService();
                    }
                    break;
                default:
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void startForegroundService() {
        startForeground(NOTIFY_ID, getNotification(""));
        final Handler handler = new Handler();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(running){
                    new UWDBTask().execute("Get Unwatched");
                    handler.postDelayed(this, 120000);
                }

            }
        }, 0);
    }

    private void stopForegroundService()
    {
        Log.d("Notify Service", "Stop foreground service.");
        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }

    private Notification getNotification(String title) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notify Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        //PendingIntent contentIntent= PendingIntent.getActivity(this, 0,new Intent(this, OverviewActivity.class),0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getText(R.string.have_not_watched))
                .setContentText(title)
                .setOnlyAlertOnce(false)
                .setOngoing(true);
                //.setContentIntent(contentIntent);

        Intent stopIntent = new Intent(this, NotificationService.class);
        stopIntent.setAction(ACTION_STOP_FOREGROUND_SERVICE);
        PendingIntent pendingPrevIntent = PendingIntent.getService(this, 0, stopIntent, 0);
        NotificationCompat.Action prevAction = new NotificationCompat.Action(android.R.drawable.ic_media_pause, "Stop Notifications", pendingPrevIntent);
        builder.addAction(prevAction);

        return builder.build();
    }

    private void updateNotification(String title) {

        Notification notification=getNotification(title);

        NotificationManager mNotificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFY_ID,notification);
    }

    private class UWDBTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.d("Database", params[0]);
            DatabaseApp dba = (DatabaseApp) getApplicationContext();
            MovieDatabase mdb = dba.getDatabase();

            List<Movie> uwList = mdb.movieDao().getAllUnwatched();
            Random rand = new Random();
            Movie randomUWM = uwList.get(rand.nextInt(uwList.size()));

            return randomUWM.getTitle();
        }

        @Override
        protected void onPostExecute(String p) {
            super.onPostExecute(p);
            updateNotification(p);
        }
    }
}

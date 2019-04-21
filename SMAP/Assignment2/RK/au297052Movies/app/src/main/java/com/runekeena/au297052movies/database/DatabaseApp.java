package com.runekeena.au297052movies.database;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.persistence.room.Room;
import android.os.Build;

public class DatabaseApp extends Application {
    // Variables
    public MovieDatabase database;

    //Constants
    public static final String CHANNEL_ID = "notify_channel";

    // Reference - Database - L5: Persistence

    public MovieDatabase getDatabase(){
        if (database == null){
            database = Room.databaseBuilder(getApplicationContext(), MovieDatabase.class, "moviedatabase").build();
        }
        return database;
    }

    // Reference - Notification channel - https://www.youtube.com/watch?v=FbpD5RZtbCc

    @Override
    public void onCreate() {
        super.onCreate();
        setupNotificationChannel();
    }

    private void setupNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Movie Notification Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

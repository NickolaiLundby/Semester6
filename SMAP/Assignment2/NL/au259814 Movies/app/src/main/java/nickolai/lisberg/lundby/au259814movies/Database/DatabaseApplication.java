package nickolai.lisberg.lundby.au259814movies.Database;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.persistence.room.Room;
import android.os.Build;

import nickolai.lisberg.lundby.au259814movies.Models.Constants;

// Reference to the notification channel:
// https://www.youtube.com/watch?v=FbpD5RZtbCc

public class DatabaseApplication extends Application {
    public MovieDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    public MovieDatabase GetDatabase(){
        if (database == null){
            database = Room.databaseBuilder(getApplicationContext(), MovieDatabase.class, "movie-database")
                    .build();
        }
        return database;
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    Constants.CHANNEL_ID,
                    Constants.CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}

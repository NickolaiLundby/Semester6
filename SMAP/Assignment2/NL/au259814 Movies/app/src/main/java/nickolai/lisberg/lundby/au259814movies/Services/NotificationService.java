package nickolai.lisberg.lundby.au259814movies.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nickolai.lisberg.lundby.au259814movies.Activities.OverviewActivity;
import nickolai.lisberg.lundby.au259814movies.Database.DatabaseApplication;
import nickolai.lisberg.lundby.au259814movies.Database.MovieDatabase;
import nickolai.lisberg.lundby.au259814movies.Models.Constants;
import nickolai.lisberg.lundby.au259814movies.Models.Movie;
import nickolai.lisberg.lundby.au259814movies.R;
import nickolai.lisberg.lundby.au259814movies.Utilities.MovieHelperClass;

public class NotificationService extends Service {
    Movie m;
    MovieDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        RandomMovie();
    }

    private void NotificationLogic() {
        NotificationManager notificationManager
                = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
            mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        if(m != null){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Constants.CHANNEL_ID)
                    .setSmallIcon(MovieHelperClass.GetPosterId(m.getGenres()))
                    .setContentTitle("Movie suggestion: "  + m.getTitle())
                    .setContentText("Watch it now!");

            Intent resultIntent = new Intent(this, OverviewActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(OverviewActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(resultPendingIntent);

            notificationManager.notify(Constants.NOTIFICATION_ID, builder.build());

            Log.d(Constants.DEBUG_NOTIFICATION_TAG, Constants.DEBUG_NOTIFICATION_CREATED);
        }
        else{
            Log.d(Constants.DEBUG_NOTIFICATION_TAG, Constants.DEBUG_NOTIFICATION_NOT_CREATED);
        }
    }

    private void RandomMovie(){
        new RandomMovieTask().execute();
    }

    private class RandomMovieTask extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... strings) {
            DatabaseApplication dba = (DatabaseApplication) getApplicationContext();
            db = dba.GetDatabase();
            ArrayList<Movie> list = new ArrayList<>();
            for(Movie m : db.movieDao().getAll())
            {
                if(!m.isWatched())
                    list.add(m);
            }

            if(list.isEmpty())
                m = null;
            else{
                Random rand = new Random();
                m = list.get(rand.nextInt(list.size()));
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            NotificationLogic();
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

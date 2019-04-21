package com.runekeena.au297052movies.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

public class MovieService extends Service {

    public class LocalBinder extends Binder{
        public MovieService getService(){
            return MovieService.this;
        }
    }

    // Variables
    IBinder iBinder = new LocalBinder();
    MovieDatabase movieDatabase;
    RequestQueue requestQueue;
    ArrayList<Movie> movieList;
    Movie currentMovie;
    Movie currentUWMovie;
    Movie movieToSave = null;
    boolean running=false;

    // Getters and setters
    public Movie getCurrentUWMovie() {
        return currentUWMovie;
    }
    public void setCurrentUWMovie(Movie currentUWMovie) {
        this.currentUWMovie = currentUWMovie;
    }
    public Movie getCurrentMovie() {
        return currentMovie;
    }
    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }

    //Constants
    private static final int NOTIFY_ID = 1;
    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";
    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";

    /// Notifications / Foreground service

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Reference - Foreground Service - https://www.dev2qa.com/android-foreground-service-example/

        if(intent != null)
        {
            String action = intent.getAction();

            switch (action)
            {
                case ACTION_START_FOREGROUND_SERVICE:
                    if(!running){
                        Log.d("Notify Service", "Foreground service is started.");
                        new UWDBTask().execute("Get Unwatched");
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

    private void startForegroundService(String title) {

        // Reference - Repeated Task - https://guides.codepath.com/android/Repeating-Periodic-Tasks

        running = true;
        startForeground(NOTIFY_ID, getNotification(title));
        final Handler handler = new Handler();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(running){
                    new UWDBTask().execute("Get Unwatched");
                    handler.postDelayed(this, 120000);
                }

            }
        },  120000);
    }

    private void stopForegroundService() {

        // Reference - Foreground Service - https://www.dev2qa.com/android-foreground-service-example/

        Log.d("Notify Service", "Stop foreground service.");
        stopForeground(true);
        stopSelf();
    }

    private Notification getNotification(String title) {

        // Reference - Foreground Service - https://www.dev2qa.com/android-foreground-service-example/

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, DatabaseApp.CHANNEL_ID);

        if (title != null) {
            Intent detailsIntent = new Intent(this, DetailsActivity.class);
            detailsIntent.setAction(DetailsActivity.ACTION_UNWATCHED);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, detailsIntent, 0);

                    builder.setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(getText(R.string.have_not_watched))
                    .setContentText(title)
                    .setOnlyAlertOnce(false)
                    .setOngoing(true)
                    .setContentIntent(pendingIntent);
        } else {
                    builder.setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(getText(R.string.watched_all))
                    .setOnlyAlertOnce(false)
                    .setOngoing(true);
        }

        Intent stopIntent = new Intent(this, MovieService.class);
        stopIntent.setAction(ACTION_STOP_FOREGROUND_SERVICE);
        PendingIntent pendingStopIntent = PendingIntent.getService(this, 0, stopIntent, 0);
        NotificationCompat.Action prevAction = new NotificationCompat.Action(android.R.drawable.ic_media_pause, getResources().getString(R.string.stop_notifications), pendingStopIntent);
        builder.addAction(prevAction);

        return builder.build();
    }

    private void updateNotification(String title) {

        if(!running){
            startForegroundService(title);
        } else {

            Notification notification = getNotification(title);

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(NOTIFY_ID, notification);
        }
    }

    // Async task for getting Unwatched Movies from Database
    private class UWDBTask extends AsyncTask<String, Void, String> {

        // Reference  - Async Tasks - L6: Services and Asynch Processing

        Movie randomUWMovie;

        @Override
        protected String doInBackground(String... params) {
            Log.d("Database", params[0]);
            DatabaseApp dba = (DatabaseApp) getApplicationContext();
            MovieDatabase mdb = dba.getDatabase();

            List<Movie> uwList = mdb.movieDao().getAllUnwatched();

            if (uwList.isEmpty()){
                return null;
            } else {

            Random rand = new Random();
            randomUWMovie = uwList.get(rand.nextInt(uwList.size()));

            return randomUWMovie.getTitle();
            }
        }

        @Override
        protected void onPostExecute(String p) {
            super.onPostExecute(p);
            updateNotification(p);
            setCurrentUWMovie(randomUWMovie);
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        InitDatabaseNotifications();
        return iBinder;
    }

    public void getMovieOnline(String title){
        String url = MovieHelper.UrlBuilder(title);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("API success responds", response);
                        Movie m = new Movie(response);
                        movieToSave = m;
                        new DBTasks().execute("add", m.getTitle());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("API error responds", error.toString());
            }
        });

        requestQueue.add(stringRequest);
    }

    private void InitDatabaseNotifications(){
        if (movieDatabase == null){
            DatabaseApp dba = (DatabaseApp) getApplicationContext();
            movieDatabase = dba.getDatabase();
        }
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(this);
        }

        new DBTasks().execute("init");
        Intent startIntent = new Intent(this, MovieService.class);
        startIntent.setAction(MovieService.ACTION_START_FOREGROUND_SERVICE);
        startService(startIntent);
    }

    // Database methods

    public void deleteMovie(){
        new DBTasks().execute("del");
    }

    public void addMovie(String title){
        new DBTasks().execute("add", title);
    }

    public void updateMovie(){
        new DBTasks().execute("update");
    }

    // AsyncTask for database operations
    private class DBTasks extends AsyncTask<String, Void, String> {

        // Reference  - Async Tasks - L6: Services and Asynch Processing

        ArrayList<Movie> mList;

        @Override
        protected String doInBackground(String... params) {
            Log.d("Database", params[0]);
            String p = params[0];
            switch (p) {
                case "init":
                    mList = new ArrayList<>(movieDatabase.movieDao().getAll());
                    if (mList.isEmpty()) {
                        InputStream inputStream = getResources().openRawResource(R.raw.movielist);
                        MovieHelper mh = new MovieHelper();
                        movieList = mh.readMovieCSVData(inputStream);
                        for (Movie m : movieList)
                            movieDatabase.movieDao().insertMovie(m);

                        mList = new ArrayList<>(movieDatabase.movieDao().getAll());
                    }
                    break;
                case "del":
                    movieDatabase.movieDao().delete(currentMovie);
                    mList = new ArrayList<>(movieDatabase.movieDao().getAll());
                    break;
                case "add":
                    Log.d("Database", params[1]);
                    if(movieDatabase.movieDao().findByTitle(params[1]) != null){
                        Log.d("Database", params[1]+" found on db");
                    } else if(movieToSave != null) {
                        movieDatabase.movieDao().insertMovie(movieToSave);
                        movieToSave = null;
                    } else {
                        getMovieOnline(params[1]);
                    }
                    mList = new ArrayList<>(movieDatabase.movieDao().getAll());
                    break;
                case "update":
                    movieDatabase.movieDao().update(currentMovie);
                    mList = new ArrayList<>(movieDatabase.movieDao().getAll());
                    break;
                default:
                    break;
            }

            return p;
        }

        @Override
        protected void onPostExecute(String p) {
            super.onPostExecute(p);
            databaseUpdated(mList);
        }
    }

    private void databaseUpdated(ArrayList<Movie> movies){
        movieList = movies; //new ArrayList<>(movieDatabase.movieDao().getAll());
        broadcast();
    }

    public ArrayList<Movie> getMovieList(){
        return movieList;
    }

    private void broadcast(){
        try{
            sendBroadcast(new Intent().setAction(OverviewActivity.DATABASE_UPDATED));
            Log.d("Broadcast", "Sent from MovieService");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

package com.runekeena.au297052movies.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.runekeena.au297052movies.R;
import com.runekeena.au297052movies.activities.Overview.OverviewActivity;
import com.runekeena.au297052movies.database.DatabaseApp;
import com.runekeena.au297052movies.database.MovieDatabase;
import com.runekeena.au297052movies.model.Movie;
import com.runekeena.au297052movies.utils.MovieHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    Movie movieToSave = null;
    public Movie getCurrentMovie() {
        return currentMovie;
    }

    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }

    @Override
    public IBinder onBind(Intent intent) {
        InitDatabase();
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


    private void InitDatabase(){
        if (movieDatabase == null){
            DatabaseApp dba = (DatabaseApp) getApplicationContext();
            movieDatabase = dba.getDatabase();
        }
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(this);
        }
        new DBTasks().execute("init");
    }

    public void deleteMovie(){
        new DBTasks().execute("del");
    }

    public void addMovie(String title){
        new DBTasks().execute("add", title);
    }

    public void updateMovie(){
        new DBTasks().execute("update");
    }

    private class DBTasks extends AsyncTask<String, Void, String> {

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

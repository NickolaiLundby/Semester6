package nickolai.lisberg.lundby.au259814movies.Services;

import android.app.AlarmManager;
import android.app.Notification;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import nickolai.lisberg.lundby.au259814movies.Activities.OverviewActivity;
import nickolai.lisberg.lundby.au259814movies.Database.DatabaseApplication;
import nickolai.lisberg.lundby.au259814movies.Database.MovieDatabase;
import nickolai.lisberg.lundby.au259814movies.Models.Constants;
import nickolai.lisberg.lundby.au259814movies.Models.Movie;
import nickolai.lisberg.lundby.au259814movies.R;
import nickolai.lisberg.lundby.au259814movies.Utilities.CSVReader;
import nickolai.lisberg.lundby.au259814movies.Utilities.MovieHelperClass;
import nickolai.lisberg.lundby.au259814movies.Utilities.NotificationReceiver;

public class MovieService extends Service {

    // Variables
    ArrayList<Movie> arrayOfMovies;
    MovieDatabase db;
    IBinder mBinder = new LocalBinder();
    RequestQueue requestQueue;
    Movie currentMovie;
    Movie updateMovie;
    Movie apiMovie;

    @Override
    public void onCreate() {
        InitializeDatabase();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationLogic();
        startAlarm();
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        if(requestQueue == null)
            requestQueue = Volley.newRequestQueue(this);

        return mBinder;
    }

    public class LocalBinder extends Binder {
        public MovieService getServiceInstance() {
            return MovieService.this;
        }
    }

    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }

    public Movie getCurrentMovie() {
        return currentMovie;
    }

    public ArrayList<Movie> getArrayOfMovies() {
        return arrayOfMovies;
    }

    public void setArrayOfMovies(ArrayList<Movie> arrayOfMovies) {
        this.arrayOfMovies = arrayOfMovies;
    }

    private Movie randomMovie(){
        Random rand = new Random();
        return arrayOfMovies.get(rand.nextInt(arrayOfMovies.size()));
    }

    /// ************************ ///
    /// DATABASE FUNCTIONS BELOW ///
    /// ************************ ///
    public void InitializeDatabase(){
        new MyTask().execute(Constants.ASYNC_INITIALIZE);
    }

    private void InitializeDatabaseImpl() {
        DatabaseApplication dba = (DatabaseApplication) getApplicationContext();
        db = dba.GetDatabase();
        setArrayOfMovies(new ArrayList<>(db.movieDao().getAll()));
        if(getArrayOfMovies().isEmpty())
        {
            InputStream inputStream = getResources().openRawResource(R.raw.movielist);
            CSVReader csvReader = new CSVReader(inputStream);
            setArrayOfMovies(csvReader.read());
            for(Movie m : getArrayOfMovies())
                db.movieDao().insertMovie(m);
        }
    }

    public void AddMovie(String movieTitle) {
        new MyTask().execute(Constants.ASYNC_ADD, movieTitle);
    }

    private void AddMovieImpl(String movieTitle) {
        String url = MovieHelperClass.UrlBuilder(movieTitle);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(Constants.DEBUG_API_TAG, Constants.DEBUG_API_RAW + response);
                        Movie m = new Movie(response);
                        if(m.getTitle().isEmpty())
                        {
                            Log.d(Constants.DEBUG_API_TAG, Constants.DEBUG_API_NULL);
                        }
                        else {
                            Log.d(Constants.DEBUG_API_TAG, Constants.DEBUG_API_TITLE + m.getTitle());
                            apiMovie = m;
                            ApiCallback();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Constants.DEBUG_EXCEPTION_TAG, error.networkResponse.toString());
            }
        });
        requestQueue.add(stringRequest);
    }

    public void DeleteMovie() {
        new MyTask().execute(Constants.ASYNC_DELETE);
    }

    private void DeleteMovieImpl() {
        db.movieDao().delete(currentMovie);
        setArrayOfMovies(new ArrayList<>(db.movieDao().getAll()));
        Log.d(Constants.DEBUG_DATABASE_TAG, Constants.DEBUG_DATABASE_DELETED + currentMovie.getTitle());
    }

    public void UpdateMovie(Movie movie) {
        updateMovie = movie;
        new MyTask().execute(Constants.ASYNC_UPDATE);
    }

    public void UpdateMovieImpl(Movie movie) {
        db.movieDao().update(movie);
        setArrayOfMovies(new ArrayList<>(db.movieDao().getAll()));
        Log.d(Constants.DEBUG_DATABASE_TAG, Constants.DEBUG_DATABASE_UPDATED + currentMovie.getTitle());
    }

    private void ApiCallback(){
        new MyTask().execute(Constants.ASYNC_CALLBACK);
    }

    private void ApiCallbackImpl() {
        db.movieDao().insertMovie(apiMovie);
        setArrayOfMovies(new ArrayList<>(db.movieDao().getAll()));
        Log.d(Constants.DEBUG_DATABASE_TAG, Constants.DEBUG_DATABASE_ADDED + apiMovie.getTitle());
    }

    private class MyTask extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... params) {
            String myParam = params[0];

            switch(myParam) {
                case Constants.ASYNC_INITIALIZE:
                    InitializeDatabaseImpl();
                    break;
                case Constants.ASYNC_DELETE:
                    DeleteMovieImpl();
                    break;
                case Constants.ASYNC_ADD:
                    if(db.movieDao().findByTitle(params[1]) != null)
                        Log.d(Constants.DEBUG_DATABASE_TAG, Constants.DEBUG_DATABASE_NOT_ADDED + params[1]);
                    else
                        AddMovieImpl(params[1]);
                    break;
                case Constants.ASYNC_UPDATE:
                    UpdateMovieImpl(updateMovie);
                    break;
                case Constants.ASYNC_CALLBACK:
                    ApiCallbackImpl();
                    break;
                default:
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            sendMyBroadCast();
        }
    }

    private void sendMyBroadCast() {
        try
        {
            sendBroadcast(new Intent().setAction(Constants.BROADCAST_DATABASE_UPDATED));
            Log.d(Constants.DEBUG_BROADCAST_TAG, Constants.DEBUG_BROADCAST_SENT);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void startAlarm() {
        new AsyncAlarmTask().execute();
    }

    private class AsyncAlarmTask extends AsyncTask<Void, Void, Void> {
        Movie randomMovie;
        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<Movie> allMovies = new ArrayList<>(db.movieDao().getAll());
            Random rand = new Random();
            randomMovie = allMovies.get(rand.nextInt(allMovies.size()));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MovieService.this, NotificationReceiver.class);
            intent.putExtra("Title", randomMovie.getTitle());
            intent.putExtra("Genre", randomMovie.getGenres());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MovieService.this, 1, intent, 0);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), 20000, pendingIntent);
            super.onPostExecute(aVoid);
        }
    }

    private void NotificationLogic(){
        new NotificationAsyncTask().execute();
    }

    private void NotificationLogicImpl(Movie m){
        Intent notificationIntent = new Intent(this, OverviewActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        if(!m.isWatched()){
            Notification notification = new NotificationCompat.Builder(this, Constants.CHANNEL_ID)
                    .setContentTitle(getResources().getString(R.string.notification_new_suggestion))
                    .setContentText(getResources().getString(R.string.titel) + m.getTitle())
                    .setSmallIcon(MovieHelperClass.GetPosterId(m.getGenres()))
                    .setContentIntent(pendingIntent)
                    .build();

            startForeground(1, notification);
        }
        else{
            Notification notification = new NotificationCompat.Builder(this, Constants.CHANNEL_ID)
                    .setContentTitle(getResources().getString(R.string.notification_rewatch_suggestion))
                    .setContentText(getResources().getString(R.string.titel) + m.getTitle())
                    .setSmallIcon(MovieHelperClass.GetPosterId(m.getGenres()))
                    .setContentIntent(pendingIntent)
                    .build();

            startForeground(1, notification);
        }
        Log.d(Constants.DEBUG_NOTIFICATION_TAG, Constants.DEBUG_NOTIFICATION_CREATED);
    }

    private class NotificationAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            NotificationLogicImpl(randomMovie());
            return null;
        }
    }
}

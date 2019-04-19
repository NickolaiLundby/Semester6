package nickolai.lisberg.lundby.au259814movies.Services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
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
import java.util.Random;

import nickolai.lisberg.lundby.au259814movies.Activities.OverviewActivity;
import nickolai.lisberg.lundby.au259814movies.Database.DatabaseApplication;
import nickolai.lisberg.lundby.au259814movies.Database.MovieDatabase;
import nickolai.lisberg.lundby.au259814movies.Models.Constants;
import nickolai.lisberg.lundby.au259814movies.Models.Movie;
import nickolai.lisberg.lundby.au259814movies.R;
import nickolai.lisberg.lundby.au259814movies.Utilities.CSVReader;
import nickolai.lisberg.lundby.au259814movies.Utilities.MovieHelperClass;

public class MovieService extends Service {

    // Variables
    ArrayList<Movie> arrayOfMovies;
    ArrayList<Movie> arrayOfUnwatchedMovies;
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
        Intent notificationIntent = new Intent(this, OverviewActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Notification notification = new NotificationCompat.Builder(this, Constants.CHANNEL_ID)
                .setContentTitle("Movie suggestion!")
                .setContentText("Title: " + "Title goes here")
                .setSmallIcon(MovieHelperClass.GetPosterId("Default"))
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

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

    public void setArrayOfUnwatchedMovies(ArrayList<Movie> arrayOfUnwatchedMovies) {
        this.arrayOfUnwatchedMovies = arrayOfUnwatchedMovies;
    }

    private void SyncUnwatchedMovies(){
        setArrayOfUnwatchedMovies(new ArrayList<Movie>());
        for(Movie m : getArrayOfMovies())
            if(!m.isWatched())
                arrayOfUnwatchedMovies.add(m);
    }

    private Movie randomUnwatchedMovie(){
        Random rand = new Random();
        return arrayOfUnwatchedMovies.get(rand.nextInt(arrayOfUnwatchedMovies.size()));
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

        SyncUnwatchedMovies();
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
        SyncUnwatchedMovies();
        Log.d(Constants.DEBUG_DATABASE_TAG, Constants.DEBUG_DATABASE_DELETED + currentMovie.getTitle());
    }

    public void UpdateMovie(Movie movie) {
        updateMovie = movie;
        new MyTask().execute(Constants.ASYNC_UPDATE);
    }

    public void UpdateMovieImpl(Movie movie) {
        db.movieDao().update(movie);
        setArrayOfMovies(new ArrayList<>(db.movieDao().getAll()));
        SyncUnwatchedMovies();
        Log.d(Constants.DEBUG_DATABASE_TAG, Constants.DEBUG_DATABASE_UPDATED + currentMovie.getTitle());
    }

    private void ApiCallback(){
        new MyTask().execute(Constants.ASYNC_CALLBACK);
    }

    private void ApiCallbackImpl() {
        db.movieDao().insertMovie(apiMovie);
        setArrayOfMovies(new ArrayList<>(db.movieDao().getAll()));
        SyncUnwatchedMovies();
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
}

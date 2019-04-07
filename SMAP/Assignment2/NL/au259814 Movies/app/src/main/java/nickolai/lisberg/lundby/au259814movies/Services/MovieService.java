package nickolai.lisberg.lundby.au259814movies.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;

import nickolai.lisberg.lundby.au259814movies.Activities.OverviewActivity;
import nickolai.lisberg.lundby.au259814movies.Database.DatabaseApplication;
import nickolai.lisberg.lundby.au259814movies.Database.MovieDatabase;
import nickolai.lisberg.lundby.au259814movies.Models.APIModels.MovieAPI;
import nickolai.lisberg.lundby.au259814movies.Models.Movie;
import nickolai.lisberg.lundby.au259814movies.Models.ServiceResponse;
import nickolai.lisberg.lundby.au259814movies.R;
import nickolai.lisberg.lundby.au259814movies.Utilities.CSVReader;
import nickolai.lisberg.lundby.au259814movies.Utilities.MovieHelperClass;

public class MovieService extends Service {

    // Variables
    ArrayList<Movie> arrayOfMovies;
    MovieDatabase db;
    Movie movieResult;
    IBinder mBinder = new LocalBinder();
    RequestQueue requestQueue;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        InitializeDatabase();

        requestQueue = Volley.newRequestQueue(this);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    private void SendABroadCast(Movie movie)
    {
        try
        {
            Intent broadCastIntent = new Intent();
            broadCastIntent.setAction(OverviewActivity.DATABASE_BROADCAST);
            broadCastIntent.putExtra(OverviewActivity.MOVIE_BROADCAST, movie);

            sendBroadcast(broadCastIntent);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public class LocalBinder extends Binder {
        public MovieService getServiceInstance() {
            return MovieService.this;
        }
    }

    public void InitializeDatabase()
    {
        DatabaseApplication dba = (DatabaseApplication) getApplicationContext();
        db = dba.GetDatabase();
        arrayOfMovies = new ArrayList<>(db.movieDao().getAll());
        if(arrayOfMovies.isEmpty())
        {
            InputStream inputStream = getResources().openRawResource(R.raw.movielist);
            CSVReader csvReader = new CSVReader(inputStream);
            arrayOfMovies = csvReader.read();
            for(Movie m : arrayOfMovies)
                db.movieDao().insertMovie(m);
        }
    }

    public void AddToDatabase(Movie movie)
    {
        String url = MovieHelperClass.UrlBuilder(movie.getTitle());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        MovieAPI movieAPI = gson.fromJson(response, MovieAPI.class);
                        if(movieAPI.getTitle().isEmpty())
                        {
                            // Handle empty response from API
                        }
                        if (db.movieDao().findByTitle(movieAPI.getTitle()) != null)
                        {
                            // Handle database already containing movie
                        }
                        else
                        {
                            // Add the movie
                            movieResult = MovieHelperClass.MovieFromMovieAPI(movieAPI);
                            db.movieDao().insertMovie(movieResult);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new UnsupportedOperationException();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void DeleteFromDatabase(Movie movie)
    {
        db.movieDao().delete(movie);
    }

    public void UpdateMovie(Movie movie)
    {
        db.movieDao().update(movie);
    }

    public void UpdateAllMovies()
    {
        for(Movie movie: db.movieDao().getAll()){
            String url = MovieHelperClass.UrlBuilder(movie.getTitle());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson = new Gson();
                            MovieAPI movieAPI = gson.fromJson(response, MovieAPI.class);
                            if(movieAPI.getTitle().isEmpty())
                            {
                                // Handle empty response from API
                            }
                            if (db.movieDao().findByTitle(movieAPI.getTitle()) != null)
                            {
                                // Handle database already containing movie
                                movieResult = MovieHelperClass.MovieFromMovieAndMovieAPI(movieAPI, db.movieDao().findByTitle(movieAPI.getTitle()));
                                db.movieDao().update(movieResult);
                            }
                            else
                            {
                                // Add the movie
                                movieResult = MovieHelperClass.MovieFromMovieAPI(movieAPI);
                                db.movieDao().insertMovie(movieResult);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    throw new UnsupportedOperationException();
                }
            });
            requestQueue.add(stringRequest);
        }
    }
}

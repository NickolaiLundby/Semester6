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
import nickolai.lisberg.lundby.au259814movies.R;
import nickolai.lisberg.lundby.au259814movies.Utilities.CSVReader;
import nickolai.lisberg.lundby.au259814movies.Utilities.MovieHelperClass;

public class MovieService extends Service {

    // Variables
    ArrayList<Movie> arrayOfMovies;
    MovieDatabase db;
    Movie movieResult;
    IBinder mBinder = new LocalBinder();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
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

    private void GetMovieByTitle(String title)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = MovieHelperClass.UrlBuilder(title);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // The response is received as a JSON, formatted as seen on
                        // http://omdbapi.com. We will use MovieAPI class to parse it.
                        // As we don't need the entire data structure, we convert it to
                        // our simpler Movie object using our helper function.
                        Gson gson = new Gson();
                        MovieAPI movieAPI = gson.fromJson(response, MovieAPI.class);
                        if(db.movieDao().findByTitle(movieAPI.getTitle()) != null)
                        {
                            // If the movie is already stored on the database, eg. with
                            // user comments and rating, we wan't to keep this user info.
                            movieResult = MovieHelperClass.MovieFromMovieAndMovieAPI(movieAPI, db.movieDao().findByTitle(movieAPI.getTitle()));
                        }
                        else
                        {
                            // If the movie isn't already stored, we created a new object,
                            // entirely based on the API object, with empty user comment and rating.
                            movieResult = MovieHelperClass.MovieFromMovieAPI(movieAPI);
                        }
                        // We then need to update the database with this object.
                        db.movieDao().update(movieResult);
                        // Let all activities listening know that the database was
                        // updated, and specifically that movie object.
                        SendABroadCast(movieResult);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                throw new UnsupportedOperationException("Not yet implemented");
            }
        });

        queue.add(stringRequest);
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
        public MovieService getServerInstance() {
            return MovieService.this;
        }
    }
}

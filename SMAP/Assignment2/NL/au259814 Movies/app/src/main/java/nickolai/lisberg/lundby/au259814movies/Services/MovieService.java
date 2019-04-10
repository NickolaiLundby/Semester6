package nickolai.lisberg.lundby.au259814movies.Services;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

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
    Movie currentMovie;

    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        InitializeDatabase();

        requestQueue = Volley.newRequestQueue(this);

        return mBinder;
    }

    public class LocalBinder extends Binder {
        public MovieService getServiceInstance() {
            return MovieService.this;
        }
    }


    /// ************************ ///
    /// DATABASE FUNCTIONS BELOW ///
    /// ************************ ///
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

    public ServiceResponse AddToDatabase(String movieTitle)
    {
        if(db.movieDao().findByTitle(movieTitle) != null){
            return new ServiceResponse(false, "Movie already in database");
        }
        else{
            String url = MovieHelperClass.UrlBuilder(movieTitle);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("APIResponse", "Response: " + response);
                            Movie m = new Movie(response);
                            if(m.getTitle() == null)
                            {
                                Log.d("APIResponse", "Null object");
                            }
                            else {
                                Log.d("APIResponse", "Movie: " + m.getTitle());
                                db.movieDao().insertMovie(m);
                                sendMyBroadCast();
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
        return new ServiceResponse(true, "Movie added");
    }

    public void DeleteMovie()
    {
        db.movieDao().delete(currentMovie);
        Log.d("DatabaseOperation", "Movie deleted: " + currentMovie.getTitle());
    }

    public void UpdateMovie(Movie movie)
    {
        db.movieDao().update(movie);
    }

    public void UpdateAllMovies() {
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

    public Movie GetCurrentMovie() {
        try{
            return db.movieDao().findByTitle(currentMovie.getTitle());
        } catch(Exception e){
            throw new UnsupportedOperationException();
        }
    }

    public ArrayList<Movie> GetAllMovies() {
        return new ArrayList<>(db.movieDao().getAll());
    }

    private void sendMyBroadCast()
    {
        try
        {
            sendBroadcast(new Intent().setAction(OverviewActivity.BROADCAST_DATABASE_UPDATED));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

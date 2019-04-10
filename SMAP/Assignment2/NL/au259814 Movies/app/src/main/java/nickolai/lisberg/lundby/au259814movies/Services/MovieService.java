package nickolai.lisberg.lundby.au259814movies.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import nickolai.lisberg.lundby.au259814movies.Activities.OverviewActivity;
import nickolai.lisberg.lundby.au259814movies.Database.DatabaseApplication;
import nickolai.lisberg.lundby.au259814movies.Database.MovieDatabase;
import nickolai.lisberg.lundby.au259814movies.Models.Movie;
import nickolai.lisberg.lundby.au259814movies.Models.ServiceResponse;
import nickolai.lisberg.lundby.au259814movies.R;
import nickolai.lisberg.lundby.au259814movies.Utilities.CSVReader;
import nickolai.lisberg.lundby.au259814movies.Utilities.MovieHelperClass;

public class MovieService extends Service {

    // Variables
    ArrayList<Movie> arrayOfMovies;
    MovieDatabase db;
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
            Log.d("Database", "Not added: " + movieTitle);
            return new ServiceResponse(false, getResources().getString(R.string.service_response_already));
        }
        else{
            String url = MovieHelperClass.UrlBuilder(movieTitle);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("APIResponse", "Raw response: " + response);
                            Movie m = new Movie(response);
                            if(m.getTitle().isEmpty())
                            {
                                Log.d("APIResponse", "Null object");
                            }
                            else {
                                Log.d("APIResponse", "Movie title: " + m.getTitle());
                                db.movieDao().insertMovie(m);
                                Log.d("Database", "Added: " + m.getTitle());
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
        return new ServiceResponse(true, getResources().getString(R.string.service_response_added));
    }

    public void DeleteMovie()
    {
        db.movieDao().delete(currentMovie);
        Log.d("Database", "Deleted: " + currentMovie.getTitle());
    }

    public void UpdateMovie(Movie movie)
    {
        db.movieDao().update(movie);
        Log.d("Database", "Updated: " + currentMovie.getTitle());
    }

    public void UpdateAllMovies() {
        ArrayList<Movie> movieArrayList = new ArrayList<>(db.movieDao().getAll());
        for (int i = 0; i < movieArrayList.size(); i++) {
            String url = MovieHelperClass.UrlBuilder(movieArrayList.get(i).getTitle());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Movie m = new Movie(response);
                            if(m.getTitle().isEmpty())
                            {
                                Log.d("APIResponse", "Null object");
                            }
                            else
                            {
                                Log.d("APIResponse", "Movie title: " + m.getTitle());
                                if (db.movieDao().findByTitle(m.getTitle()) != null)
                                {
                                    db.movieDao().update(MovieHelperClass.UpdatedMovie(db.movieDao().findByTitle(m.getTitle()), m));
                                    Log.d("Database", "Updated: " + m.getTitle());
                                }
                                else
                                {
                                    db.movieDao().insertMovie(m);
                                    Log.d("Database", "Added: " + m.getTitle());
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    throw new UnsupportedOperationException();
                }
            });
            requestQueue.add(stringRequest);
            if(i == movieArrayList.size())
                sendMyBroadCast();
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
            Log.d("Broadcast", "Sending broadcast from MovieService");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

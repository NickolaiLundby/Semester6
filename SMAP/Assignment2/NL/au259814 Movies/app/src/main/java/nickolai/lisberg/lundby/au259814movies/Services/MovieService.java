package nickolai.lisberg.lundby.au259814movies.Services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
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
import nickolai.lisberg.lundby.au259814movies.Models.Constants;
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
    Movie updateMovie;

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        InitializeDatabase();

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

    /// ************************ ///
    /// DATABASE FUNCTIONS BELOW ///
    /// ************************ ///
    public void InitializeDatabase(){
        new MyTask().execute("Initialize");
    }

    private void InitializeDatabaseImpl()
    {
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

    public void AddToDatabase(String movieTitle) {
        new MyTask().execute("Add", movieTitle);
    }

    private void apiCallback(Movie movie) {
        new MyTask().execute("Callback");
    }

    private void AddToDatabaseImpl(String movieTitle)
    {
        if(db.movieDao().findByTitle(movieTitle) != null){
            Log.d(Constants.DEBUG_DATABASE_TAG, Constants.DEBUG_DATABASE_NOT_ADDED + movieTitle);
        }
        else{
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
                                AddMovie(m);
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
    }

    private void AddMovie(Movie movie)
    {
        db.movieDao().insertMovie(movie);
        setArrayOfMovies(new ArrayList<>(db.movieDao().getAll()));
        Log.d(Constants.DEBUG_DATABASE_TAG, Constants.DEBUG_DATABASE_ADDED + movie.getTitle());
    }

    public void DeleteMovie() {
        new MyTask().execute("Delete");
    }

    private void DeleteMovieImpl()
    {
        db.movieDao().delete(currentMovie);
        setArrayOfMovies(new ArrayList<>(db.movieDao().getAll()));
        Log.d(Constants.DEBUG_DATABASE_TAG, Constants.DEBUG_DATABASE_DELETED + currentMovie.getTitle());
    }

    public void UpdateMovie(Movie movie) {
        updateMovie = movie;
        new MyTask().execute("Update");
    }

    public void UpdateMovieImpl(Movie movie)
    {
        db.movieDao().update(movie);
        setArrayOfMovies(new ArrayList<>(db.movieDao().getAll()));
        Log.d(Constants.DEBUG_DATABASE_TAG, Constants.DEBUG_DATABASE_UPDATED + currentMovie.getTitle());
    }

    public void UpdateAllMovies(){
        new MyTask().execute("UpdateAll");
    }

    private void UpdateAllMoviesImpl() {
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
                                Log.d(Constants.DEBUG_API_TAG, Constants.DEBUG_API_NULL);
                            }
                            else
                            {
                                Log.d(Constants.DEBUG_API_TAG, Constants.DEBUG_API_TITLE + m.getTitle());
                                if (db.movieDao().findByTitle(m.getTitle()) != null)
                                {
                                    UpdateMovie(MovieHelperClass.UpdatedMovie(db.movieDao().findByTitle(m.getTitle()), m));
                                }
                                else
                                {
                                    AddMovie(m);
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

    private class MyTask extends AsyncTask<String, Integer, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String myParam = params[0];

            switch(myParam) {
                case "Initialize":
                    InitializeDatabaseImpl();
                    break;
                case "Delete":
                    DeleteMovieImpl();
                    break;
                case "Add":
                    AddToDatabaseImpl(params[1]);
                    break;
                case "Update":
                    UpdateMovieImpl(updateMovie);
                    break;
                case "UpdateAll":
                    UpdateAllMoviesImpl();
                    break;
                case "Callback":
                    break;
                default:
                    break;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            sendMyBroadCast();
        }
    }

    private void sendMyBroadCast()
    {
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

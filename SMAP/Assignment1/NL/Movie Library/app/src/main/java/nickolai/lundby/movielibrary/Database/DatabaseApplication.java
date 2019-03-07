package nickolai.lundby.movielibrary.Database;

import android.app.Application;
import android.arch.persistence.room.Room;

public class DatabaseApplication extends Application {
    public MovieDatabase database;

    public MovieDatabase GetDatabase(){
        if (database == null){
            // Allow main thread query. This is obviously bad practice
            database = Room.databaseBuilder(getApplicationContext(), MovieDatabase.class, "movie-database").allowMainThreadQueries().build();
        }
        return database;
    }
}

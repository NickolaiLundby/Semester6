package nickolai.lisberg.lundby.au259814movies.Database;

import android.app.Application;
import android.arch.persistence.room.Room;

public class DatabaseApplication extends Application {
    public MovieDatabase database;

    public MovieDatabase GetDatabase(){
        if (database == null){
            database = Room.databaseBuilder(getApplicationContext(), MovieDatabase.class, "movie-database")
                    .build();
        }
        return database;
    }
}

package nickolai.lisberg.lundby.au259814movies.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import nickolai.lisberg.lundby.au259814movies.Models.Movie;
import nickolai.lisberg.lundby.au259814movies.Models.MovieDao;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}

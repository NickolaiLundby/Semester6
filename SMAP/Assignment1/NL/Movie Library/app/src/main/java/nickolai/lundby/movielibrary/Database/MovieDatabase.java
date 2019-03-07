package nickolai.lundby.movielibrary.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import nickolai.lundby.movielibrary.Models.Movie;
import nickolai.lundby.movielibrary.Models.MovieDao;
import nickolai.lundby.movielibrary.Utilities.ListStringConverter;

@Database(entities = {Movie.class}, version = 1)
@TypeConverters({ListStringConverter.class})
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}

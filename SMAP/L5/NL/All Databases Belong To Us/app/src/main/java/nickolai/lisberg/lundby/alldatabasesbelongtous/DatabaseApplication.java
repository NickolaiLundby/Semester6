package nickolai.lisberg.lundby.alldatabasesbelongtous;

import android.app.Application;
import android.arch.persistence.room.Room;

public class DatabaseApplication extends Application {
    public AppDatabase database;

    public AppDatabase GetDatabase(){
        if (database == null){
            // Allow main thread query. This is obviously bad practice
            database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "task-database").allowMainThreadQueries().build();
        }
        return database;
    }
}

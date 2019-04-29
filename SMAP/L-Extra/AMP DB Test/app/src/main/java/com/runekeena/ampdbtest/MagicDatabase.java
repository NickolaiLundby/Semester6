package com.runekeena.ampdbtest;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Collection.class, RefCard.class, Card.class}, version = 1)
public abstract class MagicDatabase extends RoomDatabase {

    private static MagicDatabase instance;

    public abstract MagicDao magicDao();

    public static synchronized MagicDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MagicDatabase.class, "magic_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private MagicDao magicDao;

        private PopulateDbAsyncTask(MagicDatabase database) {
            magicDao = database.magicDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            magicDao.insertCard(new Card("FakeCard1", 1.50));
            magicDao.insertCard(new Card("FakeCard2", 2.00));
            magicDao.insertCard(new Card("FakeCard3", 3.75));
            magicDao.insertCollection(new Collection("TestCollection1", "The first one..."));
            magicDao.insertCollection(new Collection("TestCollection2", "The second one..."));
            magicDao.insertCollection(new Collection("TestCollection3", "The third one..."));
            return null;
        }
    }
}

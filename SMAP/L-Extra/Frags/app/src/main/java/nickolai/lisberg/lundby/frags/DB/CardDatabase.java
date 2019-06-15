package nickolai.lisberg.lundby.frags.DB;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import nickolai.lisberg.lundby.frags.Card;
import nickolai.lisberg.lundby.frags.Collection;
import nickolai.lisberg.lundby.frags.MagicDao;

@Database(entities = {Card.class, Collection.class}, version = 1)
public abstract class CardDatabase extends RoomDatabase {
    private static CardDatabase instance;

    public abstract MagicDao magicDao();

    public static synchronized CardDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CardDatabase.class,
                    "magic_database")
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

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private MagicDao magicDao;

        private PopulateDbAsyncTask(CardDatabase db) {
            magicDao = db.magicDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Collection collection1 = new Collection("Grease Lightning", "It's shocking.");
            collection1.setCoId(123);
            magicDao.insertCollection(collection1);
            Collection collection2 = new Collection("Lightning", "It's not as shocking.");
            collection2.setCoId(321);
            magicDao.insertCollection(collection2);

            Card card1 = new Card("Lightning Bolt", "Alpha", "Lightning Bolt does 3 damage to one target.");
            card1.setCollectionId(collection1.getCoId());
            magicDao.insertCard(card1);
            Card card2 = new Card("Lightning Bolt", "Fourth Edition", "Lightning Bolt deals 3 damage to target creature or player.");
            card2.setCollectionId(collection1.getCoId());
            magicDao.insertCard(card2);
            Card card3 = new Card("Lightning Bolt", "Magic 2011", "Lightning Bolt deals 3 damage to target creature or player.");
            card3.setCollectionId(collection2.getCoId());
            magicDao.insertCard(card3);

            return null;
        }
    }
}

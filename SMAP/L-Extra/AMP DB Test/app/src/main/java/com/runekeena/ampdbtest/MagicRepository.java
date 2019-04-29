package com.runekeena.ampdbtest;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class MagicRepository {
    private MagicDao magicDao;

    private LiveData<List<Collection>> collections;

    public MagicRepository(Application application){
        MagicDatabase database = MagicDatabase.getInstance(application);
        magicDao = database.magicDao();
        collections = magicDao.getAllCollections();
    }


    // Collection operations
    public void insertCollection(Collection collection){
        new InsertCollectionAsyncTask(magicDao).execute(collection);

    }

    public void updateCollection(Collection collection){
        new UpdateCollectionAsyncTask(magicDao).execute(collection);
    }

    public void deleteCollection(Collection collection){
        new DeleteCollectionAsyncTask(magicDao).execute(collection);
    }

    public LiveData<List<Collection>> getAllCollections(){
        return collections;
    }

    // Card operations
    public void insertCard(Card card){
        new InsertCardAsyncTask(magicDao).execute(card);
    }

    public void updateCard(Card card){
        new UpdateCardAsyncTask(magicDao).execute(card);
    }

    /* How?
    public Card getCardByTitle(String title){
        new GetCardByTitleAsyncTask(magicDao).execute(title);
    }
    */

    // RefCard operations
    public void insertRefCard(RefCard refCard){
        //new InsertRefCardAsyncTask(magicDao).execute(refCard);
    }

    public void updateRefCard(RefCard refCard){
        //new UpdateRefCardAsyncTask(magicDao).execute(refCard);
    }

    /* How?
    public Card getRefCardsByCollectionId(int collectionId){
        new GetRefCardsByCollectionIdAsyncTask(magicDao).execute(collectionId);
    }
    */


    // Collection AsyncTasks
    private static class InsertCollectionAsyncTask extends AsyncTask<Collection, Void, Void>{
        private MagicDao magicDao;

        private InsertCollectionAsyncTask(MagicDao magicDao){
            this.magicDao = magicDao;
        }

        @Override
        protected Void doInBackground(Collection... collections) {
            magicDao.insertCollection(collections[0]);
            return null;
        }
    }

    private static class UpdateCollectionAsyncTask extends AsyncTask<Collection, Void, Void>{
        private MagicDao magicDao;

        private UpdateCollectionAsyncTask(MagicDao magicDao){
            this.magicDao = magicDao;
        }

        @Override
        protected Void doInBackground(Collection... collections) {
            magicDao.updateCollection(collections[0]);
            return null;
        }
    }

    private static class DeleteCollectionAsyncTask extends AsyncTask<Collection, Void, Void>{
        private MagicDao magicDao;

        private DeleteCollectionAsyncTask(MagicDao magicDao){
            this.magicDao = magicDao;
        }

        @Override
        protected Void doInBackground(Collection... collections) {
            magicDao.deleteCollection(collections[0]);
            return null;
        }
    }

    // Card AsyncTasks
    private static class InsertCardAsyncTask extends AsyncTask<Card, Void, Void>{
        private MagicDao magicDao;

        private InsertCardAsyncTask(MagicDao magicDao){
            this.magicDao = magicDao;
        }

        @Override
        protected Void doInBackground(Card... cards) {
            magicDao.insertCard(cards[0]);
            return null;
        }
    }

    private static class UpdateCardAsyncTask extends AsyncTask<Card, Void, Void>{
        private MagicDao magicDao;

        private UpdateCardAsyncTask(MagicDao magicDao){
            this.magicDao = magicDao;
        }

        @Override
        protected Void doInBackground(Card... cards) {
            magicDao.updateCard(cards[0]);
            return null;
        }
    }

    private static class GetCardByTitleAsyncTask extends AsyncTask<String, Void, Void>{
        private MagicDao magicDao;

        private GetCardByTitleAsyncTask(MagicDao magicDao){
            this.magicDao = magicDao;
        }

        @Override
        protected Void doInBackground(String... titles) {
            LiveData<Card> card =  magicDao.getCardByTitle(titles[0]);
            return null;
        }
    }

}

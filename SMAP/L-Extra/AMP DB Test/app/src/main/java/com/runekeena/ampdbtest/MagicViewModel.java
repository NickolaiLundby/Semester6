package com.runekeena.ampdbtest;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MagicViewModel extends AndroidViewModel {

    private MagicRepository repository;
    private LiveData<List<Collection>> collections;

    public MagicViewModel(@NonNull Application application) {
        super(application);
        repository = new MagicRepository(application);
        collections = repository.getAllCollections();
    }

    public void insertCollection(Collection collection){
        repository.insertCollection(collection);
    }

    public void updateCollection(Collection collection){
        repository.updateCollection(collection);
    }

    public void deleteCollection(Collection collection){
        repository.deleteCollection(collection);
    }

    public LiveData<List<Collection>> getCollections(){
        return collections;
    }
}

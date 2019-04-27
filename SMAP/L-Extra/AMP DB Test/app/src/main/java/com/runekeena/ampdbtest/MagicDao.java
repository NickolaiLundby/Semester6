package com.runekeena.ampdbtest;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface MagicDao {

    @Insert
    void insertCollection(Collection collection);

    @Update
    void updateCollection(Collection collection);

    @Delete
    void deleteCollection(Collection collection);

    @Query("SELECT * FROM collection_table")
    LiveData<List<Collection>> getAllCollections();

    @Insert
    void insertCard(Card card);

    @Update
    void updateCard(Card card);

    @Query("SELECT * FROM card_table WHERE title LIKE :title")
    LiveData<Card> getCardByTitle(String title);

    @Query("SELECT * FROM ref_card_table WHERE collectionId LIKE :id")
    LiveData<List<RefCard>> getRefCardsByCollectionId(int id);

    @Query("SELECT * FROM card_table INNER JOIN ref_card_table ON card_table.id=ref_card_table.cardId WHERE ref_card_table.collectionId=:id")
    LiveData<List<Card>> getCardsByCollectionId(int id);
}

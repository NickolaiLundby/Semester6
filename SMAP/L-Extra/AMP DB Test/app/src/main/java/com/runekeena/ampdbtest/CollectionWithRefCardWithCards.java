package com.runekeena.ampdbtest;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class CollectionWithRefCardWithCards {
    
    @Embedded
    Collection collection;

    @Relation(parentColumn = "coId", entityColumn = "collectionId", entity = RefCard.class)
    List<RefCardWithCard> refCards;
}

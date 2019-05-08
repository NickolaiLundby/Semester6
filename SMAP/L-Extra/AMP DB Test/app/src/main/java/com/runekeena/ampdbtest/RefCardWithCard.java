package com.runekeena.ampdbtest;

import android.arch.persistence.room.Embedded;

public class RefCardWithCard{

    @Embedded
    RefCard refCard;

    @Embedded
    Card card;
}

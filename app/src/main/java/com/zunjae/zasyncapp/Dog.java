package com.zunjae.zasyncapp;

import com.zunjae.zasync.CachingLimit;

import java.util.concurrent.TimeUnit;

/**
 * Created by zunjae on 6/29/2017.
 */

public class Dog implements CachingLimit {
    private final String name;
    private final int numberOfLegs;
    private final long cacheLimit;

    public Dog(String name, int numberOfLegs) {
        this.name = name;
        this.numberOfLegs = numberOfLegs;
        this.cacheLimit = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7);
    }

    public String getName() {
        return name;
    }

    public int getNumberOfLegs() {
        return numberOfLegs;
    }

    @Override
    public long getExpirationDate() {
        return cacheLimit;
    }
}

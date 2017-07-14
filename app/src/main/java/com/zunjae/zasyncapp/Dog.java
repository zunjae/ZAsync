package com.zunjae.zasyncapp;

import com.zunjae.zasync.CachingLimit;

/**
 * Created by zunjae on 6/29/2017.
 */

public class Dog implements CachingLimit {
    private final String name;
    private final int numberOfLegs;
    private long cacheLimit;

    public Dog(String name, int numberOfLegs) {
        this.name = name;
        this.numberOfLegs = numberOfLegs;
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

    @Override
    public void setExpirationDate(long expirationDate) {
        this.cacheLimit = expirationDate;
    }
}

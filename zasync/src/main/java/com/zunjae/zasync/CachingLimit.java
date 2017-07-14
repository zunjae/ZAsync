package com.zunjae.zasync;

/**
 * Created by zunjae on 7/13/2017.
 */

public interface CachingLimit {
    long getExpirationDate();

    void setExpirationDate(long expirationDate);
}

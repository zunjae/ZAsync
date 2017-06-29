package com.zunjae.zasyncapp;

import android.util.Log;

import java.security.SecureRandom;

/**
 * Created by zunjae on 6/29/2017.
 */

public class DogRepository {
    private final static String TAG = "DogRepository";

    public static Dog getDogFromRepo() {
        return new Dog("zunjae", 4);
    }

    public static boolean hasDogSaved() {
        int randomNumber = new SecureRandom().nextInt(1000);
        return randomNumber > 1;
    }

    public static Dog getMockDogFromCloud() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "done with sleeping...");
        return new Dog("Cloud boii", 4);
    }
}

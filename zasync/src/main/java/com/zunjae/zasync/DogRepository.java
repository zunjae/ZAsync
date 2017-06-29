package com.zunjae.zasync;

import java.security.SecureRandom;

/**
 * Created by zunjae on 6/29/2017.
 */

public class DogRepository {
    public static Dog getDogFromRepo() {
        return new Dog("zunjae", 4);
    }

    public static boolean hasDogSaved() {
        int randomNumber = new SecureRandom().nextInt(1000);
        return randomNumber > 500;
    }
}

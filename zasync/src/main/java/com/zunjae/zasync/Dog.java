package com.zunjae.zasync;

/**
 * Created by zunjae on 6/29/2017.
 */

public class Dog {
    private String name;
    private int numberOfLegs;

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
}

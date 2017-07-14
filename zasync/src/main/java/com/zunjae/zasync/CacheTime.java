package com.zunjae.zasync;

import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Copied pieces from the java.util.concurrent package.
 * All credits goes to :Doug Lea:
 * <p>
 * I take no credits. This wrapper is made to prevent some boilerplate code
 */
public final class CacheTime {

    public static long SECONDS(long d) {
        return System.currentTimeMillis() + x(d, C3 / C2, MAX / (C3 / C2));
    }

    public static long MINUTES(long d) {
        return System.currentTimeMillis() + x(d, C4 / C2, MAX / (C4 / C2));
    }

    public static long HOURS(long d) {
        return System.currentTimeMillis() + x(d, C5 / C2, MAX / (C5 / C2));
    }

    public static long DAYS(long d) {
        return System.currentTimeMillis() + x(d, C6 / C2, MAX / (C6 / C2));
    }

    // Handy constants for conversion methods
    private final static long C0 = 1L;
    private final static long C1 = C0 * 1000L;
    private final static long C2 = C1 * 1000L;
    private final static long C3 = C2 * 1000L;
    private final static long C4 = C3 * 60L;
    private final static long C5 = C4 * 60L;
    private final static long C6 = C5 * 24L;

    private final static long MAX = Long.MAX_VALUE;

    public boolean unitTest() {
        long a1 = SECONDS(1);
        long a2 = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(1);

        long b1 = MINUTES(1);
        long b2 = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1);

        long c1 = HOURS(1);
        long c2 = System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1);

        long d1 = DAYS(1);
        long d2 = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1);

        Log.i(getClass().getSimpleName(), String.format("[%d] vs [%d]", a1, a2));
        Log.i(getClass().getSimpleName(), String.format("[%d] vs [%d]", b1, b2));
        Log.i(getClass().getSimpleName(), String.format("[%d] vs [%d]", c1, c2));
        Log.i(getClass().getSimpleName(), String.format("[%d] vs [%d]", d1, d2));
        return (a1 == a2) && (b1 == b2) && (c1 == c2) && (d1 == d2);
    }

    /**
     * Scale d by m, checking for overflow.
     * This has a short name to make above code more readable.
     */
    private static long x(long d, long m, long over) {
        if (d > +over) return Long.MAX_VALUE;
        if (d < -over) return Long.MIN_VALUE;
        return d * m;
    }
}

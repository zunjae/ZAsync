package com.zunjae.zasync;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zunjae on 6/29/2017.
 */


public abstract class ZAsync<Result> {

    private static final String TAG = "ZAsync";

    // todo : figure out if context is required. Probably not.
    @NonNull
    private Context context;

    /**
     * Call cancel() in onDestroy to prevent memory leaks
     */
    private boolean cancelled = false;

    public ZAsync(@NonNull Context context) {
        if (context instanceof Activity) {
            throw new IllegalArgumentException("Make sure you provide the App Context to prevent leaks");
        }
        this.context = context;
    }

    /**
     * Used to display a loading indicator etc.
     */
    protected void onPreExecute() {
    }

    /**
     * Instead of directly calling the internet,
     * first check if our database already has the content saved
     * True means there is cache available.
     * If that is the case, returnCache() will be called instead of doInBackground();
     *
     * @return a boolean which tells if we locally have cache available
     */
    public abstract boolean hasCache();

    /**
     * This method will be called if hasCache() return true
     * This will load the data saved locally instead of connecting to the internet
     *
     * @return the results saved locally
     */
    public abstract Result returnCache();

    /**
     * Connect to the internet to load new data.
     */
    public abstract Result doInBackground();

    public void cancel() {
        this.cancelled = true;
    }

    /**
     * This will be called based off the result from returnCache() or onPostExecute()
     * Will display the Results back on the main thread
     */
    protected void onPostExecute(@Nullable Result result) {
    }

    public void execute() {
        Log.i(TAG, "ZAsync executed");

        onPreExecute();

        // add some proper threading here... :')

        Result result;
        if (hasCache()) {
            result = returnCache();
        } else {
            result = doInBackground();
        }

        if (cancelled) {
            Log.i(TAG, "[onPostExecute] Not continuing onPostExecute");
            return;
        }

        onPostExecute(result);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public boolean isCancelled() {
        return cancelled;
    }
}

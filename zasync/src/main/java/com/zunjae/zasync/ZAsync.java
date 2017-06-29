package com.zunjae.zasync;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zunjae on 6/29/2017.
 */


public abstract class ZAsync<Result> {

    private static final String TAG = "ZAsync";

    /**
     * Call cancel() in onDestroy to prevent memory leaks
     */
    private boolean cancelled = false;

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Result result;
                if (hasCache()) {
                    result = returnCache();
                } else {
                    result = doInBackground();
                }
                communicateBackToUIThread(result);
            }
        }).start();
    }

    /**
     * Will be called after execute()
     * If the activity was destroyed, then we should not push back data to prevent leaks
     *
     * @param result the results from either returnCache() or doInBackground()
     */
    private void communicateBackToUIThread(final Result result) {
        if (cancelled) {
            Log.i(TAG, "[communicateBackToUIThread] Not continuing due to cancel() being called");
            return;
        }

        // communicate back to the main thread
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                onPostExecute(result);
            }
        });
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public boolean isCancelled() {
        return cancelled;
    }
}

package com.zunjae.zasync;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
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

    @Nullable
    private Application.ActivityLifecycleCallbacks activityCallBackListener;

    @Nullable
    private Application application;

    public void cancelOnActivityDestroyed(Application application, final Activity calledFromActivity) {
        this.application = application;
        activityCallBackListener = new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                // todo: check if there is a better way to compare classes
                if (activity.getClass() == calledFromActivity.getClass()) {
                    cancel();
                }
            }
        };
        application.registerActivityLifecycleCallbacks(activityCallBackListener);
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
    protected boolean hasCache() {
        return false;
    }

    /**
     * This method will be called if hasCache() return true
     * This will load the data saved locally instead of connecting to the internet
     *
     * @return the results saved locally
     */
    protected Result returnCache() {
        return null;
    }

    /**
     * Connect to the internet to load new data.
     */
    public abstract Result doInBackground();

    public void cancel() {
        this.cancelled = true;
        // unregister onDestroy listener
        if (application != null) {
            application.unregisterActivityLifecycleCallbacks(activityCallBackListener);
        }
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

        // unregister onDestroy listener
        if (application != null) {
            application.unregisterActivityLifecycleCallbacks(activityCallBackListener);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public boolean isCancelled() {
        return cancelled;
    }
}

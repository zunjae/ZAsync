package com.zunjae.zasyncapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.zunjae.zasync.CacheTime;
import com.zunjae.zasync.ZAsync;

import java.util.concurrent.TimeUnit;

/**
 * Example using the ZAsync
 * For obvious reasons, this class is very verbose, but it'll give you an overview
 * of what is happening
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private TextView helloWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helloWorld = (TextView) findViewById(R.id.helloWorld);

        DogAsyncTask asyncTask = new DogAsyncTask(this);
        asyncTask.setForceSkipCache(false);
        asyncTask.execute();

        Log.i(TAG, "Current time in ms: \t" + System.currentTimeMillis());
        Log.i(TAG, "Using CacheTime: \t" + CacheTime.DAYS(7));
        Log.i(TAG, "Using TimeUnit: \t" + (System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)));
        Log.i(TAG, "Shitty inconsistent unit test is valid?\t" + new CacheTime().unitTest());
    }

    private class DogAsyncTask extends ZAsync<Dog> {
        private final String TAG = "DogAsyncTask";

        public DogAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected boolean hasCache() {
            Log.i(TAG, "Checking if dog is saved in the database. " +
                    "This should always be a lightweight task");
            return DogRepository.hasDogSaved();
        }

        @Override
        protected Dog returnCache() {
            Log.i(TAG, "Returning data from Cache");
            return DogRepository.getDogFromRepo();
        }

        @Override
        public void onPreExecute() {
            Log.i(TAG, "Progressbar is now showing");
        }

        @Override
        public Dog doInBackground() {
            Log.i(TAG, "Returning data from cloud");
            return DogRepository.getMockDogFromCloud();
        }

        @Override
        public void onPostExecute(@Nullable Dog dog) {
            Log.i(TAG, "Progressbar dismissed");

            if (dog != null) {
                helloWorld.setText("Doggy found: " + dog.getName());
            } else {
                helloWorld.setText("Failed to load doggy");
            }
        }
    }
}

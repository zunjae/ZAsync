package com.zunjae.zasyncapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zunjae.zasync.ZAsync;

public class MainActivity extends AppCompatActivity {

    private DogAsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asyncTask = new DogAsyncTask();
        asyncTask.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (asyncTask != null) {
            asyncTask.cancel();
        }
    }

    private class DogAsyncTask extends ZAsync<Dog> {
        private static final String TAG = "DogAsyncTask";

        @Override
        public void onPreExecute() {
            Log.i(TAG, "Progressbar loaded");
        }

        @Override
        public boolean hasCache() {
            return DogRepository.hasDogSaved();
        }

        @Override
        public Dog returnCache() {
            return DogRepository.getDogFromRepo();
        }

        @Override
        public Dog doInBackground() {
            return DogRepository.getMockDogFromCloud();
        }

        @Override
        public void onPostExecute(@Nullable Dog dog) {
            Log.i(TAG, "Progressbar dismissed");

            if (dog != null) {
                Log.i(TAG, "Loaded doggy with name " + dog.getName());
            } else {
                Log.i(TAG, "Since the process failed, show retry button");
            }
        }
    }
}

package com.zunjae.zasyncapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zunjae.zasync.Dog;
import com.zunjae.zasync.DogRepository;
import com.zunjae.zasync.ZAsync;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DogAsyncTask dogTest = new DogAsyncTask(getApplicationContext());
        dogTest.execute();
    }


    private class DogAsyncTask extends ZAsync<Dog> {
        private static final String TAG = "DogAsyncTask";

        public DogAsyncTask(@NonNull Context context) {
            super(context);
        }

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
            // simulate connecting to the cloud
            return new Dog("Shitty library", 5);
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

package com.zunjae.zasyncapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.zunjae.zasync.ZAsync;

public class MainActivity extends AppCompatActivity {

    private TextView helloWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helloWorld = (TextView) findViewById(R.id.helloWorld);

        DogAsyncTask asyncTask = new DogAsyncTask();
        asyncTask.cancelOnActivityDestroyed(getApplication(), this);
        asyncTask.execute();
    }

    private class DogAsyncTask extends ZAsync<Dog> {
        private final String TAG = "DogAsyncTask";

        @Override
        protected boolean hasCache() {
            return true;
        }

        @Override
        protected Dog returnCache() {
            return new Dog("zunjae", 4);
        }

        @Override
        public void onPreExecute() {
            Log.i(TAG, "Progressbar loaded");
        }

        @Override
        public Dog doInBackground() {
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

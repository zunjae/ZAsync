# ZAsync
Replacement for the default AsyncTask. Has similar interfaces, but added support for caching to make your code more readable


# Installation

Add this in your root build.gradle at the end of repositories:

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
  
  
Add the dependency in your app build.gradle file:

```
dependencies {
    compile 'com.github.zunjae:ZAsync:0.4'
}
```

# Usage:

```java
@Nullable
private DogAsyncTask asyncTask;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    asyncTask = new DogAsyncTask(this);
    asyncTask.setForceSkipCache(false); //optional
    asyncTask.execute();
}

---

private class DogAsyncTask extends ZAsync<Dog> {

    public DogAsyncTask(Activity activity) {
        super(activity);
    }

    @Override
    public void onPreExecute() {
        // show notification
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
        // dismiss notification
    }
}
---

// optional cancel listener
@OnClick(R.id.cancelButton)
public void onCancelButtonClicked() {
    if (asyncTask != null) {
        asyncTask.cancel();
    }
}

```

# Note

* It is required to add a constructor calling the superclass
* No need to cancel the ZAsync from the onDestroy method. This now happens for you. If you do need to cancel the AsyncTask, call .cancel()


# License

```
Copyright 2017 ZUNJAE
   
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
   
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
   
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
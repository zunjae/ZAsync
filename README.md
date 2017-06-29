# ZAsync
Custom implementation for an AsyncTask. Includes easy caching control

Add it in your root build.gradle at the end of repositories:

```java
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
  
  
Add the dependency:

```java
dependencies {
    compile 'com.github.zunjae:ZAsync:0.1'
}
```

#Usage:

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    asyncTask = new DogAsyncTask();
    asyncTask.execute();
}

---

private class DogAsyncTask extends ZAsync<Dog> {

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
@Override
protected void onDestroy() {
    super.onDestroy();
    if (asyncTask != null) { asyncTask.cancel(); }
}

```




# License

```Copyright 2017 ZUNJAE
   
   Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
   
   The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
   
   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.```
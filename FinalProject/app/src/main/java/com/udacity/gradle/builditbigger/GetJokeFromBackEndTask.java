package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;

import com.example.toufik.myapplication.joketellerbackend.myApi.MyApi;
import com.example.toufik.myapplication.joketellerbackend.myApi.model.JokeHolder;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * AsyncTask to retrieve a joke from the GCE module.
 * The class publishes an event via EventBus to the MainActivity which in return checks
 * whether the message is null or not and starts a new Activity to display the joke.
 */
public class GetJokeFromBackEndTask extends AsyncTask<Context, Object,
        JokeHolder> {

    private static MyApi myApiService = null;
    private Context mContext;

    @Override
    protected JokeHolder doInBackground(Context... params) {

        mContext = params[0];

        if (myApiService == null) {  // Only do this once
          /*  MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://192.168.0.120:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });*/
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://joketeller-163721.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        try {
            return myApiService.sayJoke().execute().getData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JokeHolder jokeHolder) {
        super.onPostExecute(jokeHolder);
        // Post event to the UI thread
        EventBus.getDefault().post(new JokeEvent(jokeHolder));
    }
}

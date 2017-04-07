package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.toufik.myapplication.joketellerbackend.myApi.MyApi;
import com.example.toufik.myapplication.joketellerbackend.myApi.model.JokeHolder;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * AsyncTask to retrieve a joke from the GCE module.
 * The class publishes an event via EventBus to the MainActivity which in return checks
 * whether the message is null or not and starts a new Activity to display the joke.
 */
public class GetJokeFromBackEndTask extends AsyncTask<Void, Object,
        JokeHolder> {

    ProgressDialog progressDialog;
    private static MyApi myApiService = null;

    public GetJokeFromBackEndTask(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (progressDialog != null) {
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Fetching Joke...");
            progressDialog.show();
        }
    }

    @Override
    protected JokeHolder doInBackground(Void... params) {

        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://192.168.0.120:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
/*
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("myDeployedBackEnd.appspot.com/_ah/api/");
*/

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
    protected void onCancelled() {
        // stop progress dialog
        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
            progressDialog = null;
        }
        super.onCancelled();
    }

    @Override
    protected void onPostExecute(JokeHolder jokeHolder) {
        super.onPostExecute(jokeHolder);

        // stop progress dialog
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        // Post event to the UI thread
        EventBus.getDefault().post(new JokeEvent(jokeHolder));
    }
}

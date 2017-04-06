package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.toufik.myapplication.joketellerbackend.myApi.MyApi;
import com.example.toufik.myapplication.joketellerbackend.myApi.model.JokeHolder;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import tdbouk.udacity.com.jokedisplaylib.JokeActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        new GetJokeFromBackEndTask().execute(MainActivity.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JokeEvent event) {

        com.example.toufik.myapplication.joketellerbackend.myApi.model.JokeHolder jokeHolder = event.jokeHolder;
        if (jokeHolder != null) {
            Intent displayJokeIntent = new Intent(this, JokeActivity.class);
            displayJokeIntent.putExtra(JokeActivity.EXTRA_JOKE_QUESTION, jokeHolder.getQuestion());
            displayJokeIntent.putExtra(JokeActivity.EXTRA_JOKE_ANSWER, jokeHolder.getAnswer());
            startActivity(displayJokeIntent);
        } else {
            Snackbar.make(findViewById(R.id.fragment), R.string.error_backend,
                    Snackbar.LENGTH_SHORT).show();
        }
    }
}


class GetJokeFromBackEndTask extends AsyncTask<Context, Object,
        com.example.toufik.myapplication.joketellerbackend.myApi.model.JokeHolder> {

    private static MyApi myApiService = null;
    private Context mContext;

    @Override
    protected com.example.toufik.myapplication.joketellerbackend.myApi.model.JokeHolder doInBackground(Context... params) {

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



package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import tdbouk.udacity.com.jokedisplaylib.JokeActivity;


public class MainActivity extends AppCompatActivity {

    private GetJokeFromBackEndTask getJokeFromBackEndTask;
    private ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        if (getJokeFromBackEndTask != null)
            getJokeFromBackEndTask.cancel(true);
        if (progressDialog != null)
            progressDialog.dismiss();
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

    private boolean checkInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

/*
    private boolean checkNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        } else {
            // not connected to the internet
            return false;
        }
        return true;
    }
*/

    public void tellJoke(View view) {
        if (checkInternetConnection()) {
            progressDialog = new ProgressDialog(MainActivity.this);
            getJokeFromBackEndTask = new GetJokeFromBackEndTask(progressDialog);
            getJokeFromBackEndTask.execute();
        } else {
            Snackbar.make(findViewById(R.id.fragment), R.string.error_no_internet, Snackbar.LENGTH_SHORT);
        }
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



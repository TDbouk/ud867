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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import tdbouk.udacity.com.jokedisplaylib.JokeActivity;


public class MainActivity extends AppCompatActivity {

    private GetJokeFromBackEndTask mGetJokeFromBackEndTask;
    private ProgressDialog mProgressDialog;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        if (mGetJokeFromBackEndTask != null)
            mGetJokeFromBackEndTask.cancel(true);
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                showJoke();
            }
        });
        requestNewInterstitial();
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

    /**
     * Load interstitial Ad.
     */
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    /**
     * A helper method to check if an internet connection is available.
     *
     * @return <code>false</code> if internet connection is not available
     */
    private boolean checkInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    /**
     * A method to fetch a joke from the GCE module if internet connection is available
     * using {@link GetJokeFromBackEndTask}.
     */
    private void showJoke() {

        if (checkInternetConnection()) {
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mGetJokeFromBackEndTask = new GetJokeFromBackEndTask(mProgressDialog);
            mGetJokeFromBackEndTask.execute();
        } else {
            Snackbar.make(findViewById(R.id.fragment), R.string.error_no_internet, Snackbar.LENGTH_SHORT);
        }
    }

    /**
     * Handles on click events
     *
     * @param view
     */
    public void tellJoke(View view) {

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            showJoke();
        }
    }

    /**
     * Subscribe to the event in order to receive it when posted
     *
     * @param event
     */
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



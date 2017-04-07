package tdbouk.udacity.com.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import com.example.toufik.myapplication.joketellerbackend.myApi.model.JokeHolder;
import com.udacity.gradle.builditbigger.GetJokeFromBackEndTask;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 * This test tests fetching data from server via AsyncTask.
 * The test fails if the task returns null.
 */
@RunWith(AndroidJUnit4.class)
public class AsyncTaskInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {

        // test if GetJokeFromBackEndTask is retrieving a joke or not
        JokeHolder jh = new GetJokeFromBackEndTask(null).execute().get();
        assertTrue("joke is null", jh != null);
    }
}

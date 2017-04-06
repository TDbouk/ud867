package tdbouk.udacity.com.builditbigger;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.toufik.myapplication.joketellerbackend.myApi.model.JokeHolder;
import com.udacity.gradle.builditbigger.GetJokeFromBackEndTask;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AsyncTaskInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        // test for the package name
        assertEquals("com.udacity.gradle.builditbigger", appContext.getPackageName());

        // test if GetJokeFromBackEndTask is retrieving a joke or not
        JokeHolder jh = new GetJokeFromBackEndTask().execute().get();
        assertTrue("joke is null", jh != null);
    }
}

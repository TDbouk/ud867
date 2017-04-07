package tdbouk.udacity.com.jokedisplaylib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE_QUESTION = "com.question";
    public static final String EXTRA_JOKE_ANSWER = "com.answer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        // reference widgets in layout
        TextView question = (TextView) findViewById(R.id.tv_joke_question);
        TextView answer = (TextView) findViewById(R.id.tv_joke_answer);

        // get calling intent
        Intent parentIntent = getIntent();

        // set data to views in layout
        if (parentIntent != null) {

            if (parentIntent.hasExtra(EXTRA_JOKE_QUESTION)) {
                question.setText(parentIntent.getStringExtra(EXTRA_JOKE_QUESTION));
            }
            if (parentIntent.hasExtra(EXTRA_JOKE_ANSWER)) {
                answer.setText(parentIntent.getStringExtra(EXTRA_JOKE_ANSWER));
            }
        }
    }
}

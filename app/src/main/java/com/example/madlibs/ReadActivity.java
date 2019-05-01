package com.example.madlibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import static android.text.Html.FROM_HTML_MODE_COMPACT;

public class ReadActivity extends AppCompatActivity {

    private Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        if (savedInstanceState == null) {
            // get the data (story object) send by the previous activity
            Intent intent = getIntent();
            this.story = (Story) intent.getSerializableExtra("filled_story");

        } else {
            // restore story object
            this.story = (Story) savedInstanceState.getSerializable("storyObj");
        }

        // display the story text in activity
        String storyText = story.toString();
        TextView field = findViewById(R.id.wholeStory);
        // ensure the html can be read
        field.setText(Html.fromHtml(storyText, FROM_HTML_MODE_COMPACT));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save the story instance
        outState.putSerializable("storyObj", story);
    }

    // when the user presses the back button
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        // clear the story object
        story.clear();

        // send the user to the start screen
        startActivity(new Intent(ReadActivity.this, MainActivity.class));
        finish();
    }

    public void restartClicked(View v) {
        // empty the story object?????

        // send user to first activity
        Intent intent = new Intent(ReadActivity.this, MainActivity.class);
        startActivity(intent);

    }
}

package com.example.madlibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PlaceholderActivity extends AppCompatActivity {

    // save the serialized extra (the send story) as a private var
    private Story story;
    private int totalWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);

        if (savedInstanceState == null) {
            // get the data (Story object) send by MainActivity)
            Intent intent = getIntent();
            this.story = (Story) intent.getSerializableExtra("chosen_story");

        } else {
            // restore story object
            this.story = (Story) savedInstanceState.getSerializable("storyObj");
        }

        // set the Hint in the TextView
        String placeholder = story.getNextPlaceholder();
        TextView textField = findViewById(R.id.editText);
        textField.setHint(placeholder);

        // set total of words and start progress
        this.totalWords = story.getPlaceholderCount();
        int wordsDone = story.getPlaceholderFilledIn();
        TextView progressText = findViewById(R.id.progressText);
        progressText.setText(wordsDone + "/" + totalWords + " words done");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save the story instance
        outState.putSerializable("storyObj", story);
    }

    // on click listener for the Next button
    public void nextClicked(View v) {

        TextView textField = findViewById(R.id.editText);
        String typedWord = textField.getText().toString();

        // check if the textField wasn't empty
        if (typedWord.equals("")) {

            // show the feedback message
            TextView feedback = findViewById(R.id.noTextError);
            feedback.setVisibility(View.VISIBLE);

        } else {
            // store the filled in words
            story.fillInPlaceholder(typedWord);

            // erase the typed text
            textField.setText("");

            // update the Hint in the TextView
            String placeholder = story.getNextPlaceholder();
            textField.setHint(placeholder);

            // update the Progress
            int wordsDone = story.getPlaceholderFilledIn();
            TextView progressText = findViewById(R.id.progressText);
            progressText.setText(wordsDone + "/" + totalWords + " words done");

            // hide the feedback message
            TextView feedback = findViewById(R.id.noTextError);
            feedback.setVisibility(View.INVISIBLE);

            // start new activity if all words are filled in
            if (story.isFilledIn()) {
                // pass Story to next activity
                Intent intent = new Intent(PlaceholderActivity.this, ReadActivity.class);
                intent.putExtra("filled_story", story);

                // send user to the next screen
                startActivity(intent);
            }
        }


    }
}

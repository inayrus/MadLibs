package com.example.madlibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startClicked(View v) {

        // figure out what story the user chose
        RadioGroup radioGroup = findViewById(R.id.RadioGroup);
        int chosenButtonId = radioGroup.getCheckedRadioButtonId();

        // check whether a button is selected
        if (chosenButtonId != -1) {

            // get the name of the chosen story
            RadioButton chosenButton = findViewById(chosenButtonId);
            String storyName = chosenButton.getText().toString();

            // get the id of the story's txt file
            int resID = getResources().getIdentifier("madlib_" + storyName,"raw", getPackageName());

            // initialize a the chosen story
            InputStream stream = getResources().openRawResource(resID);
            Story story = new Story(stream);

            // pass Story to next activity
            Intent intent = new Intent(MainActivity.this, PlaceholderActivity.class);
            intent.putExtra("chosen_story", story);

            // send user to the next screen
            startActivity(intent);

        } else {
            // show error message when no button is selected
            TextView errorText = findViewById(R.id.choiceError);
            errorText.setVisibility(View.VISIBLE);
        }
    }
}

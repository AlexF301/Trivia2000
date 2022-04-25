package edu.moravian.csci299.Trivia2000;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 *
 * Authors: Alexander Flores Sosa and Edwin Cojitambo
 */
public class MainActivity extends MenuActivity implements View.OnClickListener {
    private Spinner gameDifficulty;
    private SharedPreferences preferences;
    /**
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("game preferences", Context.MODE_PRIVATE);

        gameDifficulty = findViewById(R.id.difficulty);
        // sets a spinner with difficulty's
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.modes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameDifficulty.setAdapter(adapter);

        gameDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             *
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("game difficulty", gameDifficulty.getSelectedItem().toString());
                editor.apply();
            }

            /**
             *
             * @param parent
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>) gameDifficulty.getAdapter();
        // set the default according to value
        gameDifficulty.setSelection(arrayAdapter.getPosition(preferences.getString("game difficulty", "")));

        Button startButton = findViewById(R.id.play_button);
        startButton.setOnClickListener(this);
    }

    /**
     * Sets the difficulty that was picked, as well as setting up the game beased on which topic
     * was picked using intents
     * @param v the view that was clicked
     */
    @Override
    public void onClick(View v) {
        Intent pickTopic = new Intent(this, TopicPickerActivity.class);
        String difficulty = gameDifficulty.getSelectedItem().toString();
        pickTopic.putExtra("Game Difficulty", difficulty);
        startActivity(pickTopic);
    }

}


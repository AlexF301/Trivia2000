package edu.moravian.csci299.Trivia2000;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameEndActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private String difficulty;
    private String topic;
    private int score;
    private EditText playerName;

    /**
     * Sets the variables of difficulty and topic with intents, as well as setting the score
     * TextView from the game score intent. Sets the listener for the play again button, and
     * creating and commiting a new instance of the high score fragment
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        this.score = getIntent().getIntExtra("Game Score", 0);
        this.difficulty = getIntent().getStringExtra("Game Difficulty");
        this.topic = getIntent().getStringExtra("Game Topic");

        TextView score_box = findViewById(R.id.score);
        score_box.setText(Integer.toString(score));

        this.playerName = findViewById(R.id.name_box);
        playerName.addTextChangedListener(this);


        Button button = findViewById(R.id.play_again);
        button.setOnClickListener(this);
        
        HighScoreFragment highScoreFragment = HighScoreFragment.newInstance(topic, score);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment, highScoreFragment)
                .commit();

    }

    /**
     * Sets the result of the passed intent from Game Screen's activity, and allows for a new
     * game to be plaey
     * @param v the View that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent playAgain = new Intent(this, TopicPickerActivity.class);
        playAgain.putExtra("Game Difficulty", this.difficulty);
        startActivity(playAgain);
        finish();
    }

    /**
     *  Required as part of listener but leave empty
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /**
     * Required as part of listener but leave empty
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    /**
     * Required as part of listener but leave empty
     * @param edited
     */
    @Override
    public void afterTextChanged(Editable edited) {
    }
}

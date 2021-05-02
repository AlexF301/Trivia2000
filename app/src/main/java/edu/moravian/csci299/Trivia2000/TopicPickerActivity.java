package edu.moravian.csci299.Trivia2000;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

/**
* The activity that allows for users to pick a topic
*/
public class TopicPickerActivity extends MenuActivity implements View.OnClickListener {
    private String difficulty;
    private String topic;

    private final List<Integer> topicIds = Arrays.asList(
            R.id.button, R.id.button2, R.id.button3, R.id.button4,R.id.button5);
    private final List<String> stringTopics = Arrays.asList("soccer","basketball",
            "music","movies","random");

    /**
     * Gets the difficulty intent, as well as setting the listeners for the topic choices
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_picker);

        this.difficulty = getIntent().getStringExtra("Game Difficulty");
        for (int id : topicIds) {
            findViewById(id).setOnClickListener(this);
        }
    }

    /**
     * Sets the topic chosen by the user, passes along the intents for the difficulty
     * and topic, and starts the game based on those intents
     * @param v the view that was clicked
     */
    @Override
    public void onClick(View v) {
        int position = topicIds.indexOf(v.getId());
        this.topic = stringTopics.get(position);

        Intent startGame = new Intent(this, GameActivity.class);
        startGame.putExtra("Game Difficulty", this.difficulty);
        startGame.putExtra("Topic Type", this.topic);
        startActivity(startGame);
    }
}
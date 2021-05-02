package edu.moravian.csci299.Trivia2000;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * A single high score that has an associated id, score, topic type, and player
 */
@Entity
public class HighScore {
    @PrimaryKey
    @NonNull
    public int id;
    @NonNull
    public int score;
    @NonNull
    public String topicType = "";
    @NonNull
    public String player_name = "";
}
package edu.moravian.csci299.Trivia2000;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * The connection to the database of Questions and scores for the app.
 */
@Database(entities = {Question.class, HighScore.class}, version = 1, exportSchema = false)
public abstract class QuizDatabase extends RoomDatabase {
    public abstract QuizDao QuizDAO();
    public abstract ScoreDao scoreDAO();

}


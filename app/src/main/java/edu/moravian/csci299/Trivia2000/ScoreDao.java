package edu.moravian.csci299.Trivia2000;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
* The data acess object for performing queries involving scores of the quiz
*/
@Dao
public interface ScoreDao {
    /**
     * Gets all of the high scores by its topic type
     * @param topicType the topic that was selected
     * @return the scores by the associated topic 
     */
    @Query("SELECT * FROM HighScore WHERE topicType = (:topicType)")
    LiveData<List<HighScore>> getHighScoreByTopic(String topicType);

    /**
     * Updates a high score in the database.
     * @param highScore the score to update
     */
    @Update
    void updateScore(HighScore highScore);

    /**
     * Adds a score to the database.
     * @param highScore the score to add
     */
    @Insert
    void addScore(HighScore highScore);

    /**
     * Removes a score in the database.
     * @param highScore the score to remove
     */
    @Delete
    void removeScore(HighScore highScore);

}
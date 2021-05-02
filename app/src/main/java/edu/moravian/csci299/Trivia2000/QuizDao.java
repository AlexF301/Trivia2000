package edu.moravian.csci299.Trivia2000;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

/**
* The data acess object for performing queries involving questions of the quiz
*/
@Dao
public interface QuizDao {
    /**
     * @return live-data view of all questions
     */
    @Query("SELECT * FROM question")
    LiveData<List<Question>> getAllQuestions();

    /**
     * @return live-data view of all questions by its topic
     */
    @Query("SELECT * FROM question WHERE topicType = (:topicType) ")
    LiveData<List<Question>> getQuestionsByTopic(String topicType);

    /**
     * @return live-data view of the question by its id
     */
    @Query("SELECT * FROM question WHERE id = (:id)")
    LiveData<Question> getQuestion(int id);

    /**
     * Adds a question into the database
     * @param question the question being added
     */
    @Insert
    void addQuestion(Question question);

    /**
     * Updates a question in the database.
     * @param question the question to update
     */
    @Update
    void updateQuestion(Question question);

    /**
     * Remove a question in the database.
     * @param question the question to remove
     */
    @Delete
    void removeQuestion(Question question);
}



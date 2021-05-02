package edu.moravian.csci299.Trivia2000;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Encapsulates the logic for accessing data from single/multiple sources. Fetches our data
 * from the database.
*/ 
public class QuizRepository {
    private final QuizDatabase database;
    private final QuizDao quizDao;
    private final ScoreDao scoreDao;
    private final Executor executor = Executors.newSingleThreadExecutor();
    Locale local = Locale.getDefault();

    /**
     * builds the database using the context of the application, also allows for switching between
     * the english and spanish language for questions
     * @param context the state of the application
     */
    private QuizRepository(Context context) {
        if (local.getLanguage().equals(new Locale("en").getLanguage())) {
            database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    QuizDatabase.class,
                    "questions").createFromAsset("questions.db").build();
        } else {
            database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    QuizDatabase.class,
                    "questions").createFromAsset("questions-es.db").build();
        }
        quizDao = database.QuizDAO();
        scoreDao = database.scoreDAO();
    }

    /**
     *  Gets all the questions from a topic type
     * @param topicType the topic that was selected
     * @return all questions by its topic type
     */
    public LiveData<List<Question>> getQuestionsByTopic(String topicType) {
        return quizDao.getQuestionsByTopic(topicType);
    }

    /**
     * Gets the high scores by its topic type
     * @param topicType the topic selected
     * @return all the scores by its topic
     */
    public LiveData<List<HighScore>> getHighScoreByTopic(String topicType) {
        return scoreDao.getHighScoreByTopic(topicType);
    }

    /**
     * updates a score in the database
     * @param score the score being updated
     */
    public void updateScore(HighScore score) {
        executor.execute(() -> {
            scoreDao.updateScore(score);
        });
    }

    /**
     * Adds a score to the database
     * @param score the score being added
     */
    public void addScore(HighScore score) {
        executor.execute(() -> {
            scoreDao.addScore(score);
        });
    }

    /**
     * removes a score from the database
     * @param score the score being removed
     */
    public void removeScore(HighScore score) {
        executor.execute(() -> {
            scoreDao.removeScore(score);
        });
    }
    
    // The single instance of the repository
    private static QuizRepository INSTANCE;

    /**
     * gets INSTANCE of quiz Repository to then be able to get
     *
     * @return instance of quiz Repository
     */
    public static QuizRepository get() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Question Repository must be initialized");
        }
        return INSTANCE;
    }

    /**
     * Initializes new INSTANCE of QuizRepository
     *
     * @param context - application context
     */
    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new QuizRepository(context);
        }
    }
}

package edu.moravian.csci299.Trivia2000;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Question object class that contains all the data for a question including its id, question, 
 * answer, three false answers, and a topic type
 */
@Entity
public class Question {
    @PrimaryKey
    public int id;
    @NonNull
    public String question = "";
    @NonNull
    public String answer = "";
    @NonNull
    public String falseAnswer1 = "";
    @NonNull
    public String falseAnswer2 = "";
    @NonNull
    public String falseAnswer3 = "";
    @NonNull
    public String topicType = "";

    /**
     * Gets a random value from the answer / three false answers
     * @return a random answer / false answer
     */
    public String getRandomValue() {
        Random random = new Random();
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(0, answer);
        map.put(1, falseAnswer1);
        map.put(2, falseAnswer2);
        map.put(3, falseAnswer3);

        return map.get(random.nextInt(4));
    }
}


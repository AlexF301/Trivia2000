package edu.moravian.csci299.Trivia2000;

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
* The Trivia game that keeps track of the score, the question, the number of questions,
*/
public class TriviaGame extends ViewModel {
    private ArrayList<Question> questions = new ArrayList<Question>();
    private Question question;
    private int numberOfQuestions;


    /**
     * The number generator for the game
     */
    private Random random = new Random();

    /**
     * Number of questions that are being answered correctly
     */
    private int score = 0;

    /**
     * Sets the question
     * @param question the question being set
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     * Gets the question
     * @return the question
     */
    public Question getQuestion() {
        return this.question;
    }

    /**
     * Gets the score of the game
     * @return the score achieved
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the number of questions
     * @return the number of questions
     */
    public int getNumberOfQuestions() {
        return this.numberOfQuestions;
    }

    /**
     * Sets the number of questions
     * @param numberOfQuestions the number of questions for the quiz
     */
    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    /**
     * Gets the questions from a list
     * @return the questions from the list
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Checks if the game has ended
     * @return true if the number of questions = 0, else false
     */
    public boolean gameEnded() {
        return getQuestions().size() == 0;
    }

    /**
     * Starts the game and gets a random list of questions for the provided game difficulty (number
     * of questions). Complete list of Questions passed from GameActivity.
     */
    public void startGame(List<Question> questions) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        this.score = 0;
        for(int i = 0; i < getNumberOfQuestions(); i++){
            int position = random.nextInt(questions.size());
            while (list.contains(position)){
                position = random.nextInt(questions.size());
            }
            list.add(position);
            this.questions.add(questions.get(position));
        }
    }

    /**
     * Sets the difficulty based on what difficulty the user chooses, setting the number of
     * questions to be higher if the difficulty is higher
     * @param difficulty the difficulty choses
     */
    public void setDifficulty(String difficulty) {
        if (difficulty.equals("Easy")) {
            setNumberOfQuestions(5);
        } else if (difficulty.equals("Medium")) {
            setNumberOfQuestions(7);
        } else {
            setNumberOfQuestions(10);
        }
    }

    /**
     * Updates the score of the game
     */
    public void updateScore(){
        this.score++;
    }

    /**
     * clears the list being used for game
     */
    public void clearList() {
        this.questions.clear();
    }
}

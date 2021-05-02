package edu.moravian.csci299.Trivia2000;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Question> completeList = Collections.emptyList();
    private List<Question> questions = Collections.emptyList();
    private TriviaGame triviaGame;
    private String difficulty;
    private String topic;
    private Random random = new Random();
    private TextView askedQuestion;
    private HighScore score;

    // Settings for being able to swipe
    private float x1, x2;
    static final int MIN_DISTANCE = 150;

    // The list of choices for thr question asked
    private final List<Integer> choiceIds = Arrays.asList(
            R.id.choice_1, R.id.choice_2, R.id.choice_3, R.id.choice_4);

    /**
     * Initializes the choice buttons for each question,  gets the intents for both the topic and
     * difficulty, as well as uses the observer to get the list of questions by a topic. Sets the
     * difficulty of the game and starts it, as well as updating the UI to show the question and
     * choices.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        triviaGame = new ViewModelProvider(this).get(TriviaGame.class);
        askedQuestion = findViewById(R.id.asked_question);

        for (int id : choiceIds) {
            findViewById(id).setOnClickListener(this);
        }

        this.difficulty = getIntent().getStringExtra("Game Difficulty");
        this.topic = getIntent().getStringExtra("Topic Type");

        LiveData<List<Question>> liveDataQuestions = QuizRepository.get().getQuestionsByTopic(this.topic);
        liveDataQuestions.observe(this, (questions) -> {
            this.completeList = questions;
            triviaGame.setDifficulty(this.difficulty);
            triviaGame.startGame(questions);
            updateUI();
        });
    }

    /**
     * @param v the View that was clicked
     */
    @Override
    public void onClick(View v) {
        moveButton();
        validate(v.getId(), triviaGame.getQuestion());
        if (triviaGame.gameEnded()) {
            triviaGame.clearList();
            Intent endGame = new Intent(this, GameEndActivity.class);
            endGame.putExtra("Game Topic", this.topic);
            endGame.putExtra("Game Score",  this.triviaGame.getScore());

            endGame.putExtra("Game Difficulty", this.difficulty);
            startActivity(endGame);
        }
        updateUI();
    }

    /**
     * Gets the random list of questions from Trivia Game to use. Ensures no repetition of questions
     * from the original Random List. Can skip and get a new question from the OnTouchEvent.
     * Randomly assigns the buttons with the text for the options for the given question.
     */
    private void updateUI() {
        ArrayList<String> list = new ArrayList<String>();

        this.questions = this.triviaGame.getQuestions();
        for (int i = 0; i < 1; i++) {
            if (triviaGame.getQuestions().size() == 0) {
                break;
            }
            this.askedQuestion.setText(questions.get(i).question);
            for (int id : choiceIds) {
                String value = questions.get(i).getRandomValue();
                while (list.contains(value)) {
                    value = questions.get(i).getRandomValue();
                }
                list.add(value);
                Button button = findViewById(id);
                button.setText(value);
            }
            triviaGame.setQuestion(questions.get(i));
            list.clear();
            triviaGame.getQuestions().remove(i);
        }
    }

    /**
     * Updates the users score if the choice that was selected is right
     *
     * @param id       id of the choice
     * @param question the question being asked
     */
    private void validate(int id, Question question) {
        Button button = findViewById(id);
        if (button.getText() == question.answer) {
            triviaGame.updateScore();
        };
    }

    /**
     * Animation method that moves the buttons off the screen when clicked (called in onClick)
     * and moves back on screen to get new set of answers for next question
     */
    private void moveButton() {
        for (int id : choiceIds) {
            Button im = (Button) findViewById(id);
            //set position TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta
            final Animation animation = new TranslateAnimation(0, -1200, 0, 0);
            final Animation animation2 = new TranslateAnimation(1200, 0, 0, 0);
            // set Animation for 1 sec
            animation.setDuration(1000);
            im.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                /**
                 * Filler Code. leave empty
                 * @param animation
                 */
                @Override
                public void onAnimationStart(Animation animation) {
                }

                /**
                 * Once First animation for the buttons ends taking them off screen, begin
                 * new animation to bring them back onto screen
                 * @param animation
                 */
                @Override
                public void onAnimationEnd(Animation animation) {
                    animation2.setDuration(1000);
                    im.startAnimation(animation2);
                }

                /**
                 * Filler Code. leave empty
                 * @param animation
                 */
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }

    /**
     * Swipe the Screen to skip the current question and get a new one. Can skip an infinite
     * amount of times
     *
     * @param event - the MotionEvent the screen detects from the user
     *
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    this.questions.add(completeList.get(random.nextInt(completeList.size())));
                    updateUI();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}

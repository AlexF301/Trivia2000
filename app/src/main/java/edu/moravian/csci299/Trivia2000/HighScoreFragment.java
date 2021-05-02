package edu.moravian.csci299.Trivia2000;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The high score fragment that is able to display the high scores of previous players, as well as
 * their names
 */

public class HighScoreFragment extends Fragment {

    public HighScoreFragment() {
        // Required empty public constructor
    }
    /**
     * the high scores in a sorted list
     */
    ArrayList<Integer> scoresSorted = new ArrayList<>();

    // fragment initialization parameters
    private static final String ARG_SCORES_ID = "scores_id";
    private static final String GAME_SCORE = "game_score";

    private int score;
    private List<HighScore> highScores;
    private HighScore highScore;
    private TextView names;
    private TextView scores;

    private final List<Integer> scoresTextViews = Arrays.asList(R.id.top_score, R.id.top_score_2,
            R.id.top_score_3, R.id.top_score_4, R.id.top_score_5);
    private final List<Integer> namesTextViews = Arrays.asList(R.id.top_user_name,
            R.id.top_user_name_2, R.id.top_user_name_3, R.id.top_user_name_4, R.id.top_user_name_5);

    /**
     * Use this factory method to create a new instance of this fragment that
     * show the details for the given event.
     *
     * @return a new instance of fragment HighScoreFragment
     */

    public static HighScoreFragment newInstance(String topic, int score) {
        HighScoreFragment fragment = new HighScoreFragment();
        Bundle args = new Bundle();
        args.putInt(GAME_SCORE, score);
        args.putString(ARG_SCORES_ID, topic);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param savedInstanceState
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getArguments().getString(ARG_SCORES_ID);
        QuizRepository.get().getHighScoreByTopic(id).observe(this, (scores) -> {
            this.highScores = scores;
            comparesScores();
            updatesUI();
        });
        sortHighScores();
        this.score = getArguments().getInt(GAME_SCORE);
    }

    /**
     * Create the view from the layout and saves references to all of the important
     * views within
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View base = inflater.inflate(R.layout.fragment_high_score, container, false);

        for (int id : scoresTextViews) {
            this.scores = base.findViewById(id);
        }
        for (int id : namesTextViews) {
            this.names = base.findViewById(id);
        }

        // Return the base view
        return base;
    }

    /**
     *
     */
    private void comparesScores() {
        if (this.highScores.isEmpty()) {
            this.scoresSorted.add(this.score);
        }
        for (int i = 0; i < this.highScores.size(); i++) {
            if (this.score > highScores.get(i).score) {
                highScores.set(i, highScore);
            }
        }
    }

    /**
     *
     */
    private void updatesUI() {
        for (int i = 0; i < this.highScores.size(); i++) {
            names.setText(highScores.get(i).player_name);
            scores.setText(highScores.get(i).score);
        }
    }

    /**
     *
     */
    private void sortHighScores() {
        for (int i = 0; i < scoresSorted.size(); i++) {
            scoresSorted.add(highScores.get(i).score);
            Collections.sort(scoresSorted);
        }
    }
}

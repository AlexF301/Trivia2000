package edu.moravian.csci299.Trivia2000;

import android.app.Application;

/**
 * allows access to lifecycle information about the application.
 * Created upon launch of application through android manifests
 */
public class QuizApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the repository with this application as the context
        QuizRepository.initialize(this);
    }
}

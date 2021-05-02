package edu.moravian.csci299.Trivia2000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
* Class For Menu to be accessible to all activities
*/
public class MenuActivity extends AppCompatActivity {
    private Switch musicSwitch;
    private MediaPlayer mediaPlayer;
    private SharedPreferences preferences;

    /**
     * Gets the game preferences through shared preferences, as well as creating the media player
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        preferences = getSharedPreferences("game preferences",Context.MODE_PRIVATE);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.violin);

    }

    /**
     * Inflates the options menu items (switch for the music).
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuSwitch = menu.findItem(R.id.app_bar_switch);
        menuSwitch.setActionView(R.layout.switch_layout);
        musicSwitch = (Switch) menu.findItem(R.id.app_bar_switch).getActionView().findViewById(R.id.switchForActionBar);
        musicSwitch.setChecked(preferences.getBoolean("isPlaying", true));
        updatePreferences();
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * if music switch is flipped on or off, updates preferences for music setting (On or
             * Off).
             * @param buttonView
             * @param isChecked
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updatePreferences();
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Saves the users music preferences
     */
    private void updatePreferences() {
        SharedPreferences.Editor editor = preferences.edit();
        //saves music setting
        if (musicSwitch.isChecked()) {
            mediaPlayer.start();
            editor.putBoolean("isPlaying", true);
        } else {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
            editor.putBoolean("isPlaying", false);
        }
        editor.apply();
    }
}
package com.game.jason.chardeemacdennis20170417;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class WinActivity extends AppCompatActivity {

    private int blueScore;
    private int redScore;
    private ArrayList mindDeck;
    private ArrayList bodyDeck;
    private ArrayList spiritDeck;
    private String teamThatsUp;
    private int playerNumber;
    private String enabledDeck;
    public Boolean continueMusic = true;
    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        //----   hide status bar   ----//
        View decorView = getWindow().getDecorView();
        int uiOptions =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        setUpContext();

        if(redScore > blueScore) {
            TextView messageBox = (TextView) findViewById(R.id.winner_message);
            messageBox.setText("Congratulations Red Team! You have crushed your opponents. Now, it's time to crush them REALLY. Proceed to destroy your opponents idols.");
            TextView winTV = (TextView) findViewById(R.id.team_wins_TV);
            winTV.setText("Red Team Wins!");
        }
    }

    public void setUpContext() {
        mindDeck = this.getIntent().getExtras().getParcelableArrayList("mindDeck");
        bodyDeck = this.getIntent().getExtras().getParcelableArrayList("bodyDeck");
        spiritDeck = this.getIntent().getExtras().getParcelableArrayList("spiritDeck");
        blueScore = this.getIntent().getIntExtra("blueScore",0);
        redScore = this.getIntent().getIntExtra("redScore",0);
        teamThatsUp = this.getIntent().getStringExtra("teamThatsUp");
        playerNumber = this.getIntent().getIntExtra("playerNumber",0);
        enabledDeck = "mindDeck";
    }

    public void onMenuClick(View view) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(WinActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_menu_deck,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void onShowRulesClick(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(WinActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_rules,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void onMainScreenClick(View view) {
        Intent goToMain = new Intent(this,MainActivity.class);
        setUpToGoToNextActivity(goToMain);
        startActivity(goToMain);
    }

    /*-----     Volume Control start     -----*/
    @Override
    protected void onPause() {
        super.onPause();
        if (!continueMusic) {
            MusicManager.pause();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        continueMusic = this.getIntent().getBooleanExtra("continueMusic",false);
        if(!continueMusic) {
            MusicManager.start(this, MusicManager.MUSIC_MENU);
        } else {
            FloatingActionButton flashButtonOn = (FloatingActionButton) findViewById(R.id.winVolumeToggle);
            flashButtonOn.setImageResource(R.drawable.volume_off);
        }
    }
    public void onSoundToggleClick(View view) {
        if(!continueMusic) {
            MusicManager.pause();
            FloatingActionButton flashButtonOn = (FloatingActionButton) findViewById(R.id.winVolumeToggle);
            flashButtonOn.setImageResource(R.drawable.volume_off);
            continueMusic = true;
        } else {
            MusicManager.start(this, MusicManager.MUSIC_MENU);
            FloatingActionButton flashButtonOn = (FloatingActionButton) findViewById(R.id.winVolumeToggle);
            flashButtonOn.setImageResource(R.drawable.volume_on);
            continueMusic = false;
        }
    }
    /*-----     Volume Control end     -----*/


    public void setUpToGoToNextActivity(Intent intent){
        //----   Setup for going to next activity   ----//
        intent.putExtra("teamThatsUp", teamThatsUp);
        intent.putExtra("mindDeck", mindDeck);
        intent.putExtra("bodyDeck", bodyDeck);
        intent.putExtra("spiritDeck", spiritDeck);
        intent.putExtra("playerNumber",playerNumber);
        intent.putExtra("deckType", enabledDeck);
        intent.putExtra("blueScore", blueScore);
        intent.putExtra("redScore", redScore);
        intent.putExtra("continueMusic", continueMusic);
    }

    public void onWinMessageRemoveClick(View view) {
        RelativeLayout winMessageRL = (RelativeLayout) findViewById(R.id.win_message_RL);
        winMessageRL.setVisibility(View.INVISIBLE);
        winMessageRL = null;
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    /**
     * Release memory when the UI becomes hidden or when system resources become low.
     * @param level the memory-related event that was raised.
     */
    public void onTrimMemory ( int level){

        // Determine which lifecycle or system event was raised.
        switch (level) {

            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:

            /*
               Release any UI objects that currently hold memory.

               The user interface has moved to the background.
            */

                break;

            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:

            /*
               Release any memory that your app doesn't need to run.

               The device is running low on memory while the app is running.
               The event raised indicates the severity of the memory-related event.
               If the event is TRIM_MEMORY_RUNNING_CRITICAL, then the system will
               begin killing background processes.
            */

                break;

            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:

            /*
               Release as much memory as the process can.

               The app is on the LRU list and the system is running low on memory.
               The event raised indicates where the app sits within the LRU list.
               If the event is TRIM_MEMORY_COMPLETE, the process will be one of
               the first to be terminated.
            */

                break;

            default:
            /*
              Release any non-critical data structures.

              The app received an unrecognized memory level value
              from the system. Treat this as a generic low-memory message.
            */
                break;
        }
    }
}

package com.game.jason.chardeemacdennis20170417;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.os.Environment.getExternalStoragePublicDirectory;


public class DeckActivity extends AppCompatActivity {

    private int blueScore;
    private int redScore;
    private ArrayList mindDeck;
    private ArrayList bodyDeck;
    private ArrayList spiritDeck;
    private String teamThatsUp;
    private float disabledOpaque = .35f;
    private int teamScore;
    private int playerNumber;
    private String enabledDeck;
    private Intent goToCards;
    public Boolean continueMusic;
    private Boolean gameHasStarted;
    private ImageButton mindDeckButton;
    private ImageButton bodyDeckButton;
    private ImageButton spiritDeckButton;
    private String timeOutSelected = "none";
    private TextView timeoutCounter;
    private int blueTimeOuts;
    private int redTimeOuts;
    private boolean blueTimeOutTriggered = false;
    private boolean redTimeOutTriggered = false;
//    static final int REQUEST_IMAGE_CAPTURE = 1;
//    private static final int REQUEST_CAMERA = 1;
//    private static final int SELECT_FILE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

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
        setUpScoreBoard();

        mindDeckButton = (ImageButton) findViewById(R.id.mind_deck);
        bodyDeckButton = (ImageButton) findViewById(R.id.body_deck);
        spiritDeckButton = (ImageButton) findViewById(R.id.spirit_deck);

        mindDeckButton.setAlpha(disabledOpaque);
        bodyDeckButton.setAlpha(disabledOpaque);
        spiritDeckButton.setAlpha(disabledOpaque);

        if (teamScore < playerNumber+1) {
            enabledDeck = "mindDeck";
            mindDeckButton.setAlpha(1f);
        } else if (teamScore < playerNumber*2+4) {
            enabledDeck = "bodyDeck";
            bodyDeckButton.setAlpha(1f);
        } else if(teamScore >=  playerNumber*3+6 || spiritDeck.size()==0) {
            Intent goToWin = new Intent(this, WinActivity.class);
            setUpToGoToNextActivity(goToWin);
            startActivity(goToWin);
            finish();
        } else if (teamScore <  playerNumber*3+6 ) {
            enabledDeck = "spiritDeck";
            spiritDeckButton.setAlpha(1f);
        }

        if(!gameHasStarted) {
            RelativeLayout turnExpRL = (RelativeLayout) findViewById(R.id.turn_exp_RL);
            turnExpRL.setVisibility(View.VISIBLE);
        }

    }

    public void setUpContext() {
        goToCards = new Intent(this,CardActivity.class);
        mindDeck = this.getIntent().getExtras().getParcelableArrayList("mindDeck");
        bodyDeck = this.getIntent().getExtras().getParcelableArrayList("bodyDeck");
        spiritDeck = this.getIntent().getExtras().getParcelableArrayList("spiritDeck");
        blueScore = this.getIntent().getIntExtra("blueScore",0);
        redScore = this.getIntent().getIntExtra("redScore",0);
        blueTimeOuts = this.getIntent().getIntExtra("blueTimeOuts",2);
        redTimeOuts = this.getIntent().getIntExtra("redTimeOuts",2);
        teamThatsUp = this.getIntent().getStringExtra("teamThatsUp");
        playerNumber = this.getIntent().getIntExtra("playerNumber",4);
        gameHasStarted = this.getIntent().getBooleanExtra("gameHasStarted", false);
        enabledDeck = "mindDeck";
    }

    private void setUpScoreBoard() {
        FrameLayout blueScoreLayout = (FrameLayout) findViewById(R.id.blue_team_score_board_out);
        TextView blueScoreView = (TextView) findViewById(R.id.blue_team_score_TV);
        blueScoreView.setText(blueScore+"");

        FrameLayout redScoreLayout = (FrameLayout) findViewById(R.id.red_team_score_board_out);
        TextView redScoreView = (TextView) findViewById(R.id.red_team_score_TV);
        redScoreView.setText(redScore+"");

        //---  Enable button based on score logic   ---//
        if(teamThatsUp == null) {
            teamThatsUp = "blue";
            teamScore = blueScore;
            redScoreLayout.setAlpha(disabledOpaque);
        } else if (teamThatsUp.equals("blue")) {
            teamThatsUp = "red";
            teamScore = redScore;
            blueScoreLayout.setAlpha(disabledOpaque);
        } else {
            teamThatsUp = "blue";
            teamScore = blueScore;
            redScoreLayout.setAlpha(disabledOpaque);
        }

        /*---  TimeOuts  ---*/
        FloatingActionButton blueTimeOut1 = (FloatingActionButton) findViewById(R.id.blue_time_out_1);
        FloatingActionButton blueTimeOut2 = (FloatingActionButton) findViewById(R.id.blue_time_out_2);
        FloatingActionButton redTimeOut1 = (FloatingActionButton) findViewById(R.id.red_time_out_1);
        FloatingActionButton redTimeOut2 = (FloatingActionButton) findViewById(R.id.red_time_out_2);
        if(blueTimeOuts <= 1) {
            blueTimeOut1.setVisibility(View.GONE);
        }
        if (blueTimeOuts <= 0) {
            blueTimeOut2.setVisibility(View.GONE);
        }
        if(redTimeOuts <= 1) {
            redTimeOut1.setVisibility(View.GONE);
        }
        if (redTimeOuts <= 0) {
            redTimeOut2.setVisibility(View.GONE);
        }
    }

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
        intent.putExtra("blueTimeOuts", blueTimeOuts);
        intent.putExtra("redTimeOuts", redTimeOuts);
        intent.putExtra("continueMusic", continueMusic);
        intent.putExtra("gameHasStarted",gameHasStarted);
    }

    public void onMindDeckClick(View view) {
        setUpToGoToNextActivity(goToCards);

        if(enabledDeck.equals("mindDeck")) {
            startActivity(goToCards);
            finish();
        } else {
            Toast.makeText(DeckActivity.this,
                    "You are no longer in level 1: Mind",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onBodyDeckClick(View view) {
        setUpToGoToNextActivity(goToCards);

        if(enabledDeck.equals("bodyDeck")) {
            startActivity(goToCards);
            finish();
        } else if (enabledDeck.equals("mindDeck")){
            Toast.makeText(DeckActivity.this,
                    "Your team needs " + (playerNumber+1) +
                    " cards for the BODY DECK",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(DeckActivity.this,
                    "You are no longer in level 2: Mind",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onSpiritDeckClick(View view) {
        setUpToGoToNextActivity(goToCards);

        if(enabledDeck.equals("spiritDeck")) {
            startActivity(goToCards);
            finish();
        } else {
            Toast.makeText(DeckActivity.this,
                    "Your team needs " + (playerNumber*2+4) +
                            " cards for the SPIRIT DECK",
                    Toast.LENGTH_LONG).show();
        }
    }
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
        continueMusic = this.getIntent().getBooleanExtra("continueMusic",true);
        if(!continueMusic) {
            MusicManager.start(this, MusicManager.MUSIC_MENU);
        } else {
            FloatingActionButton flashButtonOn = (FloatingActionButton) findViewById(R.id.deckVolumeToggle);
            flashButtonOn.setImageResource(R.drawable.volume_off);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void onSoundToggleClick(View view) {
        if(!continueMusic) {
            MusicManager.pause();
            FloatingActionButton flashButtonOn = (FloatingActionButton) findViewById(R.id.deckVolumeToggle);
            flashButtonOn.setImageResource(R.drawable.volume_off);
            continueMusic = true;
        } else {
            MusicManager.start(this, MusicManager.MUSIC_MENU);
            FloatingActionButton flashButtonOn = (FloatingActionButton) findViewById(R.id.deckVolumeToggle);
            flashButtonOn.setImageResource(R.drawable.volume_on);
            continueMusic = false;
        }
    }

    public void onMenuClick(View view) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DeckActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_menu_deck,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void onShowRulesClick(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DeckActivity
                .this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_rules,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void onMainScreenClick(View view) {
        Intent goToMain = new Intent(this,MainActivity.class);
        setUpToGoToNextActivity(goToMain);
        startActivity(goToMain);
        finish();
    }

    public void onTurnExpClick(View view) {
        RelativeLayout turnExpRL = (RelativeLayout) findViewById(R.id.turn_exp_RL);
        turnExpRL.setVisibility(View.INVISIBLE);
    }

//    public void onAutoWinClick(View view) {
//        Intent goToWin = new Intent(this, WinActivity.class);
//        setUpToGoToNextActivity(goToWin);
//        startActivity(goToWin);
//finish();
//    }

    // Other activity code ...

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

    public void onTimeOutClickBlue1(View view) {
        timeOutSelected = "BLUE1";
        timeOutAlert();
    }

    public void onTimeOutClickBlue2(View view) {
        timeOutSelected = "BLUE2";
        timeOutAlert();
    }

    public void onTimeOutClickRed1(View view) {
        timeOutSelected = "RED1";
        timeOutAlert();
    }

    public void onTimeOutClickRed2(View view) {
        timeOutSelected = "RED2";
        timeOutAlert();
    }

    public void timeOutAlert () {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DeckActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.time_out,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void onTimeOutClick(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DeckActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.time_out_countdown,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        timeoutCounter = (TextView) dialog.findViewById(R.id.timeout_timer);
        startTimer();
    }

    public void startTimer() {
        TimeOutTimer timeOutTimer = new TimeOutTimer(120000, 1000);

        switch (timeOutSelected) {
            case "BLUE1":
                if(blueTimeOutTriggered) {
                    timeoutCounter.setText(R.string.time_out_used);
                    break;
                }
                blueTimeOutTriggered= true;
                FloatingActionButton blueTimeout1 = (FloatingActionButton) findViewById(R.id.blue_time_out_1);
                blueTimeout1.setVisibility(View.GONE);
                blueTimeOuts--;
                timeOutTimer.start();
                break;
            case "BLUE2":
                if(blueTimeOutTriggered) {
                    timeoutCounter.setText(R.string.time_out_used);
                    break;
                }
                blueTimeOutTriggered= true;
                FloatingActionButton blueTimeout2 = (FloatingActionButton) findViewById(R.id.blue_time_out_2);
                blueTimeout2.setVisibility(View.GONE);
                timeOutTimer.start();
                blueTimeOuts--;
                break;
            case "RED1":
                if(redTimeOutTriggered) {
                    timeoutCounter.setText(R.string.time_out_used);
                    break;
                }
                redTimeOutTriggered= true;
                FloatingActionButton redTimeout1 = (FloatingActionButton) findViewById(R.id.red_time_out_1);
                redTimeout1.setVisibility(View.GONE);
                timeOutTimer.start();
                redTimeOuts--;
                break;
            case "RED2":
                if(redTimeOutTriggered) {
                    timeoutCounter.setText(R.string.time_out_used);
                    break;
                }
                redTimeOutTriggered= true;
                FloatingActionButton redTimeout2 = (FloatingActionButton) findViewById(R.id.red_time_out_2);
                redTimeout2.setVisibility(View.GONE);
                timeOutTimer.start();
                redTimeOuts--;
                break;
            default:
                break;
        };


    }

    private class TimeOutTimer extends CountDownTimer {
        private TimeOutTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;
            long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
            timeoutCounter.setText("time remaining: " + minutes + ":" + seconds);
        }

        @Override
        public void onFinish() {
            timeoutCounter.setText("TIME'S UP!");
        }
    }
}


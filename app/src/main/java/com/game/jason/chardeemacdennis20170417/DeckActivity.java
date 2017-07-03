package com.game.jason.chardeemacdennis20170417;

import android.content.Intent;
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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;


public class DeckActivity extends AppCompatActivity {

    int blueScore;
    int redScore;
    ArrayList mindDeck;
    ArrayList bodyDeck;
    ArrayList spiritDeck;
    String teamThatsUp;
    float disabledOpaque = .35f;
    int teamScore;
    int playerNumber;
    String enabledDeck;
    Intent goToCards;
    public Boolean continueMusic;
    Boolean gameHasStarted;
    ImageButton mindDeckButton;
    ImageButton bodyDeckButton;
    ImageButton spiritDeckButton;
//    static final int REQUEST_IMAGE_CAPTURE = 1;
//    private static final int REQUEST_CAMERA = 1;
//    private static final int SELECT_FILE = 2;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);

        //----   load ads   ----//
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //----   hide status bar   ----//
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
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
/*        ImageButton teamThatsUpImage = (ImageButton) findViewById(R.id.team_that_is_up_image);*/

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
}


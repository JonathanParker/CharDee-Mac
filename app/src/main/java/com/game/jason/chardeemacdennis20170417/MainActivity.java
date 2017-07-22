package com.game.jason.chardeemacdennis20170417;

import android.content.ComponentCallbacks2;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity{
      /*  implements ComponentCallbacks2*/


    private ArrayList<Data> mindDeck;
    private ArrayList<Data> bodyDeck;
    private ArrayList<Data> spiritDeck;
    private Integer blueScore;
    private Integer redScore;
    private NumberPicker playerNumberEdit;
    private String playerNumberValue;
    public Boolean continueMusic = true;
    private EditText previewTitle;
    private EditText previewDescription;
    private EditText previewImageURL;
    private RadioGroup previewDeckTypeRGroup;
    public Boolean expressGame;
    private String previewTitleString;
    private String previewDescriptionString;
    private String previewImageURLString;
    private String previewDeckTypeString;
    private Boolean exit = false;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----   hide status bar   ----//
        View decorView = getWindow().getDecorView();
        int uiOptions =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        NumberPicker picker = (NumberPicker) findViewById(R.id.player_NP);
        picker.setMinValue(3);
        picker.setMaxValue(13);
        picker.setValue(5);
    }


    /*-----     Setup Decks start     -----*/
    private void setupMindDeck() {
        if(mindDeck == null) {
            mindDeck = new ArrayList<>();
        }
        final String[] title = getResources().getStringArray(R.array.mind_deck_title);
        final String[] imageUrl = getResources().getStringArray(R.array.mind_deck_image_url);
        final String[] description = getResources().getStringArray(R.array.mind_deck_description);
        final String[] supplies = getResources().getStringArray(R.array.mind_deck_supplies);
        for(int i=0; i<imageUrl.length ; i++) {
            if(expressGame && supplies[i].isEmpty()) {
                mindDeck.add(new Data(imageUrl[i], description[i], title[i]));
            } else if(!expressGame){
                mindDeck.add(new Data(imageUrl[i], description[i], title[i]));
            }
        }
    }
    private void setupBodyDeck() {
        if (bodyDeck == null) {
            bodyDeck = new ArrayList<>();
        }
        final String[] title = getResources().getStringArray(R.array.body_deck_title);
        final String[] imageUrl = getResources().getStringArray(R.array.body_deck_image_url);
        final String[] description = getResources().getStringArray(R.array.body_deck_description);
        final String[] supplies = getResources().getStringArray(R.array.body_deck_supplies);
        for (int i = 0; i < imageUrl.length; i++) {
            if (expressGame && supplies[i].isEmpty()) {
                bodyDeck.add(new Data(imageUrl[i], description[i], title[i]));
            } else if (!expressGame) {
                bodyDeck.add(new Data(imageUrl[i], description[i], title[i]));
            }
        }
    }

    private void setupSpiritDeck() {
        if(spiritDeck == null) {
            spiritDeck = new ArrayList<>();
        }
        final String[] title = getResources().getStringArray(R.array.spirit_deck_title);
        final String[] imageUrl = getResources().getStringArray(R.array.spirit_deck_image_url);
        final String[] description = getResources().getStringArray(R.array.spirit_deck_description);
        final String[] supplies = getResources().getStringArray(R.array.spirit_deck_supplies);
        for(int i=0; i<imageUrl.length ; i++) {
            if(expressGame && supplies[i].isEmpty()) {
                spiritDeck.add(new Data(imageUrl[i], description[i], title[i]));
            } else if(!expressGame){
                spiritDeck.add(new Data(imageUrl[i], description[i], title[i]));
            }
        }

    }
/*-----     Setup Decks end     -----*/

    /*-----     Start Game Buttons start     -----*/
    /* go to pre-game setup */
    public void onStartSetupFullClick(View view) {
        playerNumberEdit = (NumberPicker) findViewById(R.id.player_NP);
        playerNumberValue = String.valueOf(playerNumberEdit.getValue());
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_full_setup,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        Toast.makeText(MainActivity.this,
                "Once the games starts, there will be on bathroom breaks unless a time out is used. See rules for more detail.",
                Toast.LENGTH_LONG).show();
        expressGame = false;
    }
    /* go to tldr rules */
    public void onTldrRulesClick(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_rules_tldr,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
    public void onSetupLiteClick(View view) {

        playerNumberEdit = (NumberPicker) findViewById(R.id.player_NP);
        playerNumberValue = String.valueOf(playerNumberEdit.getValue());
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_lite_setup,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        Toast.makeText(MainActivity.this,
                "Use the restroom before starting!",
                Toast.LENGTH_LONG).show();
        expressGame = true;
    }
    /* go to deck activity */
    public void onStartGameClick(View view) {

        setupMindDeck();
        setupBodyDeck();
        setupSpiritDeck();
        blueScore = 0;
        redScore = 0;

        Intent startGame = new Intent(this, DeckActivity.class);
        final int result = 1;
        final int playerNumber = Integer.parseInt(playerNumberValue) / 2;
        Collections.shuffle(mindDeck);
        Collections.shuffle(bodyDeck);
        Collections.shuffle(spiritDeck);
        startGame.putExtra("playerNumber", playerNumber);
        startGame.putExtra("mindDeck", mindDeck);
        startGame.putExtra("bodyDeck", bodyDeck);
        startGame.putExtra("spiritDeck", spiritDeck);
        startGame.putExtra("blueScore", blueScore);
        startGame.putExtra("redScore", redScore);
        startGame.putExtra("continueMusic", continueMusic);

        startActivityForResult(startGame, result);
        finish();
    }
/*-----     Start Game Buttons end     -----*/


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
            MusicManager.pause();
            FloatingActionButton flashButtonOn = (FloatingActionButton) findViewById(R.id.mainVolumeToggle);
            flashButtonOn.setImageResource(R.drawable.volume_off);
        }
    }
    public void onSoundToggleClick(View view) {
        if(!continueMusic) {
            MusicManager.pause();
            FloatingActionButton flashButtonOn = (FloatingActionButton) findViewById(R.id.mainVolumeToggle);
            flashButtonOn.setImageResource(R.drawable.volume_off);
            continueMusic = true;
        } else {
            MusicManager.start(this, MusicManager.MUSIC_MENU);
            FloatingActionButton flashButtonOn = (FloatingActionButton) findViewById(R.id.mainVolumeToggle);
            flashButtonOn.setImageResource(R.drawable.volume_on);
            continueMusic = false;
        }
    }
    /*-----     Volume Control end     -----*/


    /*-----     Menu functionality start     -----*/
    public void onMenuClick(View view) {

        /*Dialog pop-up of menu*/
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_menu,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
    public void onMakeCardClick(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_make_own_card,null);
        previewTitle = (EditText) mView.findViewById(R.id.titleEditText);
        previewDescription = (EditText) mView.findViewById(R.id.descriptionEditText);
        previewImageURL = (EditText) mView.findViewById(R.id.imageURLEditText);
        previewDeckTypeRGroup = (RadioGroup) mView.findViewById(R.id.deckTypeRGrp);
        //--- gathering deck type ---//
        int selectedId = previewDeckTypeRGroup.getCheckedRadioButtonId();
        View deckTypeRBtnView = previewDeckTypeRGroup.findViewById(selectedId);
        int deckTypeRBtnId = previewDeckTypeRGroup.indexOfChild(deckTypeRBtnView);
        RadioButton deckTypeRBtn = (RadioButton) previewDeckTypeRGroup.getChildAt(deckTypeRBtnId);
        previewDeckTypeString = deckTypeRBtn.getText().toString().toLowerCase();
        /*--- gathering deck type end ---*/
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    } /*DISABLE BEFORE SHIP*/
    public void onPreviewClick(View view) {

        previewTitleString = previewTitle.getText().toString();
        previewDescriptionString = previewDescription.getText().toString();
        previewImageURLString = previewImageURL.getText().toString();
        if( previewTitleString.isEmpty() || previewDescriptionString.isEmpty() ){
            Toast.makeText(MainActivity.this,
                    "title, previewDescription, and deck type are required.",
                    Toast.LENGTH_LONG).show();
        } else {
            ///--- fill the preview card ---///
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.preview_card,null);


            TextView mPreviewCardText = (TextView) mView.findViewById(R.id.previewCardText);
            mPreviewCardText.setText(previewTitleString);

            TextView mPreviewTitleText= (TextView) mView.findViewById(R.id.previewTitleText);
            mPreviewTitleText.setText(previewDescriptionString);

            ImageView mPreviewCardImage = (ImageView) mView.findViewById(R.id.previewCardImage);
            new DownloadImageTask(mPreviewCardImage).execute(previewImageURLString);

            mBuilder.setView(mView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();
            dialog.getWindow().setLayout(1800, 1000); //Controlling width and height.
        }
    }
    public void onKeepClick(View view) {
        /* Non-essential: not finished */
        previewTitleString = previewTitle.getText().toString();
        previewDescriptionString = previewDescription.getText().toString();
        previewImageURLString = previewImageURL.getText().toString();
        if( previewTitleString.isEmpty() || previewDescriptionString.isEmpty() ){
            Toast.makeText(MainActivity.this,
                    "title, previewDescription, and deck type are required.",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this,
                    "card added",
                    Toast.LENGTH_LONG).show();
        }
        if (previewDeckTypeString.equals("mind")) {
            mindDeck = new ArrayList<>();
            mindDeck.add(0, new Data(previewImageURLString, previewDescriptionString, previewTitleString));
        } else if (previewDeckTypeString.equals("body")) {
            bodyDeck = new ArrayList<>();
            bodyDeck.add(0, new Data(previewImageURLString, previewDescriptionString, previewTitleString));
        } else {
            spiritDeck = new ArrayList<>();
            spiritDeck.add(0, new Data(previewImageURLString, previewDescriptionString, previewTitleString));
        }

    }
    public void onShowRulesClick(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_rules,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
    /*-----     Menu functionality end     -----*/

    /*-----     Info Menu functionality end     -----*/
    public void onExpressInfoClick(View view) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.info_express_game,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
    public void onFullInfoClick(View view) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.info_full_game,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void onPickJudgeClick(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_pick_judge,null);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
    public void onShowCreditClick(View view) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);

        builderSingle.setTitle("Credits");
        final String[] mindAttribute = getResources().getStringArray(R.array.mind_deck_attribute);
        final String[] mindTitle = getResources().getStringArray(R.array.mind_deck_title);
        final String[] bodyAttribute = getResources().getStringArray(R.array.body_deck_attribute);
        final String[] bodyTitle = getResources().getStringArray(R.array.body_deck_title);
        final String[] spiritAttribute = getResources().getStringArray(R.array.spirit_deck_attribute);
        final String[] spiritTitle = getResources().getStringArray(R.array.spirit_deck_title);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
        arrayAdapter.add( "Inspired by It's “Always Sunny in Philadelphia”" );
        arrayAdapter.add( "Developer: Jason Hu" );
        arrayAdapter.add( "Images" );

        for(int i=0; i<mindAttribute.length; i++) {
            if(!mindAttribute[i].isEmpty()) {
                arrayAdapter.add( "   " + mindTitle[i] + ": " + mindAttribute[i] );
            }
        }

        for(int i=0; i<bodyAttribute.length; i++) {
            if(!bodyAttribute[i].isEmpty()) {
                arrayAdapter.add( "   " + bodyTitle[i] + ": " + bodyAttribute[i] );
            }
        }

        for(int i=0; i<spiritAttribute.length; i++) {
            if(!spiritAttribute[i].isEmpty()) {
                arrayAdapter.add( "   " + spiritTitle[i] + ": " + spiritAttribute[i] );
            }
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builderSingle.show();

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        private DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
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
}

package com.game.jason.chardeemacdennis20170417;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/*use for adding card add functionality*/
//import android.widget.EditText;
//import android.widget.Toast;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.support.v7.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class CardActivity extends AppCompatActivity {

    private static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private SwipeFlingAdapterView flingContainer;
    private ArrayList deck;
    int blueScore;
    int redScore;
    ArrayList mindDeck;
    ArrayList bodyDeck;
    ArrayList spiritDeck;
    String teamThatsUp;
    int playerNumber;
    int teamScore;
    Boolean gameHasStarted;
    String enabledDeck;
    private Intent goToDeck;
    private  Intent goToMain;
    public Boolean continueMusic = true;
    private Boolean exit = false;
    /*use for adding card add functionality*/
//    EditText title;
//    EditText description;
//    EditText imageURL;
//    RadioGroup deckTypeRGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        //----   hide status bar   ----//
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

        decorView.setSystemUiVisibility(uiOptions);

        setVariables();

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        myAppAdapter = new MyAppAdapter(deck, CardActivity.this);
        flingContainer.setAdapter(myAppAdapter);


        //----   Initialize data for moving to next screen   ----//
        goToDeck = new Intent(CardActivity.this, DeckActivity.class);
        goToMain = new Intent(CardActivity.this, MainActivity.class);
        //----   Initialize data for moving to next screen (end)   ----//

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                onBluePlusOne();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                redPlusOne();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });

        if(!gameHasStarted) {
            RelativeLayout turnExpRL = (RelativeLayout) findViewById(R.id.try_swiping_container_RL);
            turnExpRL.setVisibility(View.VISIBLE);
        }

        gameHasStarted = true;
    }

    public void setVariables() {
        enabledDeck = this.getIntent().getExtras().getString("deckType");
        deck = this.getIntent().getExtras().getParcelableArrayList(enabledDeck);
        mindDeck = this.getIntent().getExtras().getParcelableArrayList("mindDeck");
        bodyDeck = this.getIntent().getExtras().getParcelableArrayList("bodyDeck");
        spiritDeck = this.getIntent().getExtras().getParcelableArrayList("spiritDeck");
        teamThatsUp = this.getIntent().getStringExtra("teamThatsUp");
        playerNumber = this.getIntent().getIntExtra("playerNumber",0);
        blueScore = getIntent().getIntExtra("blueScore",0);
        redScore = getIntent().getIntExtra("redScore",0);
        gameHasStarted = getIntent().getBooleanExtra("gameHasStarted", false);
    }
    public void onAutoAdvanceClick(View view) {

        if(enabledDeck.equals("mindDeck")) {
            teamScore =  playerNumber+1;
        } else if (enabledDeck.equals("bodyDeck")) {
            teamScore =  playerNumber*2+4;
        }

        if(teamThatsUp.equals("red")) {
            redScore = teamScore;
        } else {
            blueScore = teamScore;
        }

        setUpToGoToNextActivity(goToDeck);
        startActivity(goToDeck);
        finish();
    }

    public void onBluePlusOneClick(View view) {
        onBluePlusOne();
    }
    public void onBluePlusOne() {

        deck.remove(0);
        //---------   go back to deck   ---------//
        blueScore++;
        setUpToGoToNextActivity(goToDeck);
        startActivity(goToDeck);
        finish();
    }
    public void onRedPlusOneClick(View view) {
        redPlusOne();
    }

    public void redPlusOne(){
        deck.remove(0);
        //---------   go back to deck   ---------//
        redScore++;
        setUpToGoToNextActivity(goToDeck);
        startActivity(goToDeck);
        finish();
    }

    public void onNoPointClick(View view) {
        if(teamThatsUp.equals("preview")) {
            setUpToGoToNextActivity(goToMain);
            startActivity(goToMain);
            finish();
        } else {
            deck.remove(0);
            //---------   go back to deck   ---------//
            setUpToGoToNextActivity(goToDeck);
            startActivity(goToDeck);
            finish();
        }
    }

    public void onSwipingInfoRemoveClick(View view) {
        RelativeLayout turnExpRL = (RelativeLayout) findViewById(R.id.try_swiping_container_RL);
        turnExpRL.setVisibility(View.INVISIBLE);
    }

    private static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public TextView titleText;
        public ImageView cardImage;
        public FloatingActionButton trashButton;
        public FloatingActionButton keepButton;
        public FloatingActionButton removeButton;
    }

    public class MyAppAdapter extends BaseAdapter {

        public List<Data> parkingList;
        public Context context;

        private MyAppAdapter(List<Data> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;

            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item_card, parent, false);
                // configure view holder

                viewHolder = new ViewHolder();
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.cardText);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.titleText = (TextView) rowView.findViewById(R.id.titleText);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                viewHolder.trashButton = (FloatingActionButton) rowView.findViewById(R.id.redTeamPlusFab);
                viewHolder.keepButton = (FloatingActionButton) rowView.findViewById(R.id.blueTeamPlusFab);
                viewHolder.removeButton = (FloatingActionButton) rowView.findViewById(R.id.removeFab);

                if(teamThatsUp.equals("preview")) {
                    viewHolder.keepButton.setImageResource(R.mipmap.ic_add_white_24dp);
                    viewHolder.trashButton.setImageResource(R.mipmap.ic_clear_white_24dp);
                    viewHolder.removeButton.setEnabled(false);
                    viewHolder.removeButton.setVisibility(View.INVISIBLE);
                }

                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");
            viewHolder.titleText.setText(parkingList.get(position).getTitle() + "");
            Glide.with(CardActivity.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            return rowView;
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
        }
    }

    public void setUpToGoToNextActivity(Intent intent){
        //----   Setup for going to next activity   ----//
        intent.putExtra("teamThatsUp", teamThatsUp);
        intent.putExtra("mindDeck", mindDeck);
        intent.putExtra("bodyDeck", bodyDeck);
        intent.putExtra("spiritDeck", spiritDeck);
        intent.putExtra("playerNumber",playerNumber);
        intent.putExtra("deckType",enabledDeck);
        intent.putExtra(enabledDeck,deck);
        intent.putExtra("blueScore", blueScore);
        intent.putExtra("redScore", redScore);
        intent.putExtra("continueMusic", continueMusic);
        intent.putExtra("gameHasStarted", gameHasStarted);
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

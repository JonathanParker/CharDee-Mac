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
//        View decorView = getWindow().getDecorView();
//        int uiOptions =
//                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_IMMERSIVE
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

//        decorView.setSystemUiVisibility(uiOptions);

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
        if(bodyDeck == null) {
            bodyDeck = new ArrayList<>();
        }
        final String[] title = getResources().getStringArray(R.array.body_deck_title);
        final String[] imageUrl = getResources().getStringArray(R.array.body_deck_image_url);
        final String[] description = getResources().getStringArray(R.array.body_deck_description);
        final String[] supplies = getResources().getStringArray(R.array.body_deck_supplies);
        for(int i=0; i<imageUrl.length ; i++) {
            if(expressGame && supplies[i].isEmpty()) {
                bodyDeck.add(new Data(imageUrl[i], description[i], title[i]));
            } else if(!expressGame){
                bodyDeck.add(new Data(imageUrl[i], description[i], title[i]));
            }
        }
/*        bodyDeck.add(new Data("https://c1.staticflickr.com/3/2949/34289507595_43f418401a_z.jpg"*//*CC0 no attribution*//*,
                "Card drawer and opponent of their choosing must fit as many grapes in their mouth using only their faces in ten seconds. Player with most grapes in their mouth wins card for their team.",
                "Grape gobble"));
        bodyDeck.add(new Data("https://c1.staticflickr.com/9/8612/16677744421_9091f47bf5_k.jpg"*//*copyright: https://creativecommons.org/licenses/by-sa/2.0/, (attribution), https://www.flickr.com/photos/s13n1/16677744421*//*,
                "Opposing team member rips off 3 inches of duct tape on your body, their choice excluding areas above and including the neck. No flinching.",
                "Home Wax"));
                bodyDeck.add(new Data("https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Hot_Pocket_Pepperoni_Pizzeria.JPG/800px-Hot_Pocket_Pepperoni_Pizzeria.JPG"*//*https://commons.wikimedia.org/wiki/File:Hot_Pocket_Pepperoni_Pizzeria.JPG, share a-like & attribution, *//*,
                "A member of each team must eat a hot pocket fresh from the microwave. First to do so wins.",
                "Hot Pocket"));
                bodyDeck.add(new Data("http://maxpixel.freegreatpicture.com/static/photo/1x/Pepper-Jalapeno-Hot-Vegetable-Food-Spicy-Green-2053130.jpg"*//*link referral http://maxpixel.freegreatpicture.com/Pepper-Jalapeno-Hot-Vegetable-Food-Spicy-Green-2053130*//*,
                "Grab four or five of the hottest peppers. Cut these peppers into three or four good sized pieces. Put salt on peppers. Both teams elect a team member to participate. Both team members eat a piece, wait 30 seconds, and then eat another piece; repeat until someone bitches out. No beverages can be consumed during this time by either player. Choking/coughing loses. Team that eats final piece gets the card. Losing team must drink except for the team member who ate the peppers, who cannot eat/drink anything until their team wins another card.",
                "Hot Shit!"));
                bodyDeck.add(new Data("https://c1.staticflickr.com/3/2850/33448052694_8393c88ba2_z.jpg"*//*unsplash*//*,
                "Fill a large pot of water. Fill with ice. Stick your hand in it for 5 minutes. If player in the ice bath complains in any way about the temperature, the team loses the card.",
                "Ice Bath"));
                bodyDeck.add(new Data("https://c1.staticflickr.com/3/2843/33906000450_c9717dd2bb_z.jpg",
                "One member of each team stands on a chair over a teammate who lays on the ground. Pour a full beer into their mouth from there. Team that is least messy wins the card. Don’t be a bitch. ",
                "Long distance pour"));
                bodyDeck.add(new Data("https://upload.wikimedia.org/wikipedia/commons/0/02/Crushed_Chocolatemilk_can.jpg"*//*attribution*//*,
                "The player drawing this card must have every empty can consumed so far thrown at them without flinching or being a pussy from a distance of at least 15 feet. Head shots are not allowed, but the player being pelted may not cover his/her genitals. If the player does not flinch, he gets this card and the throwing team must finish his beer. Cans can be manipulated [crushed, folded, etc] for better aerodynamics/ more pain infliction.\n",
                "Look at dem cans"));
                bodyDeck.add(new Data("https://c1.staticflickr.com/5/4128/5000710747_39c62f3242_b.jpg"*//*https://www.flickr.com/photos/katerha/5000710747, attribution*//*,
                "A player from each team has 30 seconds to stuff as many marshmallows in their mouth as they can. The player with the most must then attempt to eat the marshmallows only using their mouth and without taking any out. If failure, the opposing team must attempt their marshmallow count. Do not reveal the number of marshmallows in the mouth until after the time has run out. Losing team must consume a marshmallow and a full beer each. ",
                "Marshmallow Stuff"));
                bodyDeck.add(new Data("https://cdn.pixabay.com/photo/2014/11/05/16/35/milk-518067_960_720.jpg"*//*CC0 no attribution*//*,
                "One player from each team must drink as much milk (or milk substitute) as they can within a minute. Player that drinks the most without vomiting or completes their jug first wins for his/her team. The losing opponent must remove their clothes and wear only underwear and a bathrobe for the remaining duration of the game.",
                "The McPoyle Chug"));
                bodyDeck.add(new Data("https://c1.staticflickr.com/3/2882/33906331700_432ec4b822_z.jpg"*//*CC0*//*,
                "Choose one member of the opposing team to strike your bare ass with a ruler. No flinching. Completion wins card, opponent may try to steal if failed.",
                "Naughty!"));
                bodyDeck.add(new Data("https://img.clipartfox.com/ef670ab02c821294a282f97b7677048b_free-flamingo-clipart-flamingo-hd-clipart_518-750.png",
                "All Players must stand on one foot. Whichever player stands the longest wins the card. No physical contact is allowed between any of the players, and no support from objects or other teammates can be used. If a player is the last one standing for their team, they must also start drinking a beer while standing on one foot. If both players are last one standing, both players must be drinking beer while standing on one foot. “Who Let the Dogs Out” by Baja Men will be playing on repeat in the background.",
                "The Flamingo"));
                bodyDeck.add(new Data("",
                "Select one player from each team. Slam a beer, do jumping jacks, in rhythm for 45 seconds, slam another schooner of beer, do jumping jacks for another 45 seconds. Repeat. The first one to puke, spray foam or give up loses and must take a shot. The winner gets the card. “Rock that Body” by the Black Eyed Peas will be playing on repeat in the background.",
                "Paint Mixer"));
                bodyDeck.add(new Data("",
                "Player who selects this card must stand facing the opposing team with their shirt off while each member of throws 1 ping pong ball each from a distance of 10 feet. If he/she flinches or shows any sign of pain he loses the challenge. Opponents may steal if failed.",
                "Ping…Pong"));
                bodyDeck.add(new Data("",
                "Player selecting card competes against youngest opponent. Hold one or two pitchers (up to the drawer on how many) or similar objects of equal weight up at eye level using both arms. Arms fully extended. Whoever can hold it up longer wins. Loser must drink a full beer. “Push It” by Salt-N-Pepa will be playing on repeat in the background.",
                "The Pitcher Hold"));
                bodyDeck.add(new Data("",
                "Player vs. opposing player closest in height. Plank until one of you drops. Loser must drink a full beer. “Tainted Love” by Non-Stop Erotic Cabaret will be playing on repeat in the background.",
                "Planking"));
                bodyDeck.add(new Data("",
                "Using a predetermined area (ie. around the house) each player from each team must chug a beer and run a single lap. Before the next racer may begin running, he/she must then chug his/beer before beginning the next lap and so forth until every player has run. If a team has an odd number, the odd team must select one player to not race but consume an entire beer for each lap his team completes.",
                "Relay Race"));
                bodyDeck.add(new Data("",
                "Round House Kick an object 6 feet off the ground. After every failed attempt, player must drink a shot of beer. Time limit…puke…or 2 minutes.",
                "Round House Kick"));
                bodyDeck.add(new Data("",
                "Player must pick any object (from paper airplane to giant river log) and must toss it as far as possible. Mark the distance. Opposing team nominates a player of their own to toss the same object. Furthest throw gets the card and opposing team member must consume a full beer.",
                "Shot Put"));
                bodyDeck.add(new Data("",
                "Everyone chugs a beer, all cups/cans must be flipped over to show that it is empty. First team to have all cups flipped wins the card.",
                "SHOTGUN"));
                bodyDeck.add(new Data("https://farm3.staticflickr.com/2850/33448052694_8393c88ba2_b.jpg",
                "One member from each team must try to see how many ice cubes he or she can fit in their underwear (bottom drawers only) in 45 sec. The player can only place one at a time in his or her underwear. Team member with the most ice cubes in their underwear wins the card, but must keep the ice in their underwear until it has melted. Failure to keep ice cubes in underwear until completely melted results in the loss of the card. The player may not change out of clothing until the end of the game.",
                "The Shriveler"));*/
/*
            bodyDeck.add(new Data("",
                    "Both Teams must select one player to remove shirt and be slapped on the back by every opposing teammate except the slappee. Whichever team leaves the brightest mark after 1 min wins the card. Losing team must drink a full beer each except for slappee",
                    "Slap Happy"));
            bodyDeck.add(new Data("",
                    "Both teams must hold a wall squat for one minute, and then drink a shot of beer. Repeat until there is only one player left. The team that wins may elect to steal a card (loss of card may drop a team into a lower level) OR force the entire opposing team to consume 2 full beers. “Le Freak” by C’est Chic must be playing on repeat in the background.",
                    "Team Drunken Wall Squad"));
            bodyDeck.add(new Data("",
                    "Player selecting card and opposing team player who selected the last card are Team Captains. Captains select fastest runners on their own team in order. Last picked on each team are not allowed to race and must take two shotguns, one to start their team’s relay, one to finish it. Both teams relay race predetermined distance. Winning team gets card.",
                    "That is why you always get picked last, fatty"));
*/

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
        /*Spirit deck load data*/

/*there are missing cards check google doc*/
/*        spiritDeck.add(new Data("",
                "Both teams race in relay style in order of their own choosing must take off their pants and give their pants to a teammate who subsequently takes off their own pants, puts on their first team mates pants and passes their own pants on until the entire team has traded pants. For pants to be “on” pants waits must be a least above the knees and buttoned or zipped. Team to finish first wins the card.",
                "No Pants Dance"));
        spiritDeck.add(new Data("",
                "Player must take a poop in the closest bathroom. Player may elect another member of his/her team to take his/her place. Poop must remain un-flushed and in the toilet. Upon completion of defecation, player’s entire team must enter and stay in the bathroom for 2 minutes. Opposing team may mock and insult during this time. If no poop is possible or if any player leaves the room during the poop marinating process, everyone on the player’s team must take a shot and no card is awarded.",
                "Poop, it’s funny"));
        spiritDeck.add(new Data("",
                "The player who pulls this card has to keep their head down, their hands behind their back, and their mouths shut, except when the game calls for them to behave otherwise. The puller must respond to whatever shaming emotional beratement with a humbling \"I know, I’m sorry master\" without defending themselves or getting angry. This lasts until their team wins another card. The card goes to the opposing team if the player breaks or to the puller if they are successful. Losing team must take a shot each once the card is awarded.",
                "The Shame of Shames"));
        spiritDeck.add(new Data("",
                "Everyone hands their phone to an opposing player. You then get to select anyone in their contacts list (parents and bosses not included) and type any singular text message you want within 2 minutes. You then pass the phone back to its owner and they either have to press send or take a shot. Team with the most people that press send wins the card.",
                "Shot or Send"));
        spiritDeck.add(new Data("",
                "Six Shots…One player …Accept the Challenge. Player selecting the card only. Opposing team may steal, but card drawer selects who will drink AFTER the opposing team has chosen to steal. If opposing team member fails, each team member must take a shot for every shot that was not taken by the chosen player.",
                "Six Shot Challenge"));
        spiritDeck.add(new Data("",
                "Play the rest of the game only in your underwear.You have the card while only in your underwear. If you do not strip down or then put clothing on, it goes to the other team.Take a shot to ease the embarrassment. If you are already stripped down due to another activity, no card is awarded.",
                "Strip"));
        spiritDeck.add(new Data("",
                "\"Hey, look everybody, Billy peed his pants.\" Billy: \"Of course I peed my pants, everybody my age pees their pants. It's the coolest! You ain't cool unless you pee your pants.\" The card is yours if you’re cool and pee your pants. Opposing team can steal if you are not the coolest.",
                "You ain't cool unless you pee your pants"));*/

    }
/*-----     Setup Decks end     -----*/

    /*-----     Start Game Buttons start     -----*/
    /* go to pre-game setup */
    private void onStartSetupFullClick(View view) {
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
        View mView = getLayoutInflater().inflate(R.layout.dialog_pregame_setup_lite,null);
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

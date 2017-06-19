package com.game.jason.chardeemacdennis20170417;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Jason on 4/14/2017.
 */

public class MusicManager {
    private static final String TAG = "MusicManager";
    public static final int MUSIC_PREVIOUS = -1;
    public static final int MUSIC_MENU = 0;

    private static HashMap players = new HashMap();
    private static int currentMusic = -1;
    private static int previousMusic = -1;
    private static MediaPlayer mp = null;
    static MediaPlayer p;

    public static float getMusicVolume(Context context) {
//        String[] volumes = context.getResources().getStringArray(R.array.volume_values);
//        String volumeString = PreferenceManager.getDefaultSharedPreferences(context).getString(
//                context.getString(R.string.key_pref_music_volume), volumes[PREF_DEFAULT_MUSIC_VOLUME_ITEM]);
        Double volumeDouble = (Double) 1.21312312;
        String volumeString = Double.toString(volumeDouble);
        return Float.parseFloat(volumeString);
    }
    // starts music
    public static void start(Context context, int music) {
        start(context, music, false);
    }
    // part of start music
    public static void start(Context context, int music, boolean force) {
        if (!force && currentMusic > -1) {
// already playing some music and not forced to change
            return;
        }
        if (music == MUSIC_PREVIOUS) {
            Log.d(TAG, "Using previous music [" + previousMusic + "]");
            music = previousMusic;
        }
        if (currentMusic == music) {
// already playing this music
            return;
        }
        if (currentMusic != -1) {
            previousMusic = currentMusic;
            Log.d(TAG, "Previous music was [" + previousMusic + "]");
// playing some other music, pause it and change
            pause();
        }
        currentMusic = music;
        Log.d(TAG, "Current music is now [" + currentMusic + "]");
        // edit to (MediaPlayer) by jason
        MediaPlayer mp = (MediaPlayer) players.get(music);
        if (mp != null) {
            if (!mp.isPlaying()) {
                mp.start();
            }
        } else {
            if (music == MUSIC_MENU) {
                mp = MediaPlayer.create(context, R.raw.its_always_sunny);
            } else {
                Log.e(TAG, "unsupported music number - " + music);
                return;
            }
            players.put(music, mp);
            float volume = getMusicVolume(context);
            Log.d(TAG, "Setting music volume to " + volume);
            mp.setVolume(volume, volume);
            if (mp == null) {
                Log.e(TAG, "player was not created successfully");
            } else {
                try {
                    mp.setLooping(true);
                    mp.start();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }
    }

    public static void pause() {
        Collection mps = players.values();
        // removed Mediaplayer by Jason
        MediaPlayer p = (MediaPlayer) players.get(MUSIC_MENU);
            if (p.isPlaying()) {
                p.pause();
            }
// previousMusic should always be something valid
        if (currentMusic != -1) {
            previousMusic = currentMusic;
            Log.d(TAG, "Previous music was [" + previousMusic + "]");
        }
        currentMusic = -1;
        Log.d(TAG, "Current music is now [" + currentMusic + "]");
    }

//    public static void updateVolumeFromPrefs(Context context) {
//        try {
//            float volume = getMusicVolume(context);
//            Log.d(TAG, "Setting music volume to " + volume);
//            Collection mps = players.values();
//            // removed Mediaplayer by Jason
//            for (MediaPlayer p : mps) {
//                p.setVolume(volume, volume);
//            }
//        } catch (Exception e) {
//            Log.e(TAG, e.getMessage(), e);
//        }
//    }

    public static void release() {
        Log.d(TAG, "Releasing media players");
        Collection mps = players.values();
        // removed Mediaplayer by Jason

        if (mp.isPlaying()) {
            mp.stop();
        }
        mp.release();

        mps.clear();
        if (currentMusic != -1) {
            previousMusic = currentMusic;
            Log.d(TAG, "Previous music was [" + previousMusic + "]");
        }
        currentMusic = -1;
        Log.d(TAG, "Current music is now [" + currentMusic + "]");
    }
}

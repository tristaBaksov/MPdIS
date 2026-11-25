package com.example.mediaplayer2;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    private static final String TAG = "Tag Name";
    private MediaPlayer myPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();

        try {
            myPlayer = MediaPlayer.create(this, R.raw.music_1);
            if (myPlayer == null) {
                AssetFileDescriptor afd = getAssets().openFd("1.mp3");
                myPlayer = new MediaPlayer();
                myPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                afd.close();
                myPlayer.prepare();
            }
            if (myPlayer != null) {
                myPlayer.setLooping(false); // Set looping
            }
        } catch (Exception e) {
            Log.e(TAG, "Error initializing MediaPlayer for 1.mp3", e);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent, startId);
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Log.d(TAG, "Oleg German greets You");
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        if (myPlayer != null) {
            try {
                if (!myPlayer.isPlaying()) {
                    myPlayer.start();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error starting myPlayer", e);
            }
        }
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
        if (myPlayer != null) {
            try {
                if (myPlayer.isPlaying()) {
                    myPlayer.stop();
                }
                myPlayer.release();
            } catch (Exception e) {
                Log.e(TAG, "Error stopping myPlayer", e);
            }
            myPlayer = null;
        }
        super.onDestroy();
    }
}

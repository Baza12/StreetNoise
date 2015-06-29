package pl.leszczenko.streetnoise;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;

/**
 * Created by Roma on 07.06.2015.
 */
public class PlayDefault extends Service {


    String fullPath;
    MediaPlayer mediaPlayer;
    int pausePosition = 0;
    //SeekBar seekBar;
    Runnable notification;
    //ToggleButton togglePlay;
    Handler handler = new Handler();
    TextView filePathText;
    private static final String AUDIO_RECORDER_FOLDER = "StreetNoise";

    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";
    final String SAVED_TEXT_DEFAULT = "saved_text_default";
    final String LOG_TAG = "myLogs";
    String playFile;




    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        Log.d(LOG_TAG, "full name load " + getFilenamePlay());
        Toast.makeText(this, "Service Created Default", Toast.LENGTH_SHORT).show();

        String prowerkaNaDefault = loadText();

        Log.d(LOG_TAG, "prowerkaNaDefault " + prowerkaNaDefault);



        if (prowerkaNaDefault.equalsIgnoreCase("Street_Noise.mp3") || prowerkaNaDefault.isEmpty()){

            try {

                releasePlayer();
                mediaPlayer = new MediaPlayer();
                mediaPlayer = MediaPlayer.create(this, R.raw.streetnoise);
                mediaPlayer.prepare();
                mediaPlayer.setLooping(true);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }else {


            String playFile = getFilenamePlay();

            try {
                releasePlayer();
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(playFile);
                mediaPlayer.prepare();
                mediaPlayer.setLooping(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        mediaPlayer.stop();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();


        // mediaPlayer.start();




        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                //seekBar.setMax(mediaPlayer.getDuration());
              //  mediaPlayer.setLooping(true);
                mediaPlayer.start();
                //seekBarUpdater();
            }
        });


        //mediaPlayer.start();
    }




    private String getFilenamePlay() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);

      //  Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show();



        Log.d(LOG_TAG, "Full path + name " + file.getAbsolutePath() + "/" + loadText());
        return (file.getAbsolutePath() + "/" + loadText());
    }

    public String  loadText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedTextDefault = sPref.getString(SAVED_TEXT_DEFAULT, "");
        //etText.setText(savedText);
     //   Toast.makeText(this, savedTextDefault , Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "savedTextDefault " + savedTextDefault);
        return savedTextDefault;
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }








}

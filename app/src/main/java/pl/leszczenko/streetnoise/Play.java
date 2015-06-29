package pl.leszczenko.streetnoise;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class Play extends Service {


    final String LOG_TAG = "myLogs";
    MediaPlayer mPlayer;

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
            Log.d(LOG_TAG, "Play Service Created");
            mPlayer = MediaPlayer.create(this, R.raw.streetnoise);
            mPlayer.setLooping(true);
        }

        @Override
        public void onDestroy() {
            Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
            Log.d(LOG_TAG, "Play Service Stopped");
            mPlayer.stop();
        }

        @Override
        public void onStart(Intent intent, int startid) {
            Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
            Log.d(LOG_TAG, "Play Service Started");
            mPlayer.start();
        }
    }

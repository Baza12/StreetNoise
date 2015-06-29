//This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package pl.leszczenko.streetnoise;

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

public class AudioPlayer extends Service {

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
	final String LOG_TAG = "myLogs";
	String playFile;




	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {

		Log.d(LOG_TAG, "full name load " + getFilenamePlay());
		Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();

		String prowerkaNaDefault = loadText();

		Log.d(LOG_TAG, "prowerkaNaDefault " + prowerkaNaDefault);

		if (prowerkaNaDefault.equalsIgnoreCase("Street_Noise.mp3")){

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




		mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			public void onPrepared(MediaPlayer mp) {
				//seekBar.setMax(mediaPlayer.getDuration());
				mediaPlayer.start();
				//seekBarUpdater();
			}
		});


		//mediaPlayer.start();
	}





	private String getFilenamePlay() {
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath, AUDIO_RECORDER_FOLDER);

	//	Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show();



		Log.d(LOG_TAG, "Full path + name " + file.getAbsolutePath() + "/" + loadText());
		return (file.getAbsolutePath() + "/" + loadText());
	}

	public String  loadText() {
		sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
		String savedText = sPref.getString(SAVED_TEXT, "");
		//etText.setText(savedText);
		//Toast.makeText(this, savedText , Toast.LENGTH_SHORT).show();
		Log.d(LOG_TAG, "savedText " + savedText);
		return savedText;
	}

	private void releasePlayer() {
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}









}

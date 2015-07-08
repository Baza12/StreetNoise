package pl.leszczenko.streetnoise;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class Record extends Service {

	//private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
	private static final String AUDIO_RECORDER_FILE_EXT_3GPP = ".3gpp";
	private static final String AUDIO_RECORDER_FOLDER = "StreetNoise";
	final String LOG_TAG = "myLogs";

	private MediaRecorder recorder = null;
	private int currentFormat = 0;
	private int output_formats[] = { MediaRecorder.OutputFormat.THREE_GPP,
			MediaRecorder.OutputFormat.THREE_GPP };
	private String file_exts[] = {AUDIO_RECORDER_FILE_EXT_3GPP,
			 };




	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}


	@Override
	public void onCreate() {

		Toast.makeText(this, "Service Record Created", Toast.LENGTH_SHORT).show();
		Log.d(LOG_TAG, "Service Record Created");



	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Service Record Stopped", Toast.LENGTH_SHORT).show();
		Log.d(LOG_TAG, "Service Record Stopped");
		stopRecording();
	}

	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "Service Record Started", Toast.LENGTH_SHORT).show();
		Log.d(LOG_TAG, "Service Record Started");
		startRecording();
	}


	public String getFilename() {
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath, AUDIO_RECORDER_FOLDER);

	//	Toast.makeText(this,file.toString() , Toast.LENGTH_LONG).show();

		if (!file.exists()) {
			file.mkdirs();
		}


		Log.d(LOG_TAG, filepath);
		Log.d(LOG_TAG, file.toString());

		return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
	}

	private void startRecording() {



		recorder = new MediaRecorder();

		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(output_formats[currentFormat]);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(getFilename());

		recorder.setOnErrorListener(errorListener);
		// recorder.setOnInfoListener(infoListener);

		try {
			recorder.prepare();
			recorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void stopRecording() {
		if (null != recorder) {
			recorder.stop();
			recorder.reset();
			recorder.release();

			recorder = null;
		}
	}

	private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
		@Override
		public void onError(MediaRecorder mr, int what, int extra) {
			Toast.makeText(Record.this,
					"Error: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
		}
	};

	private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			Toast.makeText(Record.this,
					"Warning: " + what + ", " + extra, Toast.LENGTH_SHORT)
					.show();
		}
	};



}

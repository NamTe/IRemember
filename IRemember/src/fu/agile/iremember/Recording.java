package fu.agile.iremember;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Recording extends Activity implements OnClickListener {

	private Button btRecorder;
	private Button btPlay;
	private Button btSave;
	private MediaRecorder mRecoder;
	private MediaPlayer mPlayer;
	private boolean startRecoding;
	private boolean startPlay;
	private String audioPath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recording);
		btPlay = (Button) findViewById(R.id.btPlayRecorder);
		btRecorder = (Button)findViewById(R.id.btRecorder);
		btSave = (Button)findViewById(R.id.btSaveRecorder);
		btPlay.setOnClickListener(this);
		btRecorder.setOnClickListener(this);
		btSave.setOnClickListener(this);
		startRecoding = false;
		startPlay = false;
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recording, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()) {
			case R.id.btRecorder : {
				if(startRecoding == false) {
					startingRecorder();
					btRecorder.setText("Stop Recording");
				} else {
					stopRecording();
					btRecorder.setText("Record");
				}
				break;
			}
			case R.id.btPlayRecorder : {
				if(startRecoding == false && startPlay == false) {
					startPlaying();
					
				}
				if(startPlay == true && startRecoding == false) {
					stopPlaying();
					
				}
				break;
			}
			case R.id.btSaveRecorder : {
				
				break;
			}
		}
	}
	
	public void startingRecorder() {
		String prefix = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String audioName = "AD_AU_" + prefix + "_.mp3";
		audioPath = getString(R.string._mnt_sdcard_iremember_audio) + "/" + audioName;
		startRecoding = true;
		mRecoder = new MediaRecorder();
		mRecoder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecoder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecoder.setOutputFile(audioPath);
		mRecoder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
        	mRecoder.prepare();
        } catch (IOException e) {
            Log.e("TEST", "prepare() failed");
        }

        mRecoder.start();

	}
	
	private void stopRecording() {
		startRecoding = false;
		mRecoder.stop();
		mRecoder.release();
        mRecoder = null;
        Log.d("TEST", audioPath);
    }
	
	private void startPlaying() {
		startPlay = true;
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(audioPath);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("TEST", "prepare() failed");
        }
    }
	
	private void stopPlaying() {
		startPlay = false;
        mPlayer.release();
        mPlayer = null;
    }



}

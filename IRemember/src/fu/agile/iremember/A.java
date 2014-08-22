package fu.agile.iremember;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class A {
	private MediaRecorder Recoder;
	private String audioPath;
	private boolean isPlay;
	private MediaPlayer mPlayer;
	A() {
		isPlay = false;
	}
	
	public boolean startingRecord() {
		try {
			mPlayer.stop();
			mPlayer.release();
			mPlayer = null;
			isPlay = false;
		} catch(Exception exc ) {
			
		}
		String prefix = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String audioName = "AD_AU_" + prefix + "_.mp3";
		audioPath = getApplicationContext()
				.getString(R.string._mnt_sdcard_iremember_audio_) + audioName;
		Recoder = new MediaRecorder();
		Recoder.setAudioSource(MediaRecorder.AudioSource.MIC);
		Recoder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		Recoder.setOutputFile(audioPath);
		Recoder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		try {
			Recoder.prepare();
        } catch (IOException e) {
            return false;
        }
		Recoder.start();
		return true;
	}
	
	public void stopRecord() {
		Recoder.stop();
		Recoder.release();
		Recoder = null;
		isPlay = false;
    }
	
	public void startPlaying() {
		try {
			mPlayer.stop();
			mPlayer.release();
			mPlayer = null;
		} catch(Exception exc) {
			
		}
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(audioPath);
            mPlayer.prepare();
            mPlayer.start();
            isPlay = true;
        } catch (IOException e) {
        	Toast.makeText(getApplicationContext(), "SomeThing went wrong", Toast.LENGTH_LONG).show();
        }
    }
	public void stopPlaying() {
		try {
			mPlayer.stop();
			mPlayer.release();
			mPlayer = null;
			isPlay = false;
		} catch(Exception exc) {
			
		}
	}
	
	private Context getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAudioPath() {
		return audioPath;
	}
	
	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}
	
	public void setIsPlay(boolean isPlay) {
		this.isPlay = isPlay;
	}
	
	public boolean getIsPlay() { return this.isPlay; }
 }

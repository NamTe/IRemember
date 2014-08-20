package fu.agile.iremember;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.media.MediaRecorder;
import android.provider.MediaStore.Video.Media;
import android.widget.Toast;

public class A {
	MediaRecorder Recoder;
	String audioPath;
	A() {
		
	}
	
	public boolean startingRecord() {
		String prefix = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String audioName = "AD_AU_" + prefix + "_.mp3";
		audioPath = "/mnt/sdcard/IRemember/Audio" + "/" + audioName;
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
    }
	
	public String getAudioPath() {
		return audioPath;
	}
 }

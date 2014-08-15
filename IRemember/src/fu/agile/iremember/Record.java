package fu.agile.iremember;

import java.io.File;

import android.text.format.Time;

public class Record {
	private String title;
	private String body;
	private File audio;
	private File image;
	private File video;
	private Time storyTime;
	
	public Record(String mTitle, String mBody, File mAudio, File mImage, File mVideo, Time storyTime) {
		title = mTitle;
		body = mBody;
		audio = new File(mAudio.getAbsolutePath().toString());
		image = new File(mImage.getAbsolutePath().toString());
		video = new File(mVideo.getAbsolutePath().toString());
		storyTime = storyTime;
	}
	
	public Record(String mTitle, String mBody) {
		title = mTitle;
		body = mBody;
	}
	
	public Record() {
		
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getBody() {
		return body;
	}
}

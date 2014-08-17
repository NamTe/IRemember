package fu.agile.iremember;

import java.io.File;

import android.text.format.Time;

public class Record {
	private int id;
	private String title;
	private String body;
	private File audio;
	private File image;
	private File video;
	private String storyTime;
	private String latitute;
	private String longitude;
	public Record(String mTitle, String mBody, String audioPath, String imagePath, String videoPath, String storyTime,String latitute,String longitude) {
		title = mTitle;
		body = mBody;
		audio = new File(audioPath);
		image = new File(imagePath);
		video = new File(videoPath);
		this.storyTime = storyTime;
		this.latitute = latitute;
		this.longitude = longitude;
	}
	
	public Record(String title, String body, String storyTime) {
		this.title = title;
		this.body = body;
		this.storyTime = storyTime;
	}
	
	public Record() {
		title = "Unknow";
		body = "Unknow";
		audio = null;
		image = null;
		video = null;
		this.storyTime = "0/0/0";
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public String getLatitute() {
		return latitute;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getBody() {
		return body;
	}
	
	public String getTime() {
		return storyTime;
	}
	
	public File getAudioFile() {
		return audio;
	}
	
	public File getImageFile() {
		return image;
	}
	
	public File getVideoFile() {
		return video;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public void setLatitute(String latitute) {
		this.latitute = latitute;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public void setStoryTime(String storyTime){
		this.storyTime = storyTime;
	}
	
	public void setAudioFile(String audioPath) {
		if(audio != null) audio = null;
		audio = new File(audioPath);
	}
	
	public void setImageFile(String imagePath) {
		if(image != null) image = null;
		image = new File(imagePath);
	}
	
	public void setVideoFile(String videoPath) {
		if(video != null) video = null;
		video = new File(videoPath);
	}
	
	public void setID(int id) {
		this.id =id;
	}
	
	
	public int getID() {
		return id;
	}
}

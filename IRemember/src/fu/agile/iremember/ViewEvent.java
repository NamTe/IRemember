package fu.agile.iremember;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

public class ViewEvent extends Activity implements OnClickListener {

	//Button Declare
	private ImageButton btEdit;
	private ImageButton btDelete;
	private ImageButton btBack;
	private ImageButton btPlayAudio;
	private ImageButton btPauseAudio;
	//Edit Text Declare
	private EditText etTitle;
	private EditText etBody;
	private MediaPlayer mMedia;
	//TextView Declare
	private TextView tvTime;
	private TextView textLocation;
	private TextView viewTitle;
	private TextView tvBody;
	private TextView tvlongitude;
	private TextView tvlatitute;
	
	//String Declare
	private String time;
	private String audioPath;
	private String imagePath;
	private String videoPath;
	private String latitute;
	private String longitude;
	private String provider;
	private TimePicker timePicker;
	private DataBase db;
	private List<Card> RecordList;
	private Card card;
	//Animation Declaration
	private Animation anim;
	private static int position;
	
	//Image Declare
	private ImageView viewImage;
	
	
	//Video
	private VideoView viewVideo;
	//something else declare
	private SeekBar seekBar;
	Handler myhaHandler = new Handler();
	private static int REQUEST_EDIT = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_event);
		
		Intent intent = getIntent();
		position = intent.getIntExtra("position", -1);
		if(position == -1) {
			Toast.makeText(getApplicationContext(), "You are encounter an error", Toast.LENGTH_LONG).show();
			Intent inte = new Intent();
			setResult(RESULT_CANCELED, inte);
			finish();
		}
		intit();  
		
		implementInit();
	}

	private void intit() {
		btEdit = (ImageButton) findViewById(R.id.btEditEvent);
		btEdit.setOnClickListener(this);
		btDelete = (ImageButton) findViewById(R.id.btDelete);
		btDelete.setOnClickListener(this);
		btBack = (ImageButton) findViewById(R.id.btBack);
		btBack.setOnClickListener(this);
		btPlayAudio = (ImageButton)findViewById(R.id.btPlayAudio);
		btPlayAudio.setOnClickListener(this);
		btPauseAudio = (ImageButton)findViewById(R.id.btPauseAudio);
		btPauseAudio.setVisibility(View.INVISIBLE);
		viewTitle = (TextView)findViewById(R.id.viewTitle);
		tvBody  = (TextView)findViewById(R.id.tvBody);
		viewImage = (ImageView)findViewById(R.id.viewImage);
		viewVideo = (VideoView)findViewById(R.id.viewVideo);
		tvlongitude = (TextView)findViewById(R.id.tvLongitude);
		tvlatitute = (TextView)findViewById(R.id.tvlatitude);
		tvTime = (TextView)findViewById(R.id.tvTime);
		anim = AnimationUtils.loadAnimation(this, R.anim.zoom_animation);
		seekBar = (SeekBar)findViewById(R.id.seekBarAudio);
		db = new DataBase(this);
		mMedia = new MediaPlayer();
	}
	
	private void implementInit() {
		RecordList = db.getAllRecords();
		card = RecordList.get(position);
		if(card.getTitle().equals("unkown") == false) 
		viewTitle.setText(card.getTitle());
		tvBody.setText(card.getBody());
		audioPath = card.getAudioFile();
		imagePath = card.getImageFile();
		videoPath = card.getVideoFile();
		if(imagePath.equalsIgnoreCase("unknow") == false) {
			Bitmap bit = BitmapFactory.decodeFile(imagePath);
			viewImage.setImageBitmap(bit);
		}else {
			viewImage.setImageResource(R.drawable.nexus);
		}
		
		viewVideo.setVideoPath(videoPath);
		viewVideo.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				viewVideo.start();
				return false;
			}
		});
		time = card.getTime();
		tvTime.setText(card.getTime());
		latitute = card.getLatitute();
		longitude = card.getLongitude();
		tvlatitute.setText(latitute);
		tvlongitude.setText(longitude);
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()) {

			case R.id.btEditEvent : {
				findViewById(R.id.bt_edit_effect).startAnimation(anim);
				Intent intent = new Intent(ViewEvent.this,Edit_view.class);
				intent.putExtra("position", position);
				startActivityForResult(intent, REQUEST_EDIT);
				break;
			} 
			 case R.id.btDelete : {
				findViewById(R.id.bt_delete_effect).startAnimation(anim);
				break;
			} 
			case R.id.btBack: {
				findViewById(R.id.bt_back_effect).startAnimation(anim);
				super.onBackPressed();
				break;
			}
			case R.id.btPlayAudio : {
				mMedia.reset();
				mMedia = MediaPlayer.create(this, Uri.parse(audioPath));
				mMedia.start();
				seekBar.setProgress(0);
				seekBar.setMax(mMedia.getDuration());				
				startPlayProgressUpdater();
				//btPauseAudio.setVisibility(View.VISIBLE);
				//btPlayAudio.setVisibility(View.INVISIBLE);
				break;
			}
		}
	}

	public void startPlayProgressUpdater() {
	    
		Runnable notification = new Runnable() {
			public void run() {
				seekBar.setProgress(mMedia.getCurrentPosition());
				startPlayProgressUpdater();
				if(mMedia.isPlaying() == false) {
					seekBar.setProgress(mMedia.getDuration());
				}
			}
		};
		myhaHandler.postDelayed(notification,1000);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		implementInit();
	}
}

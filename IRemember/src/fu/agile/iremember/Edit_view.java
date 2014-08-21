package fu.agile.iremember;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

public class Edit_view extends Activity implements OnClickListener, LocationListener{

	//Button Declare
	private ImageButton btAddTitle;
	private ImageButton btAddBody;
	private ImageButton create;
	private ImageButton btAddVideo;
	private ImageButton btCameraVideo;
	private ImageButton btPlayAudio;
	private ImageButton btRecordAudio;
	private ImageButton btStopRecordAudio;
	private ImageButton btSaveAudio;
	private ImageButton btAddImage;
	private ImageButton btOpenCamera;
	private ImageButton btAddAudio;
	private ImageButton btAddLocation;
	private ImageButton btCreate;
	private ImageButton btClear;
	private ImageButton btCancel;
	private ImageButton btAddTime;
	private ImageButton btFromFileVideo;
	private ImageButton btFromFile;
	
	
	
	
	//Edit Text Declare
	private EditText etTitle;
	private EditText etBody;
	
	//TextView Declare
	private TextView TimeView;
	private TextView textLocation;
	
	//String Declare
	private String time;
	private String audioPath;
	private String imagePath;
	private String videoPath;
	private static String tag = "Hello";
	private String latitute;
	private String longitude;
	private String provider;
	private TimePicker timePicker;
	//ImageView
	private ImageView imageView;
	private ImageView viewImage;
	//VideoView
	private VideoView videoView;
	//Animation Declaration
	private Animation anim;
	//Something else
	private Location location;
	private Card newRecord;
	private DataBase db;
	private LocationManager locationManager;
	static private int TAKE_PICTURE_CODE = 1;
	static private int TAKE_VIDEO_CODE = 2;
	static private int TAKE_AUDIO_RECORDER = 3;
	
	private A recordAudio = new A();
	private int position;
	private List<Card> RecordList;
	private Card card;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		init();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the locatioin provider -> use
	    // default
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);

	    latitute = "Location not available";
    	longitude = "Location not available";
	    // Initialize the location fields
	    if (location != null) {
	      System.out.println("Provider " + provider + " has been selected.");
	      onLocationChanged(location);
	    } else {
	    	latitute = "Location not available";
	    	longitude = "Location not available";
	    }
	    db = new DataBase(getApplicationContext());
	    Intent intent = getIntent();
	    position = intent.getIntExtra("position", -1);
	    if(position == -1) {
	    	Toast.makeText(getApplicationContext(), "You are facing some error", Toast.LENGTH_LONG).show();
	    	Intent inte = new Intent();
	    	setResult(RESULT_CANCELED, inte);
	    	finish();
	    }else {
	    	RecordList = db.getAllRecords();
	    	card = RecordList.get(position);
	    	etTitle.setText(card.getTitle());
	    	etBody.setText(card.getBody());
	    	audioPath = card.getAudioFile();
	    	imagePath = card.getImageFile();
	    	videoPath = card.getVideoFile();
	    	time = card.getTime();
	    	if(imagePath.equalsIgnoreCase("unknow") == false) {
	    		Bitmap bit = BitmapFactory.decodeFile(imagePath);
	    		imageView.setImageBitmap(bit);
	    	} else {
	    		imageView.setImageResource(R.drawable.nexus);
	    	}
	    	TimeView.setText(card.getTime());
	    	if(card.getLatitute().equals("Location not available") != true)
	    		textLocation.setText(card.getLatitute() + " : " + card.getLongitude());
	    	else {
	    		textLocation.setText("Location not available");
	    	}
	    }
	}
////	
	
	@Override
	  protected void onResume() {
	    super.onResume();
	    locationManager.requestLocationUpdates(provider, 400, 1, this);
	  }

	  /* Remove the locationlistener updates when Activity is paused */
	  @Override
	  protected void onPause() {
	    super.onPause();
	    locationManager.removeUpdates(this);
	  }

	  @Override
	  public void onLocationChanged(Location location) {
		  latitute = location.getLatitude() + "";
		  longitude = location.getLongitude() + "";
	  }

	  @Override
	  public void onStatusChanged(String provider, int status, Bundle extras) {
	    // TODO Auto-generated method stub

	  }

	  @Override
	  public void onProviderEnabled(String provider) {
	    Toast.makeText(this, "Enabled new provider " + provider,
	        Toast.LENGTH_SHORT).show();

	  }

	  @Override
	  public void onProviderDisabled(String provider) {
	    Toast.makeText(this, "Disabled provider " + provider,
	        Toast.LENGTH_SHORT).show();
	  }
	
	public void init() {
		time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		btAddTitle = (ImageButton) findViewById(R.id.btAddTitle);
		btAddTitle.setOnClickListener(this);
		btAddBody = (ImageButton) findViewById(R.id.btAddBody);
		btAddBody.setOnClickListener(this);
		btAddVideo = (ImageButton)findViewById(R.id.btAddVideo);
		btAddVideo.setOnClickListener(this);
		btCameraVideo = (ImageButton)findViewById(R.id.btCameraVideo);
		btCameraVideo.setOnClickListener(this);
		btFromFileVideo = (ImageButton)findViewById(R.id.btFromFileVideo);
		btFromFileVideo.setOnClickListener(this);
		btAddAudio = (ImageButton) findViewById(R.id.btAddAudio);
		btAddAudio.setOnClickListener(this);
		btPlayAudio = (ImageButton) findViewById(R.id.btAddPlayAudio);
		btPlayAudio.setOnClickListener(this);
		btRecordAudio = (ImageButton) findViewById(R.id.btAddRecordAudio);
		btRecordAudio.setOnClickListener(this);
		btStopRecordAudio = (ImageButton) findViewById(R.id.btStopRecordAudio);
		btStopRecordAudio.setOnClickListener(this);
		btSaveAudio = (ImageButton) findViewById(R.id.btSave);
		btSaveAudio.setOnClickListener(this);
		btAddImage = (ImageButton) findViewById(R.id.btAddImage);
		btAddImage.setOnClickListener(this);
		imageView = (ImageView) findViewById(R.id.AddViewImage);
		videoView = (VideoView) findViewById(R.id.AddViewVideo);
		btFromFile = (ImageButton) findViewById(R.id.btFromFile);
		btFromFile.setOnClickListener(this);
		btAddTime = (ImageButton)findViewById(R.id.btAddTime);
		btAddTime.setOnClickListener(this);
		btOpenCamera = (ImageButton) findViewById(R.id.btCameraImage);
		btOpenCamera.setOnClickListener(this);
		btAddLocation = (ImageButton)findViewById(R.id.btAddLocation);
		btAddLocation.setOnClickListener(this);
		btCreate = (ImageButton) findViewById(R.id.btCreateEvent);
		btCreate.setOnClickListener(this);
		btClear = (ImageButton) findViewById(R.id.btClear);
		btClear.setOnClickListener(this);
		btCancel = (ImageButton) findViewById(R.id.btCancel);
		btCancel.setOnClickListener(this);
		TimeView = (TextView)findViewById(R.id.textTime);
		
		anim = AnimationUtils.loadAnimation(this, R.anim.zoom_animation);
		etTitle = (EditText) findViewById(R.id.title);
		etBody = (EditText) findViewById(R.id.body);
		textLocation = (TextView)findViewById(R.id.textLocation);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == TAKE_PICTURE_CODE) {
			if(resultCode == RESULT_OK){      
		    	 imagePath = data.getStringExtra("resultOfPhoto");	    
		    	 Log.d(tag, imagePath);
		     }
		} 
		if(requestCode == TAKE_VIDEO_CODE) {
			if(resultCode == RESULT_OK){      
		    	 videoPath = data.getStringExtra("resultOfVideo");
		    	 Log.d(tag, videoPath);
		     }
		} 
		if(requestCode == TAKE_AUDIO_RECORDER) {			
				audioPath = data.getStringExtra("AudioPath");	
				Log.d(tag, audioPath);
		}
	} 

	 
	@Override
	public void onClick(View v) {
		
		switch(v.getId()) {
			case R.id.btAddTitle: {
				findViewById(R.id.title).requestFocus();
				findViewById(R.id.imagebtTitle).startAnimation(anim);
			} break;
			case R.id.btAddBody: {
				findViewById(R.id.body).setVisibility(View.VISIBLE);
				findViewById(R.id.body).requestFocus();
				findViewById(R.id.imagebtBody).startAnimation(anim);
			} break;
			case R.id.btAddAudio: {
				findViewById(R.id.audioLayout).setVisibility(View.VISIBLE);
				findViewById(R.id.imagebtAudio).startAnimation(anim);
			} break;
			case R.id.btAddPlayAudio: {
				findViewById(R.id.bt_play_audio_effect).startAnimation(anim);
				recordAudio.startPlaying();
			} break;
			case R.id.btAddRecordAudio: {
				findViewById(R.id.bt_record_audio_effect).startAnimation(anim);
				findViewById(R.id.btStopRecordAudio).setVisibility(View.VISIBLE);
				findViewById(R.id.btAddRecordAudio).setVisibility(View.INVISIBLE);			
				recordAudio.startingRecord();
			} break;
			case R.id.btStopRecordAudio: {
				findViewById(R.id.bt_stop_record_audio_effect).startAnimation(anim);
				findViewById(R.id.btAddRecordAudio).setVisibility(View.VISIBLE);
				findViewById(R.id.btStopRecordAudio).setVisibility(View.INVISIBLE);
				recordAudio.stopRecord();
			} break;
			case R.id.btSave: {
				findViewById(R.id.bt_save_effect).startAnimation(anim);
			} break;
			case R.id.btAddImage : {
				findViewById(R.id.imageAddLayout).setVisibility(View.VISIBLE);
				findViewById(R.id.imagebtImage).startAnimation(anim);
			} break;
			case R.id.btCameraImage: {		
				openCameraForImage(1);
			} break;
			case R.id.btFromFile : {
				openCameraForImage(0);
				break;
			}
			case R.id.btAddVideo : {
				findViewById(R.id.videoAddLayout).setVisibility(View.VISIBLE);
				findViewById(R.id.imagebtVideo).startAnimation(anim);
				
			} break;
			case R.id.btCameraVideo : {
				openCameraForVideo(2);
				break;
			}
			case R.id.btFromFileVideo : {
				openCameraForVideo(4);
				break;
			}
			
			case R.id.btCreateEvent : {
				newRecord = new Card(etTitle.getText().toString(), etBody.getText().toString(), audioPath.toString(), imagePath.toString(), videoPath.toString(), time.toString(),latitute,longitude);
				db.insertNewRecord(newRecord);
				Log.d(tag, etTitle.getText().toString());
				Log.d(tag, etBody.getText().toString());
				Log.d(tag, audioPath.toString());
				Log.d(tag, imagePath.toString());
				Log.d(tag, videoPath.toString());
				Log.d(tag, latitute.toString());
				Log.d(tag, longitude.toString());
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
				findViewById(R.id.bt_create_effect).startAnimation(anim);
				finish();
				break;
			} 
			case R.id.btAddTime : {
//				TimeView.setText(time);
//				timePicker = (TimePicker) findViewById(R.id.timePicker);
//				timePicker.setVisibility(View.VISIBLE);
				findViewById(R.id.imagebtTime).startAnimation(anim);
				time = timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();
				TimeView.setText(time);
				Log.d(tag, time);
				break;
			} case R.id.btClear : {
				findViewById(R.id.title).requestFocus();
				etBody.setText("");
				etTitle.setText("");
				findViewById(R.id.bt_clear_effect).startAnimation(anim);
				break;
			} 
			case R.id.btCancel: {
				findViewById(R.id.bt_cancel_effect).startAnimation(anim);
				super.onBackPressed();
				break;
			}
			case R.id.btAddLocation : {			
				Toast.makeText(getApplicationContext(), "Obtain new location", Toast.LENGTH_LONG).show();
				if(latitute.equals("Location not available") == true) {
					textLocation.setText("Geting location fail");
				} else {
					textLocation.setText("Success");
				}
				break;
			}
		}
	}

	public void openCameraForImage(int which) {
		Intent intent = new Intent(Edit_view.this,PhotoCapture.class);
		intent.putExtra("key", which);
		startActivityForResult(intent, TAKE_PICTURE_CODE);
	}
	
	public void openCameraForVideo(int which) {
		Intent intent = new Intent(Edit_view.this,PhotoCapture.class);
		intent.putExtra("Video", which);
		startActivityForResult(intent, TAKE_VIDEO_CODE);
	}
}

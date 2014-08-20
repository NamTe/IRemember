package fu.agile.iremember;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddScreen extends Activity implements OnClickListener, LocationListener{

	//Button Declare
	private ImageButton btAddTitle;
	private ImageButton btAddBody;
	private ImageButton create;
	private ImageButton btAddVideo;
	private ImageButton btCameraVideo;
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
	
	
	Button bt;
	
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
		btAddImage = (ImageButton) findViewById(R.id.btAddImage);
		btAddImage.setOnClickListener(this);
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
		bt = (Button) findViewById(R.id.button2);
		bt.setOnClickListener(this);
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
			case R.id.btAddImage : {
				findViewById(R.id.imageAddLayout).setVisibility(View.VISIBLE);
				findViewById(R.id.imagebtImage).startAnimation(anim);
				//openNewGameDialog();
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
			case R.id.btAddAudio : {
				Intent intent = new Intent(AddScreen.this,Recording.class);
				startActivityForResult(intent, TAKE_AUDIO_RECORDER);
				findViewById(R.id.imagebtAudio).startAnimation(anim);
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
			} case R.id.btAddTime : {
				TimeView.setText(time);
				break;
			} case R.id.btClear : {
				findViewById(R.id.title).requestFocus();
//				etImageName.setText("");
				etBody.setText("");
				etTitle.setText("");
//				latituteField.setText("0");
//				longitudeField.setText("0");
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
			case R.id.button2: {
				findViewById(R.id.body).setVisibility(View.GONE);
				findViewById(R.id.imageAddLayout).setVisibility(View.GONE);
				findViewById(R.id.videoAddLayout).setVisibility(View.GONE);
			} break;
		}
	}

	public void openCameraForImage(int which) {
		Intent intent = new Intent(AddScreen.this,PhotoCapture.class);
		intent.putExtra("key", which);
		startActivityForResult(intent, TAKE_PICTURE_CODE);
	}
	
	public void openCameraForVideo(int which) {
		Intent intent = new Intent(AddScreen.this,PhotoCapture.class);
		intent.putExtra("Video", which);
		startActivityForResult(intent, TAKE_VIDEO_CODE);
	}
}

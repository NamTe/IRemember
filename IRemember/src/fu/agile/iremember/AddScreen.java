package fu.agile.iremember;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddScreen extends Activity implements OnClickListener , LocationListener {

	//Button Declare
	private ImageButton btAddTitle;
	private ImageButton btAddBody;
	private ImageButton create;
	private ImageButton btAddVideo;
	private ImageButton btAddImage;
	private ImageButton btOpenCamera;
	private ImageButton btAddAudio;
	private ImageButton btGetLocation;
	private ImageButton btCreate;
	private ImageButton btClear;
	private ImageButton btCancel;
	Button bt;
	
	//Edit Text Declare
	private EditText etImageName;
	private EditText etTitle;
	private EditText etBody;
	private EditText inputDialog;
	
	//TextView Declare
	private TextView TimeView;
	private TextView latituteField;
	private TextView longitudeField;
	
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
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		btAddTitle = (ImageButton) findViewById(R.id.btAddTitle);
		btAddTitle.setOnClickListener(this);
		btAddBody = (ImageButton) findViewById(R.id.btAddBody);
		btAddBody.setOnClickListener(this);
		btAddVideo = (ImageButton)findViewById(R.id.btAddVideo);
		btAddVideo.setOnClickListener(this);
		btAddAudio = (ImageButton) findViewById(R.id.btAddAudio);
		btAddAudio.setOnClickListener(this);
		btAddImage = (ImageButton) findViewById(R.id.btAddImage);
		btAddImage.setOnClickListener(this);
		btOpenCamera = (ImageButton) findViewById(R.id.btCameraImage);
		btOpenCamera.setOnClickListener(this);
		btCreate = (ImageButton) findViewById(R.id.btCreateEvent);
		btCreate.setOnClickListener(this);
		btClear = (ImageButton) findViewById(R.id.btClear);
		btClear.setOnClickListener(this);
		btCancel = (ImageButton) findViewById(R.id.btCancel);
		btCancel.setOnClickListener(this);
		anim = AnimationUtils.loadAnimation(this, R.anim.zoom_animation);
		
		bt = (Button) findViewById(R.id.button2);
		bt.setOnClickListener(this);
//		btGetLocation = (ImageButton) findViewById(R.id.btGetlocation);
//		btGetLocation.setOnClickListener(this);
//		etImageName = (EditText) findViewById(R.id.etImageName);
		etTitle = (EditText) findViewById(R.id.title);
		etBody = (EditText) findViewById(R.id.body);
//		create = (ImageButton) findViewById(R.id.btCreate);
//		create.setOnClickListener(this);
//		TimeView = (TextView)findViewById(R.id.twSetTime);
//		TimeView.setOnClickListener(this);
//		longitudeField = (TextView)findViewById(R.id.twLongitude);
//		latituteField = (TextView)findViewById(R.id.twLatitude);
//		db = new DataBase(this);
		
////
////		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
////		Criteria criteria = new Criteria();
////		provider = locationManager.getBestProvider(criteria, false);
////		latitute = "0";
////	    longitude = "0";
////	    // Initialize the location fields
////	    location = locationManager.getLastKnownLocation(provider);
////	    if (location != null) {
////		      System.out.println("Provider " + provider + " has been selected.");
////		      onLocationChanged(location);
////		    } else {
////		    	latituteField.setText("Location not available");
////		    	longitudeField.setText("Location not available");
////		    }
	}
////	
////	
////	  @Override
////	  protected void onResume() {
////	    super.onResume();
////	    	locationManager.requestLocationUpdates(provider, 400, 1, this);
////	  }
////
////	  /* Remove the locationlistener updates when Activity is paused */
////	  @Override
////	  protected void onPause() {
////	    super.onPause();
////	    	locationManager.removeUpdates(this);
////	  }
////
////	  @Override
////	  public void onLocationChanged(Location location) {
////			int lat = (int) (location.getLatitude());
////		    int lng = (int) (location.getLongitude());
////		    latitute = String.valueOf(lat);
////		    longitude = String.valueOf(lng);
////	  }
////
////	  @Override
////	  public void onStatusChanged(String provider, int status, Bundle extras) {
////	    // TODO Auto-generated method stub
////
////	  }
////
////	  @Override
////	  public void onProviderEnabled(String provider) {
////	    Toast.makeText(this, "Enabled new provider " + provider,
////	        Toast.LENGTH_SHORT).show();
////
////	  }
////
////	  @Override
////	  public void onProviderDisabled(String provider) {
////	    Toast.makeText(this, "Disabled provider " + provider,
////	        Toast.LENGTH_SHORT).show();
////	  }
////	
////	
////	@Override
////	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////		if(requestCode == TAKE_PICTURE_CODE) {
////			if(resultCode == RESULT_OK){      
////		    	 imagePath = data.getStringExtra("resultOfPhoto");
////		    	 etImaegName.setText(new File(imagePath).getName());
////		     }
////		} 
////		if(requestCode == TAKE_VIDEO_CODE) {
////			if(resultCode == RESULT_OK){      
////		    	 videoPath = data.getStringExtra("resultOfVideo");
////		     }
////		} 
////		if(requestCode == TAKE_AUDIO_RECORDER) {			
////				audioPath = data.getStringExtra("AudioPath");
////				Log.d(tag, audioPath.toString());
////		}
////	} 
////
////	@Override
////	public boolean onCreateOptionsMenu(Menu menu) {
////
////		// Inflate the menu; this adds items to the action bar if it is present.
////		getMenuInflater().inflate(R.menu.add_screen, menu);
////		return true;
////	}
////
////	@Override
////	public boolean onOptionsItemSelected(MenuItem item) {
////		// Handle action bar item clicks here. The action bar will
////		// automatically handle clicks on the Home/Up button, so long
////		// as you specify a parent activity in AndroidManifest.xml.
////		int id = item.getItemId();
////		if (id == R.id.action_settings) {
////			return true;
////		}
////		return super.onOptionsItemSelected(item);
//		}
//
//
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
				
				openCamera(TAKE_PICTURE_CODE);
			} break;

			case R.id.btAddVideo : {
				
//				Intent intent = new Intent(AddScreen.this,PhotoCapture.class);
//				intent.putExtra("Video", 3);
//				startActivityForResult(intent, TAKE_VIDEO_CODE);
				findViewById(R.id.videoAddLayout).setVisibility(View.VISIBLE);
				findViewById(R.id.imagebtVideo).startAnimation(anim);
				
			} break;
			case R.id.btAddAudio : {
//				Intent intent = new Intent(AddScreen.this,Recording.class);
//				startActivityForResult(intent, TAKE_AUDIO_RECORDER);
				findViewById(R.id.imagebtAudio).startAnimation(anim);
				break;
			}
			case R.id.btCreateEvent : {
//				newRecord = new Card(etTitle.getText().toString(), etBody.getText().toString(), time.toString(), audioPath.toString(), imagePath.toString(), videoPath.toString(),latitute,longitude);
//				db.insertNewRecord(newRecord);
//				Intent intent = new Intent();
//				setResult(RESULT_OK, intent);
//				finish();
				findViewById(R.id.bt_create_effect).startAnimation(anim);
				break;
			} case R.id.twSetTime : {
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
				case R.id.btGetlocation : {
			
				Toast.makeText(getApplicationContext(), "Obtain new location", Toast.LENGTH_LONG).show();
				latituteField.setText(latitute);
			    longitudeField.setText(longitude);
				break;
			}
			case R.id.button2: {
				findViewById(R.id.body).setVisibility(View.GONE);
				findViewById(R.id.imageAddLayout).setVisibility(View.GONE);
				findViewById(R.id.videoAddLayout).setVisibility(View.GONE);
			} break;
		}
	}
//
//
//	@Override
//	public void onLocationChanged(Location location) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	@Override
//	public void onProviderEnabled(String provider) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	@Override
//	public void onProviderDisabled(String provider) {
//		// TODO Auto-generated method stub
//		
//	}

	
//	public void openNewGameDialog() {
//		
//		Builder ob = new AlertDialog.Builder(this);
//		ob.setTitle(R.string.new_game_title);
//		ob.setItems(R.array.Option_Name, new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				openCamera(which);
//			}
//		});
//		
//		inputDialog = new EditText(AddScreen.this);	
//		inputDialog.setHint("Enter Image Name Here");
//		inputDialog.setGravity(Gravity.CENTER);
//		ob.setView(inputDialog);
//		inputDialog.setBackgroundColor(color.holo_blue_bright);
//		ob.show();
//	}
//	
	public void openCamera(int which) {
		Intent intent = new Intent(AddScreen.this,PhotoCapture.class);
		intent.putExtra("key", which);
		startActivityForResult(intent, TAKE_PICTURE_CODE);
	}
//
//
//	@Override
//	public void onLocationChanged(Location location) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	@Override
//	public void onProviderEnabled(String provider) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//	@Override
//	public void onProviderDisabled(String provider) {
//		// TODO Auto-generated method stub
//		
//	}
}

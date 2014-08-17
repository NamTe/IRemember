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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddScreen extends Activity implements OnClickListener , LocationListener {

	//Button Declare
	private Button btAddPhto;
	private Button create;
	private Button btAddVideo;
	private Button btAddAudio;
	private Button btGetLocation;
	
	//Edit Text Declare
	private EditText etImaegName;
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
		setContentView(R.layout.fragment_add_screen);
		time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		btAddPhto = (Button) findViewById(R.id.btAddPhoto);
		btAddPhto.setOnClickListener(this);
		btAddVideo = (Button)findViewById(R.id.btAddVideo1);
		btAddVideo.setOnClickListener(this);
		btAddAudio = (Button) findViewById(R.id.btRecorder);
		btAddAudio.setOnClickListener(this);
		btGetLocation = (Button) findViewById(R.id.btGetlocation);
		btGetLocation.setOnClickListener(this);
		etImaegName = (EditText) findViewById(R.id.etImageName);
		etTitle = (EditText) findViewById(R.id.etTitle);
		etBody = (EditText) findViewById(R.id.etBody);
		create = (Button) findViewById(R.id.btCreate);
		create.setOnClickListener(this);
		TimeView = (TextView)findViewById(R.id.twSetTime);
		TimeView.setOnClickListener(this);
		longitudeField = (TextView)findViewById(R.id.twLongitude);
		latituteField = (TextView)findViewById(R.id.twLatitude);
		db = new DataBase(this);
		

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		latitute = "0";
	    longitude = "0";
	    // Initialize the location fields
	    location = locationManager.getLastKnownLocation(provider);
	    if (location != null) {
		      System.out.println("Provider " + provider + " has been selected.");
		      onLocationChanged(location);
		    } else {
		    	latituteField.setText("Location not available");
		    	longitudeField.setText("Location not available");
		    }
	}
	
	
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
			int lat = (int) (location.getLatitude());
		    int lng = (int) (location.getLongitude());
		    latitute = String.valueOf(lat);
		    longitude = String.valueOf(lng);
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
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == TAKE_PICTURE_CODE) {
			if(resultCode == RESULT_OK){      
		    	 imagePath = data.getStringExtra("resultOfPhoto");
		    	 etImaegName.setText(new File(imagePath).getName());
		     }
		} 
		if(requestCode == TAKE_VIDEO_CODE) {
			if(resultCode == RESULT_OK){      
		    	 videoPath = data.getStringExtra("resultOfVideo");
		     }
		} 
		if(requestCode == TAKE_AUDIO_RECORDER) {			
				audioPath = data.getStringExtra("AudioPath");
				Log.d(tag, audioPath.toString());
		}
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_screen, menu);
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
	public void onClick(View v) {
		
		switch(v.getId()) {
			case R.id.btAddPhoto : {
				openNewGameDialog();
			} break;
			case R.id.btAddVideo1 : {
				Intent intent = new Intent(AddScreen.this,PhotoCapture.class);
				intent.putExtra("Video", 3);
				startActivityForResult(intent, TAKE_VIDEO_CODE);
			} break;
			case R.id.btRecorder : {
				Intent intent = new Intent(AddScreen.this,Recording.class);
				startActivityForResult(intent, TAKE_AUDIO_RECORDER);
				break;
			}
			case R.id.btCreate : {
				newRecord = new Card(etTitle.getText().toString(), etBody.getText().toString(), time.toString(), audioPath.toString(), imagePath.toString(), videoPath.toString(),latitute,longitude);
				db.insertNewRecord(newRecord);
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
				break;
			} case R.id.twSetTime : {
				TimeView.setText(time);
				break;
			} case R.id.btCancelCreate : {
				etImaegName.setText("");
				etBody.setText("");
				etTitle.setText("");
				latituteField.setText("0");
				longitudeField.setText("0");
				break;
			}case R.id.btGetlocation : {
				Toast.makeText(getApplicationContext(), "Obtain new location", Toast.LENGTH_LONG).show();
				latituteField.setText(latitute);
			    longitudeField.setText(longitude);
				break;
			}
		}
	}

	
	public void openNewGameDialog() {
		
		Builder ob = new AlertDialog.Builder(this);
		ob.setTitle(R.string.new_game_title);
		ob.setItems(R.array.Option_Name, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				openCamera(which);
			}
		});
		
		inputDialog = new EditText(AddScreen.this);	
		inputDialog.setHint("Enter Image Name Here");
		inputDialog.setGravity(Gravity.CENTER);
		ob.setView(inputDialog);
		inputDialog.setBackgroundColor(color.holo_blue_bright);
		ob.show();
	}
	
	public void openCamera(int which) {
		Intent intent = new Intent(AddScreen.this,PhotoCapture.class);
		intent.putExtra("key", which);
		startActivityForResult(intent, TAKE_PICTURE_CODE);
	}
}

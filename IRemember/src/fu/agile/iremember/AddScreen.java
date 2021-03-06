package fu.agile.iremember;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

@SuppressLint("SimpleDateFormat")
public class AddScreen extends Activity implements OnClickListener, LocationListener{

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
	private String time = "Unkown";
	private String audioPath = "Unknow";
	private String imagePath = "Unknow";
	private String videoPath = "Unknow";
	private static String tag = "Hello";
	private String latitute;
	private String longitude;
	private String provider;
	private String tempLatitute;
	private String tempLongitude;
	//ImageView
	private ImageView imageView;
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
	private static int DATE_PICKER_ID = 111;
	private A recordAudio = new A();
	private int year = 0, month = 0, day = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		init();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the location provider -> use
	    // default
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    location = locationManager.getLastKnownLocation(provider);

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
	    Calendar c = Calendar.getInstance();
	    year = c.get(Calendar.YEAR);
	    month = c.get(Calendar.MONTH);
	    day = c.get(Calendar.DAY_OF_MONTH);
	}
////	
	
	@Override
	  protected void onResume() {
	    super.onResume();
	    locationManager.requestLocationUpdates(provider, 400, 1, this);
	  }

	  /* Remove the location listener updates when Activity is paused */
	  @Override
	  protected void onPause() {
	    super.onPause();
	    locationManager.removeUpdates(this);
	  }

	  @Override
	  public void onLocationChanged(Location location) {
		  tempLatitute = location.getLatitude() + "";
		  tempLongitude = location.getLongitude() + "";
		  
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
	
	@SuppressLint("SimpleDateFormat")
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
		etTitle.setFocusableInTouchMode(true);
		etTitle.requestFocus();
		textLocation = (TextView)findViewById(R.id.textLocation);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == TAKE_PICTURE_CODE) {
			if(resultCode == RESULT_OK){      
		    	 imagePath = data.getStringExtra("resultOfPhoto");	    
		    	 try{
		    		 Bitmap bit = BitmapFactory.decodeFile(imagePath);
		    		 imageView.setImageBitmap(bit);
		    	 } catch(Exception exc) {
		    		 
		    	 }
		     }
		} 
		if(requestCode == TAKE_VIDEO_CODE) {
			if(resultCode == RESULT_OK){      
		    	 videoPath = data.getStringExtra("resultOfVideo");
		    	 Log.d(tag, videoPath);
		     }
		} 
		if(requestCode == TAKE_AUDIO_RECORDER) {
			if(resultCode == RESULT_OK){      
		    	 Uri uri = data.getData();
		    	 Cursor cursor = getContentResolver().query(uri, new String[] { android.provider.MediaStore.Audio.AudioColumns.DATA }, null, null, null);
		    	 cursor.moveToFirst();
		    	 audioPath = cursor.getString(0);
		    	 Log.d("AudioPath",audioPath);
		     }
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
				if(recordAudio.getIsPlay() == true) {
					recordAudio.stopPlaying();
				}
				else if(audioPath.equalsIgnoreCase("Unknow") == false && audioPath != null) {
					recordAudio.setAudioPath(audioPath);
					recordAudio.startPlaying();	
				}else {
					Toast.makeText(getApplicationContext(), "You must record first", Toast.LENGTH_LONG).show();
				}
				findViewById(R.id.bt_play_audio_effect).startAnimation(anim);
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
				audioPath = recordAudio.getAudioPath();
			} break;
			case R.id.btSave: {
				findViewById(R.id.bt_save_effect).startAnimation(anim);
				OpenAudioFileToChose();
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
				
				if(etTitle.getText().toString().length() > 0 && etBody.getText().toString().length() > 0)
					try {
						newRecord = new Card(etTitle.getText().toString(), etBody.getText().toString(), audioPath.toString(), imagePath.toString(), videoPath.toString(), time.toString(),latitute,longitude);
						db.insertNewRecord(newRecord);
						Intent intent = new Intent();
						setResult(RESULT_OK, intent);
						findViewById(R.id.bt_create_effect).startAnimation(anim);
						finish();
					}catch(Exception exc) {
						Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
					}
				else {
					Toast.makeText(getApplicationContext(), "Make Sure You full fill the title field and body field", Toast.LENGTH_LONG).show();
				}
				break;
			} 
			case R.id.btAddTime : {
				findViewById(R.id.imagebtTime).startAnimation(anim);
				extracted();		
				break;
			} case R.id.btClear : {
				findViewById(R.id.title).requestFocus();
				audioPath = "unknow";
				imagePath = "unknow";
				time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				videoPath = "unknow";
				etBody.setText("");
				etTitle.setText("");
				latitute = "Location not available";
		    	longitude = "Location not available";
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
					latitute = tempLatitute;
					longitude = tempLongitude;
					textLocation.setText("Success");
				}
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void extracted() {
		showDialog(DATE_PICKER_ID);
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
	
	public void OpenAudioFileToChose() {
		Intent takeAudio ;
		if(Build.VERSION.SDK_INT < 19) {
			takeAudio = new Intent(Intent.ACTION_GET_CONTENT);
		}
		else {
			takeAudio = new Intent(Intent.ACTION_PICK);
		}
		takeAudio.setType("audio/*");
		startActivityForResult(takeAudio, TAKE_AUDIO_RECORDER);  
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
            return new DatePickerDialog(this, pickerListener, year, month,day);
    }
	
	private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
		 
        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                int selectedMonth, int selectedDay) { 
            year  = selectedYear;
            month = selectedMonth + 1;
            day   = selectedDay;
 
            // Show selected date
            time = year + "-" + month + "-" + day;
            TimeView.setText(time);
        }
	};
}
	

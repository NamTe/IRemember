package fu.agile.iremember;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddScreen extends Activity implements OnClickListener {

	Button btAddPhto;
	String imageName;
	EditText etImaegName;
	Button btAddVideo;
	Button btAddAudio;
	EditText inputDialog;
	static private int TAKE_PICTURE_CODE = 1;
	static private int TAKE_VIDEO_CODE = 2;
	static private int TAKE_AUDIO_RECORDER = 3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_add_screen);
		
		btAddPhto = (Button) findViewById(R.id.btAddPhoto);
		btAddPhto.setOnClickListener(this);
		btAddVideo = (Button)findViewById(R.id.btAddVideo1);
		btAddVideo.setOnClickListener(this);
		btAddAudio = (Button) findViewById(R.id.btRecorder);
		btAddAudio.setOnClickListener(this);
		etImaegName = (EditText) findViewById(R.id.etImageName);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == TAKE_PICTURE_CODE) {
			if(resultCode == RESULT_OK){      
		    	 imageName = data.getStringExtra("resultOfPhoto");
		    	 etImaegName.setText(imageName);
		     }
		} else if(requestCode == TAKE_VIDEO_CODE) {
	    	 
		} else if(requestCode == TAKE_AUDIO_RECORDER) {
			if(requestCode == RESULT_OK) {
				
			}
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

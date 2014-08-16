package fu.agile.iremember;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

public class MainActivity extends Activity implements  android.view.View.OnClickListener {


	Button btAdd;
	Animation animBounce;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		btAdd = (Button)findViewById(R.id.btAdd);
		btAdd.setOnClickListener(this);
		File dir = new File("/mnt/sdcard/IRemember");
		File phtotoDir = new File(getString(R.string._mnt_sdcard_iremember_photo));
		File videoDir = new File(getString(R.string._mnt_sdcard_iremember_video));
		File audioRecorder = new File(getString(R.string._mnt_sdcard_iremember_audio));
		if(dir.isDirectory() == false) {
			createNewDir(dir);
		}
		if(phtotoDir.isDirectory() == false) {
			createNewDir(phtotoDir);
		}
		if(videoDir.isDirectory() == false) {
			createNewDir(videoDir);
		}
		if(audioRecorder.isDirectory() == false) {
			createNewDir(audioRecorder);
		}
	}
	
	public void createNewDir(File newDir) {
		try {
			newDir.mkdir();
		} catch(Exception exc) {
			Toast.makeText(getApplicationContext(), "Error with sdcard " + newDir.getName(), Toast.LENGTH_LONG).show();
			finish();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) { 
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.btAdd: {
				animBounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
				findViewById(R.id.btAdd).startAnimation(animBounce);
				Intent intent = new Intent(MainActivity.this,AddScreen.class);
				startActivity(intent);
			}break;
	
			default:
				break;
		}
	}


}

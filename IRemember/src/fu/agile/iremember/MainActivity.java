package fu.agile.iremember;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements  android.view.View.OnClickListener {


	DataBase db;
	private ImageButton btAdd;
	private Animation anim;
	private ListView lw;
	private List<Card> RecordList;
	private List<String> stringAdapter;
	private AutoCompleteTextView autoComplete;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		btAdd = (ImageButton)findViewById(R.id.btAdd);
		btAdd.setOnClickListener(this);
		File dir = new File(getString(R.string._mnt_sdcard_iremember));
		lw = (ListView)findViewById(R.id.lwListRemember);
		File phtotoDir = new File(getString(R.string._mnt_sdcard_iremember_photo));
		File videoDir = new File(getString(R.string._mnt_sdcard_iremember_video));
		File audioRecorder = new File(getString(R.string._mnt_sdcard_iremember_audio));
		anim = AnimationUtils.loadAnimation(this, R.anim.zoom_animation);
		autoComplete = (AutoCompleteTextView)findViewById(R.id.etFilter);
		stringAdapter = new ArrayList<String>();
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
		
		db = new DataBase(this);
		RecordList = new ArrayList<Card>();
		RecordList = db.getAllRecords();
		autoComplete.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				displaySelect(arg2);
			}
		});
		
		autoComplete.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				display();
				return false;
			}
		});
		display();
		getStringAdapter();
		
		
	}
	
	public void getStringAdapter() {
		for(Card d : RecordList) { 
			stringAdapter.add(d.getTitle());
			Log.d("A", stringAdapter.get(0));
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,stringAdapter);
		autoComplete.setAdapter(adapter);
	}
	
	public void displaySelect(int position) {
		List<Card> list = new ArrayList<Card>();
		list.add(RecordList.get(position));
		Log.d("A", list.get(0).getTitle());
		MyAdapter adapter = new MyAdapter(this, R.layout.list_customise, list);
		lw.setAdapter(adapter);
	}
	
	public void display() {			
		MyAdapter adapter = new MyAdapter(this, R.layout.list_customise, RecordList);
		lw.setAdapter(adapter);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(resultCode == RESULT_OK) {
			display();
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
				findViewById(R.id.bt_add_effect).startAnimation(anim);
				Intent intent = new Intent(MainActivity.this,AddScreen.class);
				startActivityForResult(intent, 0);
			
			}break;
	
			default:
				break;
		}
	}


}

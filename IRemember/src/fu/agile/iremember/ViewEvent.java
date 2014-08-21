package fu.agile.iremember;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ViewEvent extends Activity implements OnClickListener {

	//Button Declare
	private ImageButton btEdit;
	private ImageButton btDelete;
	private ImageButton btBack;
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
	private String latitute;
	private String longitude;
	private String provider;
	private TimePicker timePicker;
	
	//Animation Declaration
	private Animation anim;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_event);
		intit();  
	}

	private void intit() {
		btEdit = (ImageButton) findViewById(R.id.btEditEvent);
		btEdit.setOnClickListener(this);
		btDelete = (ImageButton) findViewById(R.id.btDelete);
		btDelete.setOnClickListener(this);
		btBack = (ImageButton) findViewById(R.id.btBack);
		btBack.setOnClickListener(this);
		anim = AnimationUtils.loadAnimation(this, R.anim.zoom_animation);
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()) {

			case R.id.btEditEvent : {
				findViewById(R.id.bt_edit_effect).startAnimation(anim);
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
			
		}
	}

}

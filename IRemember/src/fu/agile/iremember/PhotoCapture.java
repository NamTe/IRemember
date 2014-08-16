package fu.agile.iremember;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

public class PhotoCapture extends Activity implements OnClickListener {

	static final int REQUEST_IMAGE_CAPTURE = 1;
	static final int REQUEST_TAKE_PHOTO = 1;
	static final int REQUEST_PICK_PHTO = 100;
	static final int ACTION_TAKE_VIDEO = 2;
	Button btSave;
	String photoPath;
	String videoPath;
	ImageView mImageView;
	VideoView mVideoView;
	int caseIntent;
	Uri mVideoUri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_capture);
		mImageView = (ImageView) findViewById(R.id.mImageView);
		mVideoView = (VideoView) findViewById(R.id.videoView1);
		btSave = (Button) findViewById(R.id.btSaveImage);
		btSave.setOnClickListener(this);
		Intent intent = getIntent();
		caseIntent = intent.getIntExtra("key",-1) == -1 ? intent.getIntExtra("Video", -1) : intent.getIntExtra("key",-1);
		if(caseIntent == 1) {
			takePicture();
		} else if(caseIntent == 0) {
			getPictureFormGallery();
		} else {
			TakeVideoIntent();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			setPic();
	    }
		else if(requestCode == REQUEST_PICK_PHTO && data != null && data.getData() != null) {
	        Uri _uri = data.getData();
	        //User had pick an image.
	        Cursor cursor = getContentResolver().query(_uri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
	        cursor.moveToFirst();
	        //Link to the image
	        photoPath = cursor.getString(0);
	        cursor.close();
	        Bitmap bit = BitmapFactory.decodeFile(photoPath);
	        mImageView.setImageBitmap(bit);
	    } else if(requestCode == ACTION_TAKE_VIDEO && data != null && data.getData() != null) {
	    	mVideoUri = data.getData();
			mVideoView.setVideoURI(mVideoUri);
			Log.d("VIDEO", videoPath);
			mVideoView.setVisibility(View.VISIBLE);
			mImageView.setVisibility(View.INVISIBLE);
			mVideoView.start();
	    }
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_capture, menu);
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
	
	public void takePicture() {
		File photoFile = null;
		try {
			photoFile = createNewPhotoFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "Can not use datastorage", Toast.LENGTH_LONG).show();
			finish();
		}
		Intent takePictureByIntend = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if(takePictureByIntend.resolveActivity(getPackageManager()) != null) {
			takePictureByIntend.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
			startActivityForResult(takePictureByIntend, REQUEST_IMAGE_CAPTURE);
			galleryAddPic();
		}
	}
	
	public File createNewPhotoFile() throws IOException {
		String photoTempName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String photoName = "AD_" + photoTempName + "_";
		File dir = new File(getString(R.string._mnt_sdcard_iremember_photo));
		File image = File.createTempFile(photoName, ".JPG",dir);
		photoPath = image.getAbsolutePath();
		return image;
	}
	
	
	public File createVideoFile() throws IOException {
		String photoTempName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String photoName = "AD_VD_" + photoTempName + "_";
		File dir = new File(getString(R.string._mnt_sdcard_iremember_video));
		File image = File.createTempFile(photoName, ".mp4",dir);
		return image;
	}
	
	private void setPic() {
		Bitmap bit = BitmapFactory.decodeFile(photoPath);
        mImageView.setImageBitmap(bit);
	}
	
	public void getPictureFormGallery () {
		Intent i = new Intent(Intent.ACTION_PICK,
	               android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, REQUEST_PICK_PHTO);  
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
			case R.id.btSaveImage : {
				Intent returnIntent = new Intent();
				if(photoPath != null) {
					returnIntent.putExtra("resultOfPhoto",photoPath);
				} else {
					returnIntent.putExtra("resultOfVideo",photoPath);
				}
				
				setResult(RESULT_OK,returnIntent);     
				finish();
				break;
			}
		}
	}
	
	private void galleryAddPic() {
	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    File f = new File(photoPath);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
	}
	
	private void TakeVideoIntent() {
		File Video = null;
		try {
			Video = createVideoFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), "Can not use datastorage", Toast.LENGTH_LONG).show();
			finish();
		}
		Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(Video));
		startActivityForResult(takeVideoIntent, ACTION_TAKE_VIDEO);
		videoPath = Video.getAbsolutePath();
	}
	
}

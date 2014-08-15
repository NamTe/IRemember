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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoCapture extends Activity implements OnClickListener {

	static final int REQUEST_IMAGE_CAPTURE = 1;
	static final int REQUEST_TAKE_PHOTO = 1;
	static final int REQUEST_PICK_PHTO = 100;
	Button btSave;
	String photoPath;
	ImageView mImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_capture);
		mImageView = (ImageView) findViewById(R.id.mImageView);
		btSave = (Button) findViewById(R.id.btSaveImage);
		btSave.setOnClickListener(this);
		Intent intent = getIntent();
		if(intent.getIntExtra("key", 0) == 1) {
			takePicture();
		} else {
			getPictureFormGallery();
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
		File dir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(photoName, ".JPG",dir);
		photoPath = image.getAbsolutePath();
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
				 returnIntent.putExtra("result",photoPath);
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
	
}

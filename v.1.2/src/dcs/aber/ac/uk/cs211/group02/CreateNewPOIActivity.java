package dcs.aber.ac.uk.cs211.group02;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class CreateNewPOIActivity extends Activity {

	private Context context;
	private ImageButton helpButton;
	private Button createButton, addImage;
	private String timeStamp;

	private File photoFile = null;

	private double lat, lng;
	private String photoPath;

	static final int REQUEST_TAKE_PHOTO = 1;
	static final int REQUEST_GALLERY_PHOTO = 2;

	private EditText nameEditText, descEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_poi);
		context = this;

		Bundle b = this.getIntent().getExtras();
		if (b != null){
			lng = b.getDouble("long");
			lat = b.getDouble("lat");
			timeStamp = b.getString("timestamp");

		}

		setEditText();
		addOnClickListeners();

	}


	public void setEditText() {

		nameEditText = (EditText) findViewById(R.id.newPOINameEditText);
		descEditText = (EditText) findViewById(R.id.newPOIDescEditText);		

	}

	public void addOnClickListeners() {


		helpButton = (ImageButton) findViewById(R.id.newPOIHelpButton);

		helpButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(context, HelpScreen.class);
				startActivity(intent);   

			}

		});


		createButton = (Button) findViewById(R.id.newPOICreateButton);

		createButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				addPointOfInterest(lat, lng, timeStamp);

			}

		});

		addImage = (Button) findViewById(R.id.newPOIAddPhotoButton);

		addImage.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v){
				buildImageSelectionPrompt();

			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_new_poi, menu);
		return true;
	}

	public String getWPOINameText() {

		return nameEditText.getText().toString();
	}

	public String getPOIDescText() {

		return descEditText.getText().toString();
	}

	public void addPointOfInterest(double lat, double lng, String tStamp){


		PointOfInterest p = new PointOfInterest(getWPOINameText(),getPOIDescText(), lat	, lng, tStamp);
		if(photoFile != null){
			p.addImage(photoFile);

		}

		Intent resultIntent = new Intent();
		resultIntent.putExtra("POIObject", p);
		setResult(RESULT_OK, resultIntent);
		finish();

	}

	public void buildImageSelectionPrompt(){

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which){
				case DialogInterface.BUTTON_POSITIVE:
					dispatchTakePictureIntent();
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					dispatchGalleryIntent();
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Which method do you want to use?").setPositiveButton("Camera"
				, dialogClickListener)
				.setNegativeButton("Device Gallery", dialogClickListener).show();
	}

	/*
	 * 
	 * !!***************************IMPORTANT CREDITATION************************************!!
	 * 
	 * the code related to acessing th devices/storages and taking photo's using the camera app
	 * was accomplished using the code provided by the Android creators/API'S. This code
	 * has been adapted slightly, and the original can be found at the following URL:
	 * 
	 * http://developer.android.com/training/camera/photobasics.html
	 * 
	 * The above link/below code, was cited on January the 6th 2014. The content maybe
	 * subject to modfication/deletion at any time without prior notice.
	 * 
	 */


	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				Toast IOEx = Toast.makeText(context, "error making image file", 
						Toast.LENGTH_SHORT);
				IOEx.show();
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
				startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

			}
		}
	}


	private File createImageFile() throws IOException {

		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
				);

		// Save a file: path for use with ACTION_VIEW intents
		photoPath = "file:" + image.getAbsolutePath();

		return image;
	}

	/*
	 *  !!************************** END OF CREDITATION ***********************!!
	 * 
	 */

	public void dispatchGalleryIntent(){
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, REQUEST_GALLERY_PHOTO);


	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if(requestCode == REQUEST_GALLERY_PHOTO){
			if (resultCode == RESULT_OK) {
				Uri photoUri = intent.getData();

				if (photoUri != null) {
					try {
						photoPath = getRealPathFromURI(context, photoUri);
						photoFile = new File(photoPath);
						Toast.makeText(context, "photo uri: " + photoPath,
								Toast.LENGTH_SHORT).show();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/*
	 * This method is vital when passing the file returned from the gallery 
	 * image selection process. Initially i did not use a Cursor and content
	 * resolver queries, thus returning incorrect path to images.
	 * 
	 *  The following solution is a variation of:
	 *  
	 *  http://stackoverflow.com/a/3414749/2942536

	 */

	public String getRealPathFromURI(Context context, Uri contentUri) {
		Cursor cursor = null;
		try { 
			String[] proj = { MediaStore.Images.Media.DATA };
			cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
}

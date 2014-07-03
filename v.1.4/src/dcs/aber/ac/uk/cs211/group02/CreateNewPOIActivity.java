package dcs.aber.ac.uk.cs211.group02;

/**
 * 
 * This code is for use by Group02 Only and can be contacted either via e-mail on:
 * 
 * che16@aber.ac.uk or cs-group02@aber.ac.uk
 * 
 * This Activity provides a user-friendly interface for the creation of new 
 * PointsOfInterest at any time in the recording of the current walk.
 * 
 * This Activity is only executed when it is started for result by the WalkRecording
 * Activity. This chain of events should NOT be changed.
 * 
 * @author Chris Edwards as part of the Aberystwyth University
 * 2013-2014 CS22120 Group project: "Walking Tour Creator"
 * 
 */

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
import android.text.InputFilter;
import android.text.Spanned;
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

	/*Used for the image file storage from camera of gallery*/
	private File photoFile = null;

	private double lat, lng;
	private String photoPath;

	/* Used as part of the startActivityForResult syntax, these
	 * can used to to identify which Activity has just been executed*/
	private static final int REQUEST_TAKE_PHOTO = 1;
	private static final int REQUEST_GALLERY_PHOTO = 2;

	private EditText nameEditText, descEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_poi);
		context = this;

		/*If no extras are passed to this Activity via intents, don't execute the code*/
		Bundle b = this.getIntent().getExtras();
		if (b != null){
			lng = b.getDouble("long");
			lat = b.getDouble("lat");
			timeStamp = b.getString("timestamp");

		}

		setEditText();
		addOnClickListeners();

	}


	/**
	 *This method simply located the user interface elements which are
	 *EditText form widgets, and assigns them to global variables. This prevents
	 *NullPointerExceptions. 
	 * 
	 */

	public void setEditText() {

		nameEditText = (EditText) findViewById(R.id.newPOINameEditText);
		descEditText = (EditText) findViewById(R.id.newPOIDescEditText);
		setFilters();

	}

	/**
	 *This method is designed to enable the user interface to be interactive.
	 *It first locates the UI elements (Buttons) and correct associates them
	 *with the desired onClickListener and resulting action. 
	 * 
	 * Any newly added UI elements that require onClickEvents should be handled
	 * within this method.
	 * 
	 */

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

				/*make new PointOfInterest via a new Activity*/
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


	/**
	 * This method returns the current PointOfInterests name.
	 * 
	 * @return String - the contents of the name EditText
	 */

	public String getWPOINameText() {

		return nameEditText.getText().toString();
	}

	/**
	 * This method returns the current PointOfInterests description.
	 * 
	 * @return String - the contents of the name EditText
	 */

	public String getPOIDescText() {

		return descEditText.getText().toString();
	}


	/**
	 * This method is used to create a new PointOfInterest which will be added to
	 * the Vector<PointOfInterest>. As this Activity is expected to return as result
	 * to the WalkRecording Activity a result must first be set. This set result is
	 * a parsable PointOfInterest Object, which has the name, description and image
	 * Specified by the user and the GPS coordinates returned based on the devices current
	 * location.
	 * 
	 * @param double - the latitude for the PointOfInterest
	 * @param double - the longitude for the POintOfInterest
	 * @param String - the time stamp for the point of interest in String format
	 */


	public void addPointOfInterest(double lat, double lng, String tStamp){


		if(checkInputLength(getWPOINameText()) && checkInputLength(getPOIDescText()) && photoFile != null){
			PointOfInterest p = new PointOfInterest(getWPOINameText(),getPOIDescText(), lat	, lng, tStamp);

			p.addImage(photoFile);


			/*Set the result, because WalkRecording is expecting one*/
			Intent resultIntent = new Intent();
			resultIntent.putExtra("POIObject", p);
			setResult(RESULT_OK, resultIntent);
			finish();
		}
		else{
			if(photoFile == null){
				Toast.makeText(context, "You MUST attach an image", Toast.LENGTH_SHORT).show();

			}else{
				Toast.makeText(context, "You cannot have empty fields", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * This method displays a alertDialog prompt with negative/positive 
	 * user interaction options. This DialogInterface has its on onClickListener
	 * and performs different actions depending on the nature of the
	 * selected response (the negative response has a different set of results
	 * to the positive response).
	 * 
	 *The "positive" response in this aspect is that the user will choose to take a new
	 *photo using the devices camera
	 *
	 *The "negative" response in this aspect is that the user will choose to select a
	 *existing image from the devices gallery.
	 *
	 */

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
	 * the code related to accessing the devices/storages and taking photo's using the camera app
	 * was accomplished using the code provided by the Android creators/API'S. This code
	 * has been adapted slightly, and the original can be found at the following URL:
	 * 
	 * http://developer.android.com/training/camera/photobasics.html
	 * 
	 * The above link/below code, was cited on January the 6th 2014. The content maybe
	 * subject to modification/deletion at any time without prior notice.
	 * 
	 */

	/**
	 * This method is used when the user opts to take a new image using the devices
	 * built in camera application. Before you can take and return the image, you must
	 * first create a new File to store this image in (using createImaegFile()). Once you 
	 * have done this you start the Camera Activity using startActivityForResult, then 
	 * this returned image is now the value of the created File you made earlier. 
	 * This created File is very important and the code associated with this method 
	 * should not be Adapted.
	 * 
	 * 
	 * @throws IOException
	 * @see IOException
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

	/**
	 * This method is called by the dispatchTakePictureIntent and is required if
	 * you want to be able to take and save a new picture using the Camera Activity.
	 * 
	 * It creates a new file and suffixes the file with the .jpg extension and
	 * prefixes this extension with the current time and date.
	 * 
	 * This File is then stored in the devices external media storage.
	 * 
	 * @return File - the created Image File
	 * @throws IOException
	 * @see IOException
	 */

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


	/**
	 * If the user opts not to take a new photo using the Camera Activity, then 
	 * they can use an exiting image from the devices gallery. In order to do this
	 * you has to dispatch a new intent which points to the devices gallery, and 
	 * specify that you are only interested in images (setType("image/*");
	 * 
	 * Once you have done the above, you can start the gallery Activity for result,
	 * and return the chosen image.
	 * 
	 * 
	 */

	public void dispatchGalleryIntent(){
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, REQUEST_GALLERY_PHOTO);


	}

	/**
	 * This method handles the returned information from the Activities that
	 * were started using startActivityForResult. These activities are expected
	 * to return some useful information so it is important to handle this information
	 * Efficiently.
	 * 
	 * Firstly a check is performed to determine the source of the response, then you
	 * must check if the Activity completed successfully using RESULT_OK checks against
	 * the resultCode.
	 * 
	 * Because the Gallery is the only Activity we are expecting a result from in this 
	 * instance, it is fairly to handle the received data. 
	 * 
	 * IMPORTANT any Activities started
	 * using startActivityForResult must have their returned data handled in this method.
	 * 
	 * 
	 */

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if(requestCode == REQUEST_GALLERY_PHOTO){
			if (resultCode == RESULT_OK) {
				Uri photoUri = intent.getData();

				if (photoUri != null) {
					try {
						/*Get the actual file path, so we locate it and pass it 
						 * around activities*/
						photoPath = getRealPathFromURI(context, photoUri);
						photoFile = new File(photoPath);
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

	/**
	 * This method is of upmost importance when dealing with the returned data
	 * from the Gallery intent. In order to correctly handle the returned image and
	 * pass it's path between activities you need to return its actual absolute path
	 * rather than the relative path it provides by default.
	 * 
	 * In order to do this you need to use a Cursor which is used to isolate data
	 * and then scan through the media store image directory until you have retrieved the
	 * full absolute file path of the file you are evaluating
	 * 
	 * 
	 * @param Context 
	 * @param Uri - the Uri to the image you are evaluating
	 * @return String - the actual absolute file path
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

	/**
	 * A method to check that the entered details are not empty.
	 * The entered string has to have a length of > 0
	 * 
	 * @param Sring - the input to check
	 * @return boolean - does the String have length > 0
	 */
	public boolean checkInputLength(String str){
		if(str.length() > 0){

			return true;

		}

		return false;
	}

	public void setFilters(){

		InputFilter charcterRestriction = new InputFilter() {

			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				for (int i = start; i < end; i++) { 
					if (!Character.isLetterOrDigit(source.charAt(i))) {
						if(source.charAt(i) != ' '){

							Toast.makeText(context, "Invalid input. You must use "
									+ "alpha numeric characters", Toast.LENGTH_SHORT).show();

							return ""; 
						} 
					}
				} 
				return null;
			}
		};
		
		/**
		 * 
		 * Apply the Functional Requirements restrictions to these EditTexts
		 */

		InputFilter charcterRestrictionOneWord = new InputFilter() {

			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				for (int i = start; i < end; i++) { 
					if (!Character.isLetterOrDigit(source.charAt(i))) {


						Toast.makeText(context, "Invalid input. You must use "
								+ "alpha numeric characters", Toast.LENGTH_SHORT).show();

						return ""; 

					}
				} 
				return null;
			}
		};

		nameEditText.setFilters(new InputFilter[]{charcterRestrictionOneWord, new InputFilter.LengthFilter(15)});
		descEditText.setFilters(new InputFilter[]{charcterRestriction, new InputFilter.LengthFilter(100)});
		
	}
}

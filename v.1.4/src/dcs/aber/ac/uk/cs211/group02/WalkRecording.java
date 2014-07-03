package dcs.aber.ac.uk.cs211.group02;

/**
 * 
 * This code is for use by Group02 Only and can be contacted either via e-mail on:
 * 
 * che16@aber.ac.uk or cs-group02@aber.ac.uk
 * 
 * This Activity provides the main Functionality and linking between the rest of
 * the Application's Activities. This Activity houses the GoogleMap Object aswell
 * as providing the ability to add PointOfInterests to the walk and also edit/Upload 
 * the walks information.
 * 
 * @author Chris Edwards as part of the Aberystwyth University
 * 2013-2014 CS22120 Group project: "Walking Tour Creator"
 * 
 */

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class WalkRecording extends FragmentActivity {

	private static String walkName, walkSDesc, walkLdesc;

	private  Location location; 
	private  LocationManager locationManager; 
	private GoogleMap map;
	private Context context;
	private Button newPOIButton, uploadButton, editButton;
	private ImageButton helpButton;
	private static Vector<PointOfInterest> pois = new Vector<PointOfInterest>();

	private Runnable task;
	private Handler handler;

	private double longitude,latitude;
	private int secs, mins, hours, millis;
	private String timeString;
	private long startTime;

	private static final int UPLOAD_REQUEST = 3;

	private TextView walkTitleTextView, timerTextView;


	private boolean stopCondition;


	private final static int NEW_POI_REQ = 0;
	private final static int EDIT_WALK_DETAILS = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_walk_recording);
		context = this;
		stopCondition = false;

		/*Retrieving passed in bundle extras*/
		Bundle bundle = getIntent().getBundleExtra("Walk info");
		walkTitleTextView = (TextView) findViewById(R.id.walkRecordingWalkTitleTextView);
		timerTextView = (TextView) findViewById(R.id.timerTextView);


		/*if there are no extras then do no execute this code, the app will crash*/
		if(bundle != null){

			walkName = bundle.getString("walkTitle");
			walkSDesc = bundle.getString("walkSDesc");
			walkLdesc = bundle.getString("walkLDesc");
			walkTitleTextView.setText(walkName);

		}

		/*Use LocationManger to find the starting location, this will be used to display
		 * the correct local map of the users surrounding area on start up*/

		locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
		location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		latitude = location.getLatitude();
		longitude = location.getLongitude();

		map=((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapView)).getMap();

		map.setMyLocationEnabled(true);

		CameraUpdate position = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 16.0f); 

		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.moveCamera(position);

		addOnClickListeners();
		startCountingTimer();


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

	public void addOnClickListeners(){

		helpButton = (ImageButton) findViewById(R.id.walkRecordingHelpButton);

		helpButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(context, HelpScreen.class);
				startActivity(intent);   

			}

		});

		newPOIButton = (Button) findViewById(R.id.walkRecordingNewPOIButton);

		newPOIButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/* Hack to get location updates working, found online 
				 * google services to be very "buggy" this is not the most 
				 * elegant solution, but it is a solution.
				 * 
				 * */

				locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
				location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				latitude = location.getLatitude();
				longitude = location.getLongitude();

				Intent intent = new Intent(context, CreateNewPOIActivity.class);
				intent.putExtra("long", longitude);
				intent.putExtra("lat", latitude);
				intent.putExtra("timestamp", getTimeStamp());
				startActivityForResult(intent, NEW_POI_REQ);   

			}

		});

		uploadButton = (Button) findViewById(R.id.walkRecordingUploadButton);

		uploadButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v){

				Intent intent = new Intent(context, ConfrimUploadActivity.class);

				intent.putExtra("walk name", walkName);
				intent.putExtra("walk short", walkSDesc);
				intent.putExtra("walk long", walkLdesc);
				startActivityForResult(intent, UPLOAD_REQUEST);
			}
		});

		editButton = (Button) findViewById(R.id.walkRecordingEditButton);

		editButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v){

				Intent intent = new Intent(context, EditWalksInfoActivity.class);

				intent.putExtra("walk name", walkName);
				intent.putExtra("walk short", walkSDesc);
				intent.putExtra("walk long", walkLdesc);
				startActivityForResult(intent, EDIT_WALK_DETAILS);
			}


		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.walk_recording, menu);
		return true;
	}

	/**
	 * Retrieve static Vector<POintsOfInterest> to store all the walks locations.
	 * This is static so it can easily be accessed by other activities, without
	 * having to pass around large amounts of data through intents
	 * 
	 * @return Vector<PointsOfInterest> - the Vector which stores the walks locations
	 */

	public static Vector<PointOfInterest> getPois() {
		return pois;
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
	 * There are several activities dealt with in this method, the conditional checks are in
	 * place to prevent app crashing and data loss/misuse. do not remove this conditional
	 * checks.
	 * 
	 * IMPORTANT any Activities started
	 * using startActivityForResult must have their returned data handled in this method.
	 * 
	 * The constants represented in all CAPS are defined as global static final integers. If
	 * you update or maintain this code, please do the same.
	 * 
	 */

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		/*Create POI Activity*/
		if(requestCode == NEW_POI_REQ){
			if( resultCode == RESULT_OK ) {
				PointOfInterest p = data.getExtras().getParcelable("POIObject");

				pois.add(p);
				addPOIToMap(p);;
			}
		}
		/*If result is from EditWalkActivity*/
		else if(requestCode == EDIT_WALK_DETAILS){
			if( resultCode == RESULT_OK ) {

				walkName = data.getExtras().getString("newName");
				walkSDesc = data.getExtras().getString("newSDesc");
				walkLdesc = data.getExtras().getString("newLDesc");
				walkTitleTextView.setText(walkName);


			}

		}

		/*from the confirmUplod Activity */
		else if(requestCode == UPLOAD_REQUEST){
			deleteWalk();

		}
	}

	/**
	 * Return the walk's currently assigned name
	 * 
	 * @return String - the walks current name
	 */

	public static String getWalkName() {
		return walkName;
	}


	/**
	 * Sets the walks current name
	 * 
	 * @param String - the name you wish to set
	 */

	public static void setWalkName(String walkName) {
		WalkRecording.walkName = walkName;
	}

	/**
	 * Returns the walks current sort description
	 * 
	 * @return String - the walks short description
	 */

	public static String getWalkSDesc() {
		return walkSDesc;
	}

	/**
	 * Sets the walks current short description value
	 * 
	 * @param String - the description you wish to set
	 */

	public static void setWalkSDesc(String walkSDesc) {
		WalkRecording.walkSDesc = walkSDesc;
	}

	/**
	 * Returns the walks current long description value
	 * 
	 * @return String - the walks Long description
	 */

	public String getWalkLdesc() {
		return walkLdesc;
	}


	/**
	 * Sets the value of the walks current long description
	 * 
	 * @param String - the walks long description
	 */

	public void setWalkLdesc(String walkLdesc) {
		WalkRecording.walkLdesc = walkLdesc;
	}

	/**
	 * This method adds points of interest to the GoogleMap based on the devices
	 * current GPS coordinates. It also creates a BitMap image based on the image
	 * file associated with the PointOfInterest and assigns this image to a 
	 * GoogleMap Marker. This Marker is then displayed on the map with
	 * the relevant information
	 * 
	 * @param PointOfInterest - The location which you want to add to the map
	 */

	public void addPOIToMap(PointOfInterest p){

		Bitmap myBitmap = BitmapFactory.decodeFile(p.getImage().getAbsolutePath());
		Bitmap result = getResizedBitmap(myBitmap, 100,100);
		LatLng loc = new LatLng(p.getLongitude(), p.getLattitude());


		map.addMarker(new MarkerOptions()
		.position(loc)
		.title(p.getName())
		.snippet(p.getDescription() + " " + p.getTimeStamp())
		.icon(BitmapDescriptorFactory.fromBitmap(result))
		.draggable(false));

	}

	/**
	 * The method resizes a bitmap based on the given parameters. This is used
	 * when creating the Bitmap for the GoogleMap Marker as a full size image returned
	 * is far too big to comfortably fit on the GoogleMap.
	 * 
	 * @param Bitmap - the Bitmap you wish to resize
	 * @param int - the resulting height of the resized Bitmap 
	 * @param int - the resulting width of the resized Bitmap 
	 * @return Bitmap - the resulting resized Bitmap
	 */
	public Bitmap getResizedBitmap(Bitmap bitmap, int resultHeight, int resultWidth) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float scaleWidth = ((float) resultHeight) / width;
		float scaleHeight = ((float) resultWidth) / height;

		// used in the manipulation
		Matrix matrix = new Matrix();

		matrix.postScale(scaleWidth, scaleHeight);

		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
		return resizedBitmap;

	}

	/**
	 * This method is used to track the amount of time elapsed during the walk.
	 * This method uses the runnable interfaces to execute an incremental timer
	 * every second, to give the illusion that there is an actual clock running.
	 * 
	 * It uses the systems time in milliseconds to perform is basic calculations
	 * using the division and modulus operators. The result of this calculate is then
	 * converted into the String format so it can easily be passed around, and easily
	 * be split back into integer or floating points.
	 * 
	 */

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void startCountingTimer() {

		startTime = System.currentTimeMillis();

		handler = new Handler();
		task = new Runnable() { 
			@Override
			public void run() {

				if(!stopCondition){
					millis = (int) (System.currentTimeMillis() - startTime);

					secs = millis / 1000 % 60;
					mins = ((millis / 1000) / 60) % 60;
					hours = ((millis / 1000) / 60) / 60;

					timeString = String.format("%02d:%02d:%02d",hours, mins, secs);
					timerTextView.setText(timeString);
					handler.postDelayed(task, 1000); 
				}
			}
		};
		task.run();
	}

	/**
	 * Returns the current elapsed time for the walk.
	 * 
	 * @return String - the current time stamp
	 */

	public String getTimeStamp() {

		return timeString;
	}

	/**
	 * Stops the timer from executing, by updating the stop condition
	 * to be true.
	 * 
	 */

	public void stopTimer() {

		stopCondition = true;

	}

	/**
	 * Deleting the waking is a functional requirement. In order to achieve this
	 * you have to clear the GoogleMap, delete all elements from the 
	 * Vector<PointsOfInterest> and reset all of the walks variables to empty
	 * Strings, as well as stopping the timer.
	 * 
	 * Any new variables/walk credentials that need to be deleted must be 
	 * added to this method.
	 * 
	 */

	public void deleteWalk() {

		map.clear();
		pois.removeAllElements();
		walkName = "";
		walkSDesc = "";
		walkLdesc = "";
		stopTimer();
		timeString = "00:00:00";
		timerTextView.setText(timeString);


	}
	
	/**
	 * Returns the current GoogleMap Object
	 * 
	 * @return GoogleMap - the current GoogleMap
	 */
	public GoogleMap getMap() {
		
		return map;
		
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		stopTimer();

	}

}
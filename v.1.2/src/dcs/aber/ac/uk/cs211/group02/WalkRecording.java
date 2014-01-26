package dcs.aber.ac.uk.cs211.group02;

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
import android.widget.Toast;

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

		Bundle bundle = getIntent().getBundleExtra("Walk info");
		walkTitleTextView = (TextView) findViewById(R.id.walkRecordingWalkTitleTextView);
		timerTextView = (TextView) findViewById(R.id.timerTextView);



		if(bundle != null){

			walkName = bundle.getString("walkTitle");
			walkSDesc = bundle.getString("walkSDesc");
			walkLdesc = bundle.getString("walkLDesc");
			walkTitleTextView.setText(walkName);

		}

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

	public static Vector<PointOfInterest> getPois() {
		return pois;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == NEW_POI_REQ){
			if( resultCode == RESULT_OK ) {
				PointOfInterest p = data.getExtras().getParcelable("POIObject");

				pois.add(p);
				addPOIToMap(p);;
			}
		}
		else if(requestCode == EDIT_WALK_DETAILS){
			if( resultCode == RESULT_OK ) {

				walkName = data.getExtras().getString("newName");
				walkSDesc = data.getExtras().getString("newSDesc");
				walkLdesc = data.getExtras().getString("newLDesc");
				walkTitleTextView.setText(walkName);


			}

		}
		else if(requestCode == UPLOAD_REQUEST){
			deleteWalk();

		}
	}

	public static String getWalkName() {
		return walkName;
	}


	public static void setWalkName(String walkName) {
		WalkRecording.walkName = walkName;
	}


	public static String getWalkSDesc() {
		return walkSDesc;
	}


	public static void setWalkSDesc(String walkSDesc) {
		WalkRecording.walkSDesc = walkSDesc;
	}


	public static String getWalkLdesc() {
		return walkLdesc;
	}


	public static void setWalkLdesc(String walkLdesc) {
		WalkRecording.walkLdesc = walkLdesc;
	}

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

	public String getTimeStamp() {

		return timeString;
	}

	public void stopTimer() {

		stopCondition = true;

	}

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

	@Override
	public void onDestroy(){
		super.onDestroy();
		stopTimer();

	}

}
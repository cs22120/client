package dcs.aber.ac.uk.cs211.group02;

import java.util.Vector;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class WalkRecording extends FragmentActivity {

	private static String walkName, walkSDesc, walkLdesc;


	private GoogleMap map;
	private Context context;
	private Button newPOIButton, uploadButton;
	private ImageButton helpButton;
	private static Vector<PointOfInterest> pois = new Vector<PointOfInterest>();

	private CreateNewPOIActivity POIAct;

	private final static int NEW_POI_REQ = 0;
	private final static int EDIT_WALK_DETAILS = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_walk_recording);
		context = this;

		Bundle bundle = getIntent().getBundleExtra("Walk info");

		if(bundle != null){

			this.walkName = bundle.getString("walkTitle");
			this.walkSDesc = bundle.getString("walkSdesc");
			this.walkLdesc = bundle.getString("walkLDesc");

		}


		map=((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapView)).getMap();

		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		POIAct = new CreateNewPOIActivity();

		addOnClickListeners();
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

				Intent intent = new Intent(context, CreateNewPOIActivity.class);
				startActivityForResult(intent, NEW_POI_REQ);   

			}

		});

		uploadButton = (Button) findViewById(R.id.walkRecordingUploadButton);

		uploadButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v){

				Intent intent = new Intent(context, ConfrimUploadActivity.class);
				Bundle b = new Bundle();


				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.walk_recording, menu);
		return true;
	}


	public void uploadTosever() {




	}

	public void deleteWalk() {


	}

	public double getLongitude() {

		return 0;
	}

	public double getLattitude() {

		return 0;

	}


	public static Vector<PointOfInterest> getPois() {
		return pois;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == NEW_POI_REQ){
			if( resultCode == RESULT_OK ) {
				PointOfInterest p = data.getExtras().getParcelable("POIObject");
				Toast lol = Toast.makeText(context, p.getName(), Toast.LENGTH_LONG);
				lol.show();
				pois.add(p);
				Toast x = Toast.makeText(context, "POIS SIZE " + pois.size() , Toast.LENGTH_LONG);
				x.show();

			}
		}
		else if(requestCode == EDIT_WALK_DETAILS){
			if( resultCode == RESULT_OK ) {
				
				

			}

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

}




package dcs.aber.ac.uk.cs211.group02;

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

public class WalkRecording extends FragmentActivity {

	private GoogleMap map;
	private Context context;
	private Button newPOIButton, uploadButton;
	private ImageButton helpButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_walk_recording);
		context = this;
		map=((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapView)).getMap();

		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

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
				startActivity(intent);   

			}

		});

		uploadButton = (Button) findViewById(R.id.walkRecordingUploadButton);

		uploadButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v){

				Intent intent = new Intent(context, ConfrimUploadActivity.class);
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



}

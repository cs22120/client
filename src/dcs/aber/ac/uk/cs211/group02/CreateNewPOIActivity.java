package dcs.aber.ac.uk.cs211.group02;

import android.media.Image;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class CreateNewPOIActivity extends Activity {

	private Context context;
	private ImageButton helpButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_poi);
		context = this;
		addOnClickListeners();
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

	}





	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_new_poi, menu);
		return true;
	}

	public String getWPOINameText() {

		return "DEFAULT VALUE";
	}

	public String getPOIDescText() {

		return "DEFAULT VALUE";
	}

	public Image getPOIImageFromDevice() {

		return null;
	}

	public PointOfInterest addPointOfInterest(){

		return null;
	}

}

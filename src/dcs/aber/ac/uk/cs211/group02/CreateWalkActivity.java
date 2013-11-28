package dcs.aber.ac.uk.cs211.group02;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class CreateWalkActivity extends Activity {

	private Context context;

	private Button recordButton;

	private ImageButton helpButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_walk);
		context = this;
		addListenerOnButtons();

	}

	public void addListenerOnButtons(){

		recordButton = (Button) findViewById(R.id.createWalkButton);
		recordButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v){

				Intent intent = new Intent(context, WalkRecording.class);
				startActivity(intent);
			}

		});


		helpButton = (ImageButton) findViewById(R.id.createWalkHelpButton);
		helpButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v){

				Intent intent = new Intent(context, HelpScreen.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_walk, menu);
		return true;
	}


	public String getWalkTitleText() {

		return "DEFAULT VALUE";
	}

	public String getWalkShortDescText() {

		return "DEFAULT VALUE";
	}

	public String getWalkLongDescext() {

		return "DEFAULT VALUE";
	}
}

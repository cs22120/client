package dcs.aber.ac.uk.cs211.group02;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class CreateWalkActivity extends Activity {

	private Context context;

	private Button recordButton;

	private ImageButton helpButton;

	private EditText nameEditText, shortEditText, longEditText;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_walk);
		context = this;
		setEditTexts();
		addListenerOnButtons();

	}

	public void setEditTexts() {

		nameEditText = (EditText) findViewById(R.id.createWalkNameEditText);
		shortEditText = (EditText) findViewById(R.id.createWalkShortDescEditText);
		longEditText = (EditText) findViewById(R.id.createWalkLongDescEditText);

	}

	public void addListenerOnButtons(){

		recordButton = (Button) findViewById(R.id.createWalkButton);
		recordButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v){

				Intent intent = new Intent(context, WalkRecording.class);
				Bundle b = new Bundle();
				b.putString("walkTitle", getWalkTitleText());
				b.putString("walkSDesc", getWalkShortDescText());
				b.putString("walkLDesc", getWalkLongDescText());
				intent.putExtra("Walk info", b);

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

		return nameEditText.getText().toString();
	}

	public String getWalkShortDescText() {

		return shortEditText.getText().toString();
	}

	public String getWalkLongDescText() {

		return longEditText.getText().toString();
	}
}

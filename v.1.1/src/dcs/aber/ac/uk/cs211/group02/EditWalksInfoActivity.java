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

public class EditWalksInfoActivity extends Activity {

	private Button updateInfoButton;
	private ImageButton helpButton;
	private EditText nameEditText, shortDescEditText, longDescEditText;

	private String name, sDesc, lDesc;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_walks_info);
		context = this;
		locateUIElements();
		addListenerOnButtons();
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null){

			name = bundle.getString("walk name");
			sDesc = bundle.getString("walk short");
			lDesc = bundle.getString("walk long");
			setEditTextValues();

		}

	}

	public void locateUIElements() {

		nameEditText = (EditText) findViewById(R.id.editWalkNameEditText);
		shortDescEditText = (EditText) findViewById(R.id.editWalkShortDescEditText);
		longDescEditText = (EditText) findViewById(R.id.editWalkLongDescEditText);



	}

	public void addListenerOnButtons() {


		updateInfoButton = (Button) findViewById(R.id.updateWalkInfoButton);

		updateInfoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent resultIntent = new Intent();
				resultIntent.putExtra("newName", getWalkTitleText());
				resultIntent.putExtra("newSDesc", getWalkShortDescText());
				resultIntent.putExtra("newLDesc", getWalkLongDescText());
				setResult(RESULT_OK, resultIntent);
				finish();

			}

		});


		helpButton = (ImageButton) findViewById(R.id.editWalkHelpButton);

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
		getMenuInflater().inflate(R.menu.edit_walks_info, menu);

		return true;
	}

	public String getWalkTitleText() {

		return nameEditText.getText().toString();
	}

	public String getWalkShortDescText() {

		return shortDescEditText.getText().toString();
	}

	public String getWalkLongDescText() {

		return longDescEditText.getText().toString();
	}

	public void setEditTextValues(){

		if(name != null && sDesc != null && lDesc != null){

			nameEditText.setText(name);
			shortDescEditText.setText(sDesc);
			longDescEditText.setText(lDesc);


		}


	}

}

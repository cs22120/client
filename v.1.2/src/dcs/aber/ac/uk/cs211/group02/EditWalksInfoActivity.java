package dcs.aber.ac.uk.cs211.group02;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

		nameEditText.setFilters(new InputFilter[]{charcterRestriction});
		shortDescEditText.setFilters(new InputFilter[]{charcterRestriction, new InputFilter.LengthFilter(100)});
		longDescEditText.setFilters(new InputFilter[]{charcterRestriction, new InputFilter.LengthFilter(1000)});


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

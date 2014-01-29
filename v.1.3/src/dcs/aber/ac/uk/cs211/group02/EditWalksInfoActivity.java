package dcs.aber.ac.uk.cs211.group02;

/**
 * 
 * This code is for use by Group02 Only and can be contacted either via e-mail on:
 * 
 * che16@aber.ac.uk or cs-group02@aber.ac.uk
 * 
 * This Activity allows the current user to edit the information about the
 * walk at run time. The information that can be edited is the walks name and
 * both the short and long descriptions.
 * 
 * @author Chris Edwards as part of the Aberystwyth University
 * 2013-2014 CS22120 Group project: "Walking Tour Creator"
 * 
 */

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

	/**
	 *This method simply located the user interface elements which are
	 *EditText form widgets, and assigns them to global variables. This prevents
	 *NullPointerExceptions. 
	 * 
	 *Also content filters are applied to these EditTexts to impose restrictions
	 *Specified by the Function Requirements of this project, these relate to
	 *character restrictions and maximum character restraints. 
	 *
	 *Any new EditText's or inputFilters should be handled with here.
	 *
	 */

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

		nameEditText.setFilters(new InputFilter[]{charcterRestrictionOneWord});
		shortDescEditText.setFilters(new InputFilter[]{charcterRestriction, new InputFilter.LengthFilter(100)});
		longDescEditText.setFilters(new InputFilter[]{charcterRestriction, new InputFilter.LengthFilter(1000)});


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

	public void addListenerOnButtons() {


		updateInfoButton = (Button) findViewById(R.id.updateWalkInfoButton);

		updateInfoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent resultIntent = new Intent();
				if(checkInputLength(getWalkTitleText()) && checkInputLength(getWalkLongDescText())
						&& checkInputLength(getWalkShortDescText())){
					resultIntent.putExtra("newName", getWalkTitleText());
					resultIntent.putExtra("newSDesc", getWalkShortDescText());
					resultIntent.putExtra("newLDesc", getWalkLongDescText());
					setResult(RESULT_OK, resultIntent);
					finish();
				}
				else{
					Toast.makeText(context, "You cannot have empty fields", Toast.LENGTH_SHORT).show();
				}
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

	/**
	 * Return the text value of the walk title Edit text.
	 * 
	 * @return String - the walks title
	 */

	public String getWalkTitleText() {

		return nameEditText.getText().toString();
	}

	/**
	 * Return the text value of the walk short description Edit text.
	 * 
	 * @return String - the walks short description
	 */

	public String getWalkShortDescText() {

		return shortDescEditText.getText().toString();
	}

	/**
	 * Return the text value of the walk long description Edit text.
	 * 
	 * @return String - the walks long description
	 */

	public String getWalkLongDescText() {

		return longDescEditText.getText().toString();
	}

	/**
	 * Because this Activity deals with the updating of the walks information
	 * you have to be able to view the walks current information. This is done using
	 * the extras intent in the onCreate() method. That being said you have to set
	 * the values of the passed in extras to the text values of the edit texts, so
	 * 1) they can be displayed to the user and 2) so the user can edit said values.
	 */

	public void setEditTextValues(){

		if(name != null && sDesc != null && lDesc != null){

			nameEditText.setText(name);
			shortDescEditText.setText(sDesc);
			longDescEditText.setText(lDesc);


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
}


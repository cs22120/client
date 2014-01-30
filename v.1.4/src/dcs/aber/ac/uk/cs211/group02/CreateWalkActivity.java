package dcs.aber.ac.uk.cs211.group02;

/**

 * 2013-2014 CS22120 Group project: "Walking Tour Creator"
 * 
 * This code is for use by Group02 Only and can be contacted either via e-mail on:
 * 
 * che16@aber.ac.uk or cs-group02@aber.ac.uk
 * 
 * This Actitiy is used to allow the user to specify information related to a
 * specific walk such as its name and both the short and longer descriptions.
 *
 * @author Chris Edwards as part of the Aberystwyth University
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

	public void setEditTexts() {

		nameEditText = (EditText) findViewById(R.id.createWalkNameEditText);
		shortEditText = (EditText) findViewById(R.id.createWalkShortDescEditText);
		longEditText = (EditText) findViewById(R.id.createWalkLongDescEditText);

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

		nameEditText.setFilters(new InputFilter[]{charcterRestrictionOneWord, new InputFilter.LengthFilter(15)});
		shortEditText.setFilters(new InputFilter[]{charcterRestriction, new InputFilter.LengthFilter(100)});
		longEditText.setFilters(new InputFilter[]{charcterRestriction, new InputFilter.LengthFilter(1000)});

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

	public void addListenerOnButtons(){

		recordButton = (Button) findViewById(R.id.createWalkButton);
		recordButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v){

				Intent intent = new Intent(context, WalkRecording.class);
				Bundle b = new Bundle();
				if(checkInputLength(getWalkTitleText()) && checkInputLength(getWalkLongDescText())
						&& checkInputLength(getWalkShortDescText())){
					b.putString("walkTitle", getWalkTitleText());
					b.putString("walkSDesc", getWalkShortDescText());
					b.putString("walkLDesc", getWalkLongDescText());
					intent.putExtra("Walk info", b);
					startActivity(intent);
				}
				else{
					Toast.makeText(context, "You cannot have empty fields", Toast.LENGTH_SHORT).show();

				}

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

		return shortEditText.getText().toString();
	}

	/**
	 * Return the text value of the walk long description Edit text.
	 * 
	 * @return String - the walks long description
	 */

	public String getWalkLongDescText() {

		return longEditText.getText().toString();
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

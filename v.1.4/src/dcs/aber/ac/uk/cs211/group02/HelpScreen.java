package dcs.aber.ac.uk.cs211.group02;

/**
 * 
 * This code is for use by Group02 Only and can be contacted either via e-mail on:
 * 
 * che16@aber.ac.uk or cs-group02@aber.ac.uk
 * 
 * This Activity is designed to provide the user with a simple, but intuitive
 * Environment for help if they ever get stuck or at a loss. The UI is simple enough
 * to understand and the information provided is concise.
 * 
 * @author Chris Edwards as part of the Aberystwyth University
 * 2013-2014 CS22120 Group project: "Walking Tour Creator"
 * 
 */

import java.util.Vector;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HelpScreen extends Activity {

	private Button nextButton, prevButton, returnButton;
	private Context context;
	private TextView contentView;
	private int screenTracker; //used to determine which help screen you are on
	private Vector<String> information = new Vector<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_screen);
		context = this;
		contentView = (TextView) findViewById(R.id.helpScreenInformationTextView);
		addOnClickListeners();
		screenTracker = 0;
		populateHelpList();
		setFirstScreen();


	}

	/**
	 * This method simply displays the first element in 
	 * Vector<String> to the user, as this will be the first help screen.
	 * 
	 */
	public void setFirstScreen(){

		contentView.setText(information.get(screenTracker));

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

	public void addOnClickListeners() {

		prevButton = (Button) findViewById(R.id.helpPrevious);

		prevButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				switchToPrevHelpText();

			}

		});

		nextButton = (Button) findViewById(R.id.helpNext);

		nextButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				switchToNextHelpText();

			}

		});

		returnButton = (Button) findViewById(R.id.helpReturn);

		returnButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help_screen, menu);
		return true;
	}

	/**
	 * This method is called when the user pressed the "next" button.
	 * The way in which the application determines what is actually the "next" 
	 * help screen is by keeping track on incremental counter which keeps track
	 * of the currently displayed screen. if this counter + 1 is greater than the size
	 * of the Vector<String> then there is no next screen available. 
	 * 
	 * if this counter + 1 is less than the size of the Vector<String> then
	 * display the next String the in Vector<String> to the user.
	 * 
	 */

	public void switchToNextHelpText() {

		if(screenTracker != information.size() && (screenTracker + 1) < information.size()){
			screenTracker++;
			contentView.setText(information.get(screenTracker));

		}

	}

	/**
	 * This method is called when the user pressed the "previous" button.
	 * The way in which the application determines what is actually the "previous" 
	 * help screen is by keeping track on incremental counter which keeps track
	 * of the currently displayed screen. if this counter - 1 is less than 0, then
	 * dont change the help text as there is no previous help screen.
	 * 
	 * if this counter - 1 is greater than 0 and less than Vector<String> size then
	 * display the current -1 String in the Vector<String> to the user.
	 * 
	 */
	
	public void switchToPrevHelpText() {

		if(screenTracker != 0){
			screenTracker--;
			contentView.setText(information.get(screenTracker));

		}


	}

	/**
	 * Private method which populates the Vector<String> with the required
	 * String resources in order to allow the user to be shown accurate help
	 * screen information. Any new help screen text's or the updating of current
	 * help screens should be achieved using this method.
	 * 
	 */
	
	private void populateHelpList() {

		information.add("To get started: \n Simply click \" create new walk \" \n");
		information.add("Enter the walks details in the boxes provided. \n NOTE: you are "
				+ "limited to 100 characters for the short description and 1000 for the long."
				+ "You may only use 0-9 a-z A-Z and the space character. \n Now click \"Record walk \"");
		information.add("A map should now be displayed with a blue dot as your current location. \n"
				+ "To add a new point of inter click \"new POI \" to save your walk press \" Upload \" \n"
				+ "and to edit the walk, press \" Edit walk \"");
		information.add("To add a new POI enter the information provided in the given boxes. \n"
				+ "Then you must add an image. To do select \"Add Image\" and a prompt will pop up."
				+ "From this prompt select how you want to add the image, from the camera or from the devices"
				+ "gallery. You will then be returned to the create POI screen. To finish click \"Create\".");
		information.add("The edit walk page should display the walks current detials. \n To change any of "
				+ "these details, simply edit the values provided in each of the boxes. \n Then select"
				+ "\" update info \".");
		information.add("ONce you are ready to save press \"Upload\" you will then be taken to the uplaod screen. \n"
				+ "the walks information will displayed to you\n Click \"Confirm\" and you will be "
				+ "prompted to make sure if you want to upload(save) or delete the walk. \n"
				+ "If you selected upload you will be created with the upload confirmed prompt and your walk is"
				+ "now saved to the server for use of the web application. \n if you want to delete your walk,"
				+ "you will be asked by another prompt if you want to delete the walk, select your response. \n");

	}

}

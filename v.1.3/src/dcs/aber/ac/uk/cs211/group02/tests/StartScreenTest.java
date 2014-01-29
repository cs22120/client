package dcs.aber.ac.uk.cs211.group02.tests;

/**
 * 
 * This code is for use by Group02 Only and can be contacted either via e-mail on:
 * 
 * che16@aber.ac.uk or cs-group02@aber.ac.uk
 * 
 * This Test Class is designed to test the major elements for the StartScreen
 * Activity. This Class tests all the main UI elements and data setting/getting.
 * 
 * @author Chris Edwards as part of the Aberystwyth University
 * 2013-2014 CS22120 Group project: "Walking Tour Creator" 
 */


import dcs.aber.ac.uk.cs211.group02.*;
import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class StartScreenTest extends ActivityUnitTestCase<StartScreen> {

	private Button startButton;
	private StartScreen startScreen;
	private ImageButton helpButton;
	private TextView helpTextView, welcomeTextView, instructiontTextView;


	public StartScreenTest() {
		super(StartScreen.class);

	}

	/**
	 * Set up the required instance of the StartScreen Activity Class. This
	 * will execute before each test.
	 *  
	 */

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(),
				StartScreen.class);
		startActivity(intent,null, null);
		startScreen = getActivity();

	}

	
	/**
	 * Test that the Button on the StartScreen which is used to bring another
	 * Activity in focus is not NULL.
	 * 
	 */
	
	@SmallTest
	public void testButtonNotNull() {
		startButton = (Button) startScreen.findViewById(R.id.startCreateWalkButton);
		assertNotNull("Button cannot be null", startButton);  

	}

	/**
	 * Test that the StartScreen help instruction TextView is not NULL. If this
	 * test passes, also test setting/getting of it's contents functions as expected.
	 * 
	 */
	
	@SmallTest
	public void testHelpTextView(){
		helpTextView = (TextView) startScreen.findViewById(R.id.startHelpTextInformationTextView);
		assertNotNull("Help TextView cannot be null", helpTextView);

		helpTextView.setText("hello help test");
		assertEquals("Help text is incorrect", "hello help test", helpTextView.getText());

	}
	
	/**
	 * Test that the StartScreen instruction TextView is not NULL. If this
	 * test passes, also test setting/getting of it's contents functions as expected.
	 * 
	 */

	@SmallTest
	public void testInstructionTextView(){
		instructiontTextView = (TextView) startScreen.findViewById(R.id.startScreenInstruction);
		assertNotNull("instruction TextView cannot be null", instructiontTextView);

		instructiontTextView.setText("hello instruction test");
		assertEquals("instruction text is incorrect", "hello instruction test",
				instructiontTextView.getText());

	}

	@SmallTest
	public void testWelcomeTextView(){
		welcomeTextView = (TextView) startScreen.findViewById(R.id.startScreenWelcome);
		assertNotNull("welcome TextView cannot be null", welcomeTextView);

		welcomeTextView.setText("hello welcome test");
		assertEquals("Welcome text is incorrect", "hello welcome test",
				welcomeTextView.getText());

	}

	/**
	 * Test that the StartScreen welcome TextView is not NULL. If this
	 * test passes, also test setting/getting of it's contents functions as expected.
	 * 
	 */
	
	@SmallTest
	public void testHelpImageButton(){
		helpButton = (ImageButton) startScreen.findViewById(R.id.helpButton);
		assertNotNull("help button cannot be null", helpButton);

	}


}



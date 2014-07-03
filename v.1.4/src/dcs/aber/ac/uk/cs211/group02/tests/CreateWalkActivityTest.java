package dcs.aber.ac.uk.cs211.group02.tests;

/**
 * 
 * This code is for use by Group02 Only and can be contacted either via e-mail on:
 * 
 * che16@aber.ac.uk or cs-group02@aber.ac.uk
 * 
 * This Test Class is designed to test the major elements for the CreateWalkActivity. 
 * This Class tests all the main UI elements and data setting/getting.
 * 
 * @author Chris Edwards as part of the Aberystwyth University
 * 2013-2014 CS22120 Group project: "Walking Tour Creator" 
 */


import dcs.aber.ac.uk.cs211.group02.*;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class CreateWalkActivityTest extends ActivityUnitTestCase<CreateWalkActivity> {

	private Button recordButton;
	private EditText nameEditText, shortEditText, longEditText;
	private CreateWalkActivity createWalk;
	private ImageButton helpButton;
	private TextView titleTextView, shortDescTextView, longDescTextView;


	public CreateWalkActivityTest() {
		super(CreateWalkActivity.class);

	}

	/**
	 * Set up the required instance of the CreateWalkActivity Class. This
	 * will execute before each test.
	 *  
	 */

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(),
				CreateWalkActivity.class);
		startActivity(intent,null, null);
		createWalk = getActivity();

	}

	/**
	 * Test that the Button responsible for executing the code which actually
	 * create a walk with the correct details in not NULL.
	 * 
	 */

	@SmallTest
	public void testButtonNotNull() {
		recordButton = (Button) createWalk.findViewById(R.id.createWalkButton);
		assertNotNull("Button cannot be null", recordButton);  

	}

	/**
	 * Test that the Title TextView is not NULL. if this test passes,
	 * go on to test that the getting/setting of the TextView's contents functions
	 * as expected.
	 * 
	 */

	@SmallTest
	public void testTitleTextView(){
		titleTextView = (TextView) createWalk.findViewById(R.id.serverResponseTextField);
		assertNotNull("title TextView cannot be null", titleTextView);

		titleTextView.setText("hello title test");
		assertEquals("title text is incorrect", "hello title test", titleTextView.getText());

	}

	/**
	 * Test that the short description TextView is not NULL. if this test passes,
	 * go on to test that the getting/setting of the TextView's contents functions
	 * as expected.
	 * 
	 */


	@SmallTest
	public void testShortDescTextView(){
		shortDescTextView = (TextView) createWalk.findViewById(R.id.createWalkShortDescTextView);
		assertNotNull("Help TextView cannot be null", shortDescTextView);

		shortDescTextView.setText("hello welcome test");
		assertEquals("Welcome text is incorrect", "hello welcome test",
				shortDescTextView.getText());

	}

	/**
	 * Test that the long description TextView is not NULL. if this test passes,
	 * go on to test that the getting/setting of the TextView's contents functions
	 * as expected.
	 * 
	 */

	@SmallTest
	public void testLongDescTextView(){
		longDescTextView = (TextView) createWalk.findViewById(R.id.createWalkLongDescTextView);
		assertNotNull("Help TextView cannot be null", longDescTextView);

		longDescTextView.setText("hello welcome test");
		assertEquals("Welcome text is incorrect", "hello welcome test",
				longDescTextView.getText());

	}

	/**
	 * Test that the help ImageButton which launches the HelpScreen Activity into
	 * focus is not NULL.
	 * 
	 */
	
	@SmallTest
	public void testHelpImageButton(){
		helpButton = (ImageButton) createWalk.findViewById(R.id.createWalkHelpButton);
		assertNotNull("help button cannot be null", helpButton);

	}
	
	/**
	 * Test that the long description EditText is not NULL. if this test is
	 * Successful then test that setting/getting it's content functions as
	 * expected.
	 * 
	 */

	@SmallTest
	public void testLongDescEditText() {

		longEditText = (EditText) createWalk.findViewById(R.id.createWalkLongDescEditText);
		assertNotNull("long EditText cannot be null", longEditText);
		longEditText.setText("hello long test");
		assertEquals("title text is incorrect", "hello long test", longEditText.getText().toString());

	}

	/**
	 * Test that the title EditText is not NULL. if this test is
	 * Successful then test that setting/getting it's content functions as
	 * expected.
	 * 
	 */
	
	@SmallTest
	public void testTitleEditText() {


		nameEditText = (EditText) createWalk.findViewById(R.id.createWalkNameEditText);
		assertNotNull("title EditText cannot be null", nameEditText);
		nameEditText.setText("title");
		assertEquals("title text is incorrect", "title", nameEditText.getText().toString());

	}
	
	/**
	 * Test that the short description EditText is not NULL. if this test is
	 * Successful then test that setting/getting it's content functions as
	 * expected.
	 * 
	 */

	@SmallTest
	public void testShortDescEditText() {

		shortEditText = (EditText) createWalk.findViewById(R.id.createWalkShortDescEditText);
		assertNotNull("short desc EditText cannot be null", shortEditText);
		shortEditText.setText("hello short test");
		assertEquals("title text is incorrect", "hello short test", shortEditText.getText().toString());

	}

	/**
	 * Test that the String length validation method functions correctly and returns
	 * the correct boolean value based of the length of the String provided.
	 * 
	 * All boundary conditions are tested in this instance, those being a valid String,
	 * and empty String, a long valid String using spaces and the shortest possible
	 * valid String.
	 * 
	 */

	@SmallTest
	public void testStringLengthVaidator() {

		String tooShort = "";
		String valid = "hello";
		String longerValid = "this should also pass the test";
		String smallestValid ="a";

		assertFalse(createWalk.checkInputLength(tooShort));
		assertTrue(createWalk.checkInputLength(valid));
		assertTrue(createWalk.checkInputLength(longerValid));
		assertTrue(createWalk.checkInputLength(smallestValid));

	}

	
	/**
	 * Test that the input filters placed on the short Description EditText
	 * correctly prevent certain characteristics from being set.
	 * 
	 * Firstly test that it prevents the use of special characters. 
	 * Secondly, test that it accepts spaces.
	 * 
	 */
	
	@SmallTest
	public void testInputValidationForShortDescription(){

		shortEditText = (EditText) createWalk.findViewById(R.id.createWalkShortDescEditText);

		shortEditText.setText("*&*(\"\");");

		assertEquals("input validation failed", "", shortEditText.getText().toString());

		shortEditText.setText("hello this should validate");
		assertEquals("input validation failed", "hello this should validate", shortEditText.getText().toString());


	}

	/**
	 * Test that the input filters placed on the long Description EditText
	 * correctly prevent certain characteristics from being set.
	 * 
	 * Firstly test that it prevents the use of special characters. 
	 * Secondly, test that it accepts spaces.
	 * 
	 */
	
	@SmallTest
	public void testInputValidationForLongDescription(){

		longEditText = (EditText) createWalk.findViewById(R.id.createWalkLongDescEditText);

		longEditText.setText("*&*(\"\");");

		assertEquals("input validation failed", "", longEditText.getText().toString());

		longEditText.setText("hello this should validate");
		assertEquals("input validation failed", "hello this should validate", longEditText.getText().toString());

	}

	/**
	 * Test that the input filters placed on the name EditText
	 * correctly prevent certain characteristics from being set.
	 * 
	 * Firstly test that it prevents the use of special characters. 
	 * Secondly test that it prevent the use of spaces (' ' character).
	 * Finally test that it accepts valid inputs.
	 * 
	 */
	
	@SmallTest
	public void testInputValidationForTitle(){


		nameEditText = (EditText) createWalk.findViewById(R.id.createWalkNameEditText);

		nameEditText.setText("*&*(\"\");");

		assertEquals("input validation failed", "", nameEditText.getText().toString());

		nameEditText.setText("Thisvalidates");
		assertEquals("input validation failed", "Thisvalidates", nameEditText.getText().toString());



	}


}



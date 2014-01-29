package dcs.aber.ac.uk.cs211.group02.tests;

/**
 * 
 * This code is for use by Group02 Only and can be contacted either via e-mail on:
 * 
 * che16@aber.ac.uk or cs-group02@aber.ac.uk
 * 
 * This Test Class is designed to test the major elements for the EditsWalksInfoActivity. 
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



public class EditWalksInfoActivityTest extends ActivityUnitTestCase<EditWalksInfoActivity> {

	private Button updateButton;
	private EditText nameEditText, shortEditText, longEditText;
	private EditWalksInfoActivity editInfo;
	private ImageButton helpButton;
	private TextView titleTextView, shortDescTextView, longDescTextView;

	public EditWalksInfoActivityTest() {
		super(EditWalksInfoActivity.class);


	}
	
	/**
	 * Set up the required instance of the EditWalksInfoActivity Class. This
	 * will execute before each test.
	 *  
	 */

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(),
				EditWalksInfoActivity.class);
		startActivity(intent,null, null);
		editInfo = getActivity();

	}

	/**
	 * Test that the Button responsible for executing the code which actually
	 * update a walk with the correct details in not NULL.
	 * 
	 */
	
	@SmallTest
	public void testButtonNotNull() {
		updateButton = (Button) editInfo.findViewById(R.id.updateWalkInfoButton);
		assertNotNull("Button cannot be null", updateButton);  

	}

	/**
	 * Test that the Title TextView is not NULL. if this test passes,
	 * go on to test that the getting/setting of the TextView's contents functions
	 * as expected.
	 * 
	 */
		
	@SmallTest
	public void testTitleTextView(){
		titleTextView = (TextView) editInfo.findViewById(R.id.editWalkTitleTextView);
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
		shortDescTextView = (TextView) editInfo.findViewById(R.id.editWalkShortDescTextView);
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
		longDescTextView = (TextView) editInfo.findViewById(R.id.editWalkLongDescTextView);
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
		helpButton = (ImageButton) editInfo.findViewById(R.id.editWalkHelpButton);
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

		longEditText = (EditText) editInfo.findViewById(R.id.editWalkLongDescEditText);
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


		nameEditText = (EditText) editInfo.findViewById(R.id.editWalkNameEditText);
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

		shortEditText = (EditText) editInfo.findViewById(R.id.editWalkShortDescEditText);
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

		assertFalse(editInfo.checkInputLength(tooShort));
		assertTrue(editInfo.checkInputLength(valid));
		assertTrue(editInfo.checkInputLength(longerValid));
		assertTrue(editInfo.checkInputLength(smallestValid));

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

		shortEditText = (EditText) editInfo.findViewById(R.id.editWalkShortDescEditText);

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

		longEditText = (EditText) editInfo.findViewById(R.id.editWalkLongDescEditText
				);

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


		nameEditText = (EditText) editInfo.findViewById(R.id.editWalkNameEditText);

		nameEditText.setText("*&*(\"\");");

		assertEquals("input validation failed", "", nameEditText.getText().toString());

		nameEditText.setText("Thisvalidates");
		assertEquals("input validation failed", "Thisvalidates", nameEditText.getText().toString());



	}


}



	
package dcs.aber.ac.uk.cs211.group02.tests;

/**
 * 
 * This code is for use by Group02 Only and can be contacted either via e-mail on:
 * 
 * che16@aber.ac.uk or cs-group02@aber.ac.uk
 * 
 * This Test Class is designed to test the major elements for the HelpScreen.
 * This Class tests all the main UI elements and data setting/getting.
 * 
 * @author Chris Edwards as part of the Aberystwyth University
 * 2013-2014 CS22120 Group project: "Walking Tour Creator" 
 */


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.TextView;
import dcs.aber.ac.uk.cs211.group02.*;

public class HelpScreenTest extends ActivityUnitTestCase<HelpScreen> {

	private Button returnButton, nextButton, previousButton;
	private HelpScreen helpScreen;
	private TextView helpTextView, labelTextView;
	private List<String> testList = new ArrayList<String>();


	public HelpScreenTest() {
		super(HelpScreen.class);


	}
	
	/**
	 * Set up the required instance of the HelpScreen. This
	 * will execute before each test.
	 *  
	 */

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(),
				HelpScreen.class);
		startActivity(intent,null, null);
		helpScreen = getActivity();
	}
	
	/**
	 * Test that the return Button on the HelpScreen is not NULL.
	 * 
	 */

	@SmallTest
	public void testReturnButtonIsNotNull(){

		returnButton = (Button) helpScreen.findViewById(R.id.helpReturn);
		assertNotNull("return button cannot  be null", returnButton);


	}
	
	/**
	 * Test that the button which controls the code to display the next
	 * help is in fact not NULL.
	 * 
	 */

	@SmallTest
	public void testNextButtonIsNotNull(){

		nextButton = (Button) helpScreen.findViewById(R.id.helpNext);
		assertNotNull("return button cannot  be null", nextButton);


	}
	
	/**
	 * Test that the button which controls the code to display the previous
	 * help is in fact not NULL.
	 * 
	 */

	@SmallTest
	public void testPreviousButtonIsNotNull(){

		previousButton = (Button) helpScreen.findViewById(R.id.helpPrevious);
		assertNotNull("return button cannot  be null", previousButton);


	}

	/**
	 * Test that the header TextView is not NULL.
	 * 
	 */
	
	@SmallTest
	public void testHeaderTextView(){

		labelTextView = (TextView) helpScreen.findViewById(R.id.helpTitleTextView);
		assertNotNull("title text view cannot be null", labelTextView);


	}

	/**
	 * Test that the setting and getting of the header TextView's content
	 * functions correctly and returns the expected results.
	 * 
	 * several conditions are tested at this stage, camel case, space characters and
	 * standard words are tested (for example: HeLlO, another test, hello).
	 * 
	 */
	
	@SmallTest
	public void testHeaderSetText(){

		String test1 = "hello";
		String test2 = "test case";
		String test3 = "CaMeLcAsE";

		labelTextView = (TextView) helpScreen.findViewById(R.id.helpTitleTextView);

		labelTextView.setText(test1);
		assertEquals("the strings are not equal", test1, labelTextView.getText());

		labelTextView.setText(test2);
		assertEquals("the strings are not equal", test2, labelTextView.getText());

		labelTextView.setText(test3);
		assertEquals("the strings are not equal", test3, labelTextView.getText());

	}

	/**
	 * Test that the information TextView is not NULL.
	 * 
	 * 
	 */
	
	@SmallTest
	public void testinformationTextView(){

		helpTextView = (TextView) helpScreen.findViewById(R.id.helpScreenInformationTextView);
		assertNotNull("title text view cannot be null", helpTextView);


	}

	/**
	 * Test that the setting and getting of the information TextView's content
	 * functions correctly and returns the expected results.
	 * 
	 * several conditions are tested at this stage, camel case, space characters and
	 * standard words are tested (for example: HeLlO, another test, hello).
	 * 
	 */
	
	@SmallTest
	public void testInformationSetText(){

		String test1 = "hello";
		String test2 = "test case";
		String test3 = "CaMeLcAsE";

		helpTextView = (TextView) helpScreen.findViewById(R.id.helpScreenInformationTextView);

		helpTextView.setText(test1);
		assertEquals("the strings are not equal", test1, helpTextView.getText());

		helpTextView.setText(test2);
		assertEquals("the strings are not equal", test2, helpTextView.getText());

		helpTextView.setText(test3);
		assertEquals("the strings are not equal", test3, helpTextView.getText());

	}

}





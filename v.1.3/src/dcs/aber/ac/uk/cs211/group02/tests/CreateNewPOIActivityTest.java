package dcs.aber.ac.uk.cs211.group02.tests;

/**
 * 
 * This code is for use by Group02 Only and can be contacted either via e-mail on:
 * 
 * che16@aber.ac.uk or cs-group02@aber.ac.uk
 * 
 * This Test Class is designed to test the major elements for the CreateNewPOIActivity. This Class tests all the main UI elements and data setting/getting.
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

public class CreateNewPOIActivityTest extends ActivityUnitTestCase<CreateNewPOIActivity> {


	private Button imageButton, createButton;
	private EditText nameEditText, descEditText;
	private CreateNewPOIActivity createPOI;
	private ImageButton helpButton;
	private TextView headerTextView, nameTextView, descTextView;


	public CreateNewPOIActivityTest() {
		super(CreateNewPOIActivity.class);

	}
	
	/**
	 * Set up the required instance of the CreateNewPOIActivity Class. This
	 * will execute before each test.
	 *  
	 */

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(),
				CreateNewPOIActivity.class);
		startActivity(intent,null, null);
		createPOI = getActivity();

	}

	/**
	 * Test that the CreateNewPOIActivity help button which launches the HelpScreen Activity
	 * into focus is not NULL.
	 * 
	 */
	
	@SmallTest
	public void testHelpButtonNotNull(){

		helpButton = (ImageButton) createPOI.findViewById(R.id.newPOIHelpButton);
		assertNotNull("Button cannot be null", helpButton);  

	}
	
	/**
	 * Test that the create button which executes the code required to create a 
	 * new PointOfInterest is not NULL.
	 * 
	 */
	

	@SmallTest
	public void testCreateButtonIsNotNull(){

		createButton = (Button) createPOI.findViewById(R.id.newPOICreateButton);
		assertNotNull("Button cannot be null", createButton);  

	}

	/**
	 * Test that the addImagge button which executes the code required in order
	 * to launch the image selection process. Depending on this users choice this
	 * button will either launch the camera Activity or the gallery Activity.
	 * 
	 */
	
	@SmallTest
	public void testAddImageButtonIsNotNull(){

		imageButton = (Button) createPOI.findViewById(R.id.newPOIAddPhotoButton);
		assertNotNull("Button cannot be null", imageButton);  

	}

	/**
	 * Test that the TextView which displays the PointOfInterest's name is not
	 * NULL.
	 * 
	 * if this test passed, the test the setting/getting of the TextView's content
	 * functions as expected.
	 * 
	 */
	
	@SmallTest
	public void testNameTextView(){

		nameTextView = (TextView) createPOI.findViewById(R.id.newPOINameTextView);
		assertNotNull("text view cannot be null", nameTextView);

		nameTextView.setText("name test");
		assertEquals("texts do not match", "name test", nameTextView.getText());

	}

	/**
	 * Test that the TextView which displays the PointOfInterest's description is not
	 * NULL.
	 * 
	 * if this test passed, the test the setting/getting of the TextView's content
	 * functions as expected.
	 * 
	 */
	
	@SmallTest
	public void testDescTextView(){

		descTextView = (TextView) createPOI.findViewById(R.id.newPOIDescTextView);
		assertNotNull("text view cannot be null", descTextView);

		descTextView.setText("desc test");
		assertEquals("texts do not match", "desc test", descTextView.getText());

	}
	
	/**
	 * Test that the EditText which displays the PointOfInterest's name is not
	 * NULL.
	 * 
	 * if this test passed, the test the setting/getting of the EditText's content
	 * functions as expected.
	 * 
	 */

	@SmallTest
	public void testNameEditText(){

		nameEditText = (EditText) createPOI.findViewById(R.id.newPOINameEditText);
		assertNotNull("edit text cannot be null", nameEditText);

		nameEditText.setText("name edit text");
		assertEquals("texts does not match expected", "name edit text", nameEditText.getText().toString());


	}
	
	/**
	 * Test that the EditText which displays the PointOfInterest's name is not
	 * NULL.
	 * 
	 * if this test passed, the test the setting/getting of the EditText's content
	 * functions as expected.
	 * 
	 */

	@SmallTest
	public void testDescEditText(){

		descEditText = (EditText) createPOI.findViewById(R.id.newPOIDescEditText);
		assertNotNull("edit text cannot be null", descEditText);

		descEditText.setText("desc edit text");
		assertEquals("texts does not match expected", "desc edit text", descEditText.getText().toString());

	}


}

package dcs.aber.ac.uk.cs211.group02.tests;

/**
 * 
 * This code is for use by Group02 Only and can be contacted either via e-mail on:
 * 
 * che16@aber.ac.uk or cs-group02@aber.ac.uk
 * 
 * This Test Class is designed to test the major elements for the ConfirmUploadActivity. This Class tests all the main UI elements and data setting/getting.
 * 
 * @author Chris Edwards as part of the Aberystwyth University
 * 2013-2014 CS22120 Group project: "Walking Tour Creator" 
 */


import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import dcs.aber.ac.uk.cs211.group02.*;

public class ConfirmUploadActivityTest extends ActivityUnitTestCase<ConfrimUploadActivity> {


	private TextView headerTextView, titleTextView, descriptionTextView,
	titleLabel, descriptionLabel;
	private Button confirmButton;
	private ImageButton help;

	private ConfrimUploadActivity upload;

	public ConfirmUploadActivityTest() {
		super(ConfrimUploadActivity.class);


	}

	/**
	 * Set up the required instance of the ConfirmUploadActivity Class. This
	 * will execute before each test.
	 *  
	 */
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(),
				HelpScreen.class);
		startActivity(intent,null, null);
		upload = getActivity();
	}

	/**
	 * Test that the ConfirmUploadActivity header TextView is not NULL.
	 * 
	 */
	
	@SmallTest
	public void testHeaderTextView(){
		headerTextView = (TextView) upload.findViewById(R.id.confirmUploadHeaderTextView);
		assertNotNull("text view cannot be null", headerTextView);

	}
	
	/**
	 * Test that the ConfirmUploadActivity header TextView
	 * setting/getting of it's contents functions as expected.
	 * 
	 */

	@SmallTest
	public void testHeaderTextViewText(){
		headerTextView = (TextView) upload.findViewById(R.id.confirmUploadHeaderTextView);

		assertEquals("context does not match", "Upload Walk", headerTextView.getText());

	}

	/**
	 * Test that the ConfirmUploadActivity titleLabel TextView is not NULL. 
	 */
	
	@SmallTest
	public void testTitleLabelTextView(){
		titleLabel = (TextView) upload.findViewById(R.id.confirmUploadWalkTitleLabelTextView);
		assertNotNull("text view cannot be null", titleLabel);

	}

	/**
	 * Test that the ConfirmUploadActivity titleLabel TextView can have its
	 * contents set and received correctly as everything functions as expected. 
	 */
	
	@SmallTest
	public void testTitleLabelTextViewText(){
		titleLabel = (TextView) upload.findViewById(R.id.confirmUploadWalkTitleLabelTextView);

		assertEquals("context does not match", "Walk Ttitle", titleLabel.getText());

	}
	
	/**
	 * Test that the ConfirmUploadActivity descriptionLabel TextView is not NULL.
	 */

	@SmallTest
	public void testDescriptionLabelTextView(){
		descriptionLabel = (TextView) upload.findViewById(R.id.confirmUploadWalkDescLabelTextView);
		assertNotNull("text view cannot be null", descriptionLabel);

	}
	
	/**
	 * Test that the ConfirmUploadActivity descriptionLabel TextView can have its
	 * contents set and received correctly as everything functions as expected. 
	 */

	@SmallTest
	public void testDescriptionLabelTextViewText(){
		descriptionLabel = (TextView) upload.findViewById(R.id.confirmUploadWalkDescLabelTextView);

		assertEquals("context does not match", "Walk Description", descriptionLabel.getText());

	}

	/**
	 * Test that the upload confirmation button which executes the upload code
	 * is Not NULL.
	 * 
	 */
	
	@SmallTest
	public void testButtonIsNotNull() {

		confirmButton = (Button) upload.findViewById(R.id.confrimUploadConfirmButton);
		assertNotNull("button cannot be null", confirmButton);
	}

	/**
	 * Test that the help ImageButton which launches the HelpScreen Activity 
	 * into focus is not NULL.
	 * 
	 */
	
	@SmallTest
	public void testHelpButtonIsNotNull(){

		help = (ImageButton) upload.findViewById(R.id.confirmUploadIHelpButton);
		assertNotNull("help button cannot be null", help);

	}

	/**
	 * Test that the ConfirmUploadActivity title TextView is not NULL. 
	 */
	
	@SmallTest
	public void testTitleTextView(){

		titleTextView = (TextView) upload.findViewById(R.id.confirmUploadWalkTitleValueTextView);
		assertNotNull("text view cannot be null", titleTextView);

	}
	
	/**
	 * Test that the ConfirmUploadActivity title TextView can have its
	 * contents set and received correctly as everything functions as expected.
	 * 
	 *  This test case uses multiple tests to check data integrity.
	 */
	
	@SmallTest
	public void testTitleTextViewText(){
		
		titleTextView = (TextView) upload.findViewById(R.id.confirmUploadWalkTitleValueTextView);
		
		titleTextView.setText("testing");
		assertEquals("content does not match", "testing", titleTextView.getText());
		
		titleTextView.setText("testing again");
		assertEquals("content does not match", "testing again", titleTextView.getText());
		
		titleTextView.setText("testing another time");
		assertEquals("content does not match", "testing another time", titleTextView.getText());
	}

	/**
	 * Test that the ConfirmUploadActivity description TextView is not NULL.
	 */
	
	@SmallTest
	public void testDEscriptionTextView(){

		descriptionTextView = (TextView) upload.findViewById(R.id.confirmUploadWalkDescValueTextView);
		assertNotNull("text view cannot be null", descriptionTextView);

	}
	
	/**
	 * Test that the ConfirmUploadActivity description TextView can have its
	 * contents set and received correctly as everything functions as expected. 
	 * 
	 * This test uses multiple test cases to check data integrity.
	 */
	
	@SmallTest
	public void testDescriptionTextViewText(){
		
		descriptionTextView = (TextView) upload.findViewById(R.id.confirmUploadWalkDescValueTextView);
		
		descriptionTextView.setText("testing");
		assertEquals("content does not match", "testing", descriptionTextView.getText());
		
		descriptionTextView.setText("testing again");
		assertEquals("content does not match", "testing again", descriptionTextView.getText());
		
		descriptionTextView.setText("testing another time");
		assertEquals("content does not match", "testing another time", descriptionTextView.getText());
	}

}

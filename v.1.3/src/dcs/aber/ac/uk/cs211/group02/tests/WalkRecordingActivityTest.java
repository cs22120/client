package dcs.aber.ac.uk.cs211.group02.tests;


/**
 * 
 * This code is for use by Group02 Only and can be contacted either via e-mail on:
 * 
 * che16@aber.ac.uk or cs-group02@aber.ac.uk
 * 
 * This Test Class is designed to test the major elements for the WalkRecordingActivity
 * Activity. This Class tests all the main UI elements and data setting/getting.
 * 
 * @author Chris Edwards as part of the Aberystwyth University
 * 2013-2014 CS22120 Group project: "Walking Tour Creator" 
 */


import java.util.Vector;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

import dcs.aber.ac.uk.cs211.group02.*;

public class WalkRecordingActivityTest extends ActivityInstrumentationTestCase2<WalkRecording> {

	private Button editButton, newPOIButton, uploadButton;
	private WalkRecording recording;
	private GoogleMap map;
	private ImageButton helpButton;
	private FragmentActivity test;
	private TextView timerTextView, titleTextView;


	public WalkRecordingActivityTest() {
		super(WalkRecording.class);

	}

	/**
	 * This is required in order to execute the test cases. This instantiates
	 * the WalkRecordingActivity.
	 * 
	 * This is executed before each test.
	 * 
	 */

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(),
				WalkRecording.class);
		setActivityIntent(intent);
		recording = getActivity();

	}

	/**
	 * Test that the Button used to execute the ConfirmUploadActivity is not NULL.
	 * 
	 */

	@SmallTest
	public void testUploadButtonNotNull() {
		uploadButton = (Button) recording.findViewById(R.id.walkRecordingUploadButton);
		assertNotNull("Button cannot be null", uploadButton);  

	}
	
	/**
	 * Test that the Button to launch the createNewPOIActivity is not NULL.
	 * 
	 */

	@SmallTest
	public void testNewPOIButtonNotNull() {
		newPOIButton = (Button) recording.findViewById(R.id.walkRecordingNewPOIButton);
		assertNotNull("Button cannot be null", newPOIButton);  

	}
	
	/**
	 * Test that the Button that launches the EditWalkActivity is not NULL.
	 * 
	 * 
	 */

	@SmallTest
	public void testEditButtonNotNull() {
		editButton = (Button) recording.findViewById(R.id.walkRecordingEditButton);
		assertNotNull("Button cannot be null", editButton);  

	}

	/**
	 * Test that the title TextView is not NUll.
	 * 
	 */
	
	@SmallTest
	public void testTitleTextView() {
		titleTextView = (TextView) recording.findViewById(R.id.walkRecordingWalkTitleTextView);
		assertNotNull("text view cannot be null", titleTextView);  
	}
	
	/**
	 * Test that the timer TextView is not NUll.
	 * 
	 */

	@SmallTest
	public void testTimerTextView() {

		timerTextView = (TextView) recording.findViewById(R.id.timerTextView);
		assertNotNull("text view cannot be null", timerTextView); 

	}
	
	/**
	 * Test that the GoogleMap Object which is displaying
	 * the users location/PointOfInterest is not NULL.
	 * 
	 */

	@SmallTest
	public void testMapIsNotNull() {
		map = recording.getMap();
		assertNotNull("map cannot be nul", map);

	}

	/**
	 * Test that the title can be correctly updated and
	 * retrieved with expected functionality.
	 * 
	 */
	
	@SmallTest
	public void SetGetAndSetTitle(){

		recording.setTitle("test 1");
		assertEquals("content is not as expected", "test 1", recording.getTitle());

		recording.setTitle("test 2");
		assertEquals("content is not as expected", "test 2", recording.getTitle());

		recording.setTitle("test 3");
		assertEquals("content is not as expected", "test 3", recording.getTitle());

	}
	
	
	/**
	 * Test that the short description can be correctly updated and
	 * retrieved with expected functionality.
	 * 
	 */

	@SmallTest
	public void SetGetAndSetShortDesc(){

		recording.setWalkSDesc("test 1");
		assertEquals("content is not as expected", "test 1", recording.getWalkSDesc());

		recording.setWalkSDesc("test 2");
		assertEquals("content is not as expected", "test 2", recording.getWalkSDesc());

		recording.setWalkSDesc("test 3");
		assertEquals("content is not as expected", "test 3", recording.getWalkSDesc());

	}
	
	/**
	 * Test that the long description can be correctly updated and
	 * retrieved with expected functionality.
	 * 
	 */

	@SmallTest
	public void SetGetAndSetLOngDesc(){

		recording.setWalkLdesc("test 1");
		assertEquals("content is not as expected", "test 1", recording.getWalkLdesc());

		recording.setWalkLdesc("test 2");
		assertEquals("content is not as expected", "test 2", recording.getWalkLdesc());

		recording.setWalkLdesc("test 3");
		assertEquals("content is not as expected", "test 3", recording.getWalkLdesc());

	}

}


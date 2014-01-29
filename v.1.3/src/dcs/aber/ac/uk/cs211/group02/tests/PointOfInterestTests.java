package dcs.aber.ac.uk.cs211.group02.tests;

/**
 * 
 * This code is for use by Group02 Only and can be contacted either via e-mail on:
 * 
 * che16@aber.ac.uk or cs-group02@aber.ac.uk
 * 
 * This Test Class is designed to test the major elements for the PointOfInterest Class.
 * This Class tests the main data structure elements that relate to a POI
 * 
 * @author Chris Edwards as part of the Aberystwyth University
 * 2013-2014 CS22120 Group project: "Walking Tour Creator" 
 */


import java.io.File;

import dcs.aber.ac.uk.cs211.group02.PointOfInterest;
import junit.framework.TestCase;

public class PointOfInterestTests extends TestCase {

	private String name, desc, timeStamp;
	private double lat, lng;
	private File image;
	private PointOfInterest p;

	/**
	 * set up the required conditions in order to execute the tests. This
	 * is executed before every test case.
	 * 
	 */

	public void setUp()throws Exception{

		name = "poiName";
		desc = "poi desc";
		timeStamp = "00:10:23";
		lat = 41.29;
		lng = 5.23;
		p = new PointOfInterest(name, desc, lng, lat, timeStamp);

	}

	/**
	 * Test point of interest is correctly created and is not NULL.
	 * 
	 */
	
	public void testPOINotNull(){

		assertNotNull("POI is null", p);

	}
	
	/**
	 * Test that a point of interest's latitude is set correctly. To test this
	 * set multiple different latitudes and compare the results using the
	 * p.getLattitude() method.
	 * 
	 */

	public void testLatitudeIsSetCorrectly(){

		double tempLat1 = p.getLattitude();
		assertEquals("latitudes differ",Double.valueOf(41.29), tempLat1);

		p.setLat(67.22);
		double tempLat2 = p.getLattitude();
		assertEquals("latitudes differ", Double.valueOf(67.22), tempLat2);

		p.setLat(8.11123);
		double tempLat3 = p.getLattitude();
		assertEquals("latitudes differ", Double.valueOf(8.11123), tempLat3);


	}
	
	/**
	 * Test that a point of interest's longitude is set correctly. To test this
	 * set multiple different latitudes and compare the results using the
	 * p.getLongitude() method.
	 * 
	 */

	public void testLongitudeIsSetCorrectly(){

		double tempLng1 = p.getLongitude();
		assertEquals("longitudes differ",Double.valueOf(5.23), tempLng1);

		p.setLong(-12.34);
		double tempLng2 = p.getLongitude();
		assertEquals("longitudes differ", Double.valueOf(-12.34), tempLng2);

		p.setLong(-4.12);
		double tempLng3 = p.getLongitude();
		assertEquals("longitudes differ", Double.valueOf(-4.12), tempLng3);

	}

	/**
	 * Test that time time stamp is correctly set. To do this set the timeStamp
	 * to varying String values and compare results using the getTimeSTamp() method 
	 * call.
	 */
	
	public void testTimeStampIsSetCorrectly(){

		String time1 = p.getTimeStamp();
		assertEquals("times differ", "00:10:23", time1);

		p.setTimeStamp("10:11:00");
		String time2 = p.getTimeStamp();
		assertEquals("times differ", "10:11:00", time2);

		p.setTimeStamp("59:59:59");
		String time3 = p.getTimeStamp();
		assertEquals("times differ", "59:59:59", time3);
	}

	/**
	 * Test that name is correctly set. To do this set the name
	 * to varying String values and compare results using the getName() method 
	 * call.
	 */
	
	public void testNameIsSetCorrectly(){

		String name1 = p.getName();
		assertEquals("names differ", "poiName", name1);

		p.setName("new name 2");
		String name2 = p.getName();
		assertEquals("names differ", "new name 2", name2);

		p.setName("new name 3");
		String name3 = p.getName();
		assertEquals("names differ", "new name 3", name3);

	}
	
	/**
	 * Test that description is correctly set. To do this set the description
	 * to varying String values and compare results using the getDescription() 
	 * method call.
	 */

	public void testDescIsSetCorrectly(){

		String desc1 = p.getDescription();
		assertEquals("descs differ", "poi desc", desc1);

		p.setDescription("new desc 2");
		String desc2 = p.getDescription();
		assertEquals("descs differ", "new desc 2", desc2);

		p.setDescription("new desc 3");
		String desc3 = p.getDescription();
		assertEquals("descs differ", "new desc 3", desc3);

	}
}



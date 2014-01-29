package dcs.aber.ac.uk.cs211.group02;

/**
 * 
 * This code is for use by Group02 Only and can be contacted either via e-mail on:
 * 
 * che16@aber.ac.uk or cs-group02@aber.ac.uk
 * 
 * This Class is the is used to store the data related to each individual 
 * PointOfInterest. Every PointOfInterest implements the "Parcelable"
 * interface so it can easily be passed efficiently between activities.
 * 
 * @author Chris Edwards as part of the Aberystwyth University
 * 2013-2014 CS22120 Group project: "Walking Tour Creator"
 * 
 * 
 */

import java.io.File;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

public class PointOfInterest implements Parcelable {


	private String name, description, timeStamp;
	private double longitude, lattitude;


	/*The Image File*/
	private File image;

	/**
	 * The PointOfInterst creates a new instance of a PointOfInterest 
	 * as well as instantiating it with the correct information. 
	 * 
	 * IMPORTANT: The addition of the image file is not in there parameters
	 * as it is handled elsewhere within the application code.
	 * 
	 * @param String - the PointOfInterest's name
	 * @param String - the PointOfInterest's name
	 * @param double - the PointOfInterest's GPS Longitude
	 * @param double - the PointOfInterest's GPS Latitude
	 * @param String - the PointOfInterest's time stamp
	 */
	public PointOfInterest(String name, String description, double longitude, double lattitude
			,String timeStamp) {

		this.name = name;
		this.description = description;
		this.longitude = longitude;
		this.lattitude = lattitude;
		this.timeStamp = timeStamp;

	}

	/**
	 * This is the Parcelable constructor for a POintOfInterest. This special
	 * constructor is required due to the implementation of the Parcelable 
	 * interface and should not be removed or edited without valid reason.
	 * 
	 * This Constructor is called upon trying to decode a PointOfInterest from
	 * a encoded Paraclable Object.
	 * 
	 * This technique is essential for the current working of this application.
	 * 
	 * @param in
	 */

	public PointOfInterest(Parcel in) {
		name = in.readString();
		description = in.readString();
		longitude = in.readDouble();
		lattitude = in.readDouble();
		image = new File(in.readString());
		timeStamp = in.readString();


	}

	/**
	 * Return the absolute path to the current image
	 * 
	 * @return String - the image's absolute path
	 */

	public String getImagePath(){

		return image.toString();
	}

	/**
	 * Add an Image to a POintOfInterest
	 * 
	 * @param File - the image File to add
	 */

	public void addImage(File im){

		this.image = im;
	}


	/**
	 * This method calls for the create of a point of interest given several
	 * important parameter arguments. This newly  created
	 * PointOfInterest Object, is then returned so it can sufficiently dealt with
	 * elsewhere in the WalkRecording Activity.
	 * 
	 * @param String - the PointOfInterest's name
	 * @param String - the PointOfInterest's description
	 * @param double - the PointOfInterest's GPS Longitude
	 * @param double - the PointOfInterest's GPS Latitude
	 * @param String - the PointOfInterest's time stamp
	 * @return PointOfInterest - the new PointOfInterestInstance
	 */

	public PointOfInterest makeAndReturnPOI (String name, String description, double longitude, double lattitude, 
			String tStamp){

		PointOfInterest p = new PointOfInterest(name, description, longitude, lattitude, tStamp);

		return p;
	}

	@Override
	public int describeContents() {
		// DO NOT EDIT
		return 0;
	}


	/**
	 * The method is part of the Parcelable interface and should not be edited.
	 * This method is used in encoding the Object into parcelable format
	 * which is required for effective passing between application Activities.
	 * 
	 * @param Parcel - the destination
	 * @param int - any flags that you want specified
	 * 
	 */

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(name);
		dest.writeString(description);
		dest.writeDouble(longitude);
		dest.writeDouble(lattitude);
		dest.writeString(image.toString());
		dest.writeString(timeStamp);
	}

	/*
	 *DO NOT EDIT. This code is the constructor for creating multiple
	 *Parcelable Obects and storing them correctly in memory.
	 *
	 * The editing of this method, could cause application failure thus,
	 * this method should not be modified unless it has since been
	 * depreciated.
	 */
	public static final Parcelable.Creator<PointOfInterest> CREATOR
	= new Parcelable.Creator<PointOfInterest>() {
		public PointOfInterest createFromParcel(Parcel in) {
			return new PointOfInterest(in);
		}

		public PointOfInterest[] newArray(int size) {
			return new PointOfInterest[size];
		}
	};

	/**
	 * Return the PointOfInterest's name
	 * 
	 * @return String - the PointOfInterest's name
	 */

	public String getName() {

		return name;
	}

	/**
	 * Return the PointOfInterest's Description
	 * 
	 * @return String - the PointOfInterest's description
	 */

	public String getDescription() {
		return description;
	}

	/**
	 * Return the PointOfInterest's GPS Longitude
	 * 
	 * @return double - the GPS Longitude
	 */

	public double getLongitude() {
		return longitude;
	}

	/**
	 * Return the PointOfInterest's GPS Latitude
	 * 
	 * @return double - the PointOfInterest's GPS Latitude
	 */

	public double getLattitude() {
		return lattitude;
	}

	/**
	 * Return the PointOfInterests associated Image File
	 * 
	 * @return File - the PointOfInterests Image file
	 */

	public File getImage(){

		return image;
	}

	/**
	 * Return the time stamp for the PointOfInterest
	 * 
	 * @return String the PointOfInterest's time stamp
	 */

	public String getTimeStamp() {

		return timeStamp;
	}

	/**
	 * Set the latitude value for this point of interest
	 * 
	 * @param double - the latitude
	 */

	public void setLat(double lat){

		this.lattitude = lat;
	}


	/**
	 * Set the longitude value for this point of interest
	 * 
	 * @param double - the longitude
	 */

	public void setLong(double lng){

		this.longitude = lng;
	}

	/**
	 * Set the name for this point of interest
	 * 
	 * @param String - the name
	 */

	public void setName(String n){

		this.name = n;

	}

	/**
	 * Set the description for this point of interest
	 * 
	 * @param String - the description
	 */

	public void setDescription(String desc){

		this.description = desc;

	}

	/**
	 * Set the time stamp for this point of interest
	 * 
	 * @param String - the time stamp
	 */

	public void setTimeStamp(String time){

		this.timeStamp = time;

	}

}

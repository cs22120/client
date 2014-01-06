package dcs.aber.ac.uk.cs211.group02;

import java.io.File;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

public class PointOfInterest implements Parcelable {


	private String name, description;
	private double longitude, lattitude;

	private File image;


	public PointOfInterest(String name, String description, double longitude, double lattitude) {

		this.name = name;
		this.description = description;
		this.longitude = longitude;
		this.lattitude = lattitude;

	}

	public PointOfInterest(Parcel in) {
		name = in.readString();
		description = in.readString();
		longitude = in.readDouble();
		lattitude = in.readDouble();
		image = new File(in.readString());


	}

	public String getImagePath(){

		return image.toString();
	}

	public void addImage(File im){

		this.image = im;
	}


	public PointOfInterest makeAndReturnPOI (String name, String description, double longitude, double lattitude){

		PointOfInterest p = new PointOfInterest(name, description, longitude, lattitude);

		return p;
	}

	@Override
	public int describeContents() {
		// DO NOT EDIT
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeString(name);
		dest.writeString(description);
		dest.writeDouble(longitude);
		dest.writeDouble(lattitude);
		dest.writeString(image.toString());
	}

	public static final Parcelable.Creator<PointOfInterest> CREATOR
	= new Parcelable.Creator<PointOfInterest>() {
		public PointOfInterest createFromParcel(Parcel in) {
			return new PointOfInterest(in);
		}

		public PointOfInterest[] newArray(int size) {
			return new PointOfInterest[size];
		}
	};

	public String getName() {

		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLattitude() {
		return lattitude;
	}

	public File getImage(){

		return image;
	}


}

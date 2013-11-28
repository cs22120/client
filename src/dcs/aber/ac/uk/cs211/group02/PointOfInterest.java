package dcs.aber.ac.uk.cs211.group02;

import android.media.Image;

public class PointOfInterest {


	private String name, description;
	private double longitude, lattitude;
	private Image image;

	public PointOfInterest(String name, String description, double longitude, double lattitude) {

		this.name = name;
		this.description = description;
		this.longitude = longitude;
		this.lattitude = lattitude;


	}

	public void addImage(Image im){

		this.image = im;
	}


}

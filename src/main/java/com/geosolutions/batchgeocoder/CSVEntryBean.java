package com.geosolutions.batchgeocoder;

public class CSVEntryBean {
	private String name;
	private double latitude;
	private double longitude;
	private double boundingNorth;
	private double boundingSouth;
	private double boundingEast;
	private double boundingWest;
	
	public CSVEntryBean(){}
	
	public String[] toStringArray(){
		
		String[] array = new String[6];
		array[0] = String.valueOf(name);
		array[1] = String.valueOf(latitude);
		array[2] = String.valueOf(longitude);
		array[3] = String.valueOf(boundingNorth);
		array[4] = String.valueOf(boundingSouth);
		array[5] = String.valueOf(boundingEast);
		array[6] = String.valueOf(boundingWest);
		return array;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getBoundingNorth() {
		return boundingNorth;
	}

	public void setBoundingNorth(double boundingNorth) {
		this.boundingNorth = boundingNorth;
	}

	public double getBoundingSouth() {
		return boundingSouth;
	}

	public void setBoundingSouth(double boundingSouth) {
		this.boundingSouth = boundingSouth;
	}

	public double getBoundingEast() {
		return boundingEast;
	}

	public void setBoundingEast(double boundingEast) {
		this.boundingEast = boundingEast;
	}

	public double getBoundingWest() {
		return boundingWest;
	}

	public void setBoundingWest(double boundingWest) {
		this.boundingWest = boundingWest;
	}
	
	
	
	
}

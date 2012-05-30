package it.geosolutions.geobatchcoder.model;

import java.util.ArrayList;
import java.util.List;

import fr.dudie.nominatim.model.Address;

public class Position {

	
	private Double latitude;
	private Double Longitude;
	private Double boundingEast;
	private Double boundingNorth;
	private Double boundingSouth;
	private Double boundingWest;
	
	public Position(){
		 latitude=0d;
		 Longitude=0d;
		 boundingEast=0d;
		 boundingNorth=0d;
		 boundingSouth=0d;
		 boundingWest=0d;
	}
	
	/**
	 * 
	 * @return a list of double in this order
	 *  latitude;Longitude;boundingEast;boundingNorth;boundingSouth;boundingWest;
	 */
	public List<String> getPositionAsList(){
		List<String> list = new ArrayList<String>();
		list.add(String.valueOf(latitude));
		list.add(String.valueOf(Longitude));
		list.add(String.valueOf(boundingEast));
		list.add(String.valueOf(boundingNorth));
		list.add(String.valueOf(boundingSouth));
		list.add(String.valueOf(boundingWest));
		return list;
	}
	
	public void setPosition(Address address){
		latitude=address.getLatitude();
		Longitude=address.getLongitude();
		boundingEast=address.getBoundingBox().getEast();
		boundingNorth=address.getBoundingBox().getNorth();
		boundingSouth=address.getBoundingBox().getSouth();
		boundingWest=address.getBoundingBox().getWest();
	}
	
	
}

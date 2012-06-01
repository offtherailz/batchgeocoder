package it.geosolutions.geobatchcoder.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.dudie.nominatim.model.Address;

public class Position {

	
	private Double latitude;
	private Double Longitude;
	
	private Double boundingNorth;
	private Double boundingSouth;
	private Double boundingWest;
	private Double boundingEast;
	
	public Position(){
		 latitude=0d;
		 Longitude=0d;
		 boundingEast=0d;
		 boundingNorth=0d;
		 boundingSouth=0d;
		 boundingWest=0d;
	}
	
	
	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return Longitude;
	}

	/**
	 * 
	 * @return the bounding box point in order
	 * boundingEast;boundingNorth;boundingSouth;boundingWest;
	 */
	public Map<String, Double> getBoundingBoxPoints(){
		Map<String, Double> points = new HashMap<String, Double>();
		points.put("north", boundingNorth);
		points.put("south", boundingSouth);
		points.put("west", boundingEast);
		points.put("east", boundingEast);
		return points;
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
		
		list.add(String.valueOf(boundingNorth));
		list.add(String.valueOf(boundingSouth));
		list.add(String.valueOf(boundingWest));
		list.add(String.valueOf(boundingEast));
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

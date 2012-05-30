package it.geosolutions.geobatchcoder.model;

import java.util.ArrayList;
import java.util.List;

public class LocationImpl implements Location{
	
	
	private Description description;
	private Position position;
	
	public void setDescription(Description description) {
		this.description = description;
	}
	
	public List<String> getLocationAsList() {
		List<String> list = new ArrayList<String>();
		list.add(description.getName());
		list.addAll(position.getPositionAsList());
		return list;
	}

	public List<String> getPositionsAlternatives() {
		return description.getAllAlternatives();
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	public String getName(){
		return this.description.getName();
	}

//	public Description getDescription() {
//		return description;
//	}
	
	
	
	
}

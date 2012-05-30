package it.geosolutions.geobatchcoder.model;

import java.util.List;

public interface Location {
	
	
	public List<String> getPositionsAlternatives();
	
	
	public void setPosition(Position position);
	
	public void setDescription(Description description);
	
	public List<String> getLocationAsList();
	
	public String getName();
}

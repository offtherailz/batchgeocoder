package it.geosolutions.batchgeocoder.model;

import java.util.List;
/**
 * 
 * @author DamianoG
 *
 *	Interface Location provide a facade for a geographical Location. 
 *	It expose general methods for access to a metadata and geodata for a geographical location.
 *
 */
public interface Location {
	
	/**
	 * 
	 * @return a list of all alternative name for this Location, in order of importance
	 */
	public List<String> getAlternativeNames();
	
	/**
	 * 
	 * @param position Set the Position object for this location
	 */
	public void setPosition(Position position);
	
	/**
	 * 
	 * @param description Set the Description object for this location
	 */
	public void setDescription(Description description);
	
	/**
	 * 
	 * @return the rappresentation of this Location as a list in the order: name,lat,long,boundingbox coordinates
	 */
	public List<String> getLocationAsList();
	
	/**
	 * 
	 * @return the most relevant name for this location
	 */
	public String getName();
}

package it.geosolutions.geobatchcoder.persistance;

import it.geosolutions.geobatchcoder.model.Location;

import java.util.List;

public interface Input {

	
	/**
	 * @return a List of Locations
	 */
	public List<Location> getLocations();
	
	/**
	 * Load Locations from the specific DataSource
	 */
	public void loadLocations();
}

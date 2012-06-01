package it.geosolutions.geobatchcoder.composer;

import it.geosolutions.geobatchcoder.model.Location;

import java.util.List;

public interface OutputComposer {

	/**
	 * Compose both header and data
	 * @param locations a list of locations with nullable fields
	 * @return
	 */
	public List<String[]> composeAll(List<Location> locations);
	
	/**
	 * 
	 * @return Compose an header for the data
	 */
	public String[] composeHeader();
	
}

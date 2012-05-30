package it.geosolutions.geobatchcoder.search;

import it.geosolutions.geobatchcoder.model.Location;

public abstract class Search {

	/**
	 * Iterate over allAlternative Location names and select the first non empty result
	 * @param location
	 * @return the outcome of the search
	 */
	public abstract boolean calculateGeoData(Location location);
	
}

package com.geosolutions.batchgeocoder;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import au.com.bytecode.opencsv.CSVReader;
import fr.dudie.nominatim.client.NominatimClient;
import fr.dudie.nominatim.model.Address;

public class GeoDecoder {
	
	private static Logger LOG = Logger.getLogger(Canvas.class
			.getCanonicalName());
	private static String STATE_SUFFIX = ", Italia";
	
	
	public GeoDecoder(){}
	
	/**
	 * 
	 * @param indexKey
	 * @param indexValue
	 * @param indexValueAlternative
	 * @param filename
	 * @return
	 */
	public Map<String, String[]> buildMap(Integer indexKey,
			Integer indexValue, Integer indexAlternativeValue, String filename) {

		List<String[]> myEntries = null;
		try {
			CSVReader reader = new CSVReader(new FileReader(filename), ';');
			myEntries = reader.readAll();
		} catch (IOException e) {
			LOG.log(Level.SEVERE,e.getLocalizedMessage(),e);
		}
		Map<String, String[]> map = new HashMap<String, String[]>();
		for (String[] el : myEntries) {
			if (el != null && el.length > 1) {
				String[] values = new String[2];
				values[0] = el[indexValue];
				values[1] = (indexAlternativeValue == null) ? null
						: el[indexAlternativeValue];
				map.put(el[indexKey], values);
			}
		}
		return map;
	}
	
	public List<Address> doSearch(NominatimClient client, String place,
			String altPlace) {
		List<Address> result = new ArrayList<Address>();
		try {
			result = client.search(place + STATE_SUFFIX);
			if (result.size() == 0) {
				if (altPlace != null) {
					result = client.search(altPlace + STATE_SUFFIX);
				}
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		return result;
	}
	
	
}

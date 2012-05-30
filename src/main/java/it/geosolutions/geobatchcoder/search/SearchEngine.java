package it.geosolutions.geobatchcoder.search;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import it.geosolutions.geobatchcoder.model.Location;
import it.geosolutions.geobatchcoder.persistance.CSVRepositoryReader;
import it.geosolutions.geobatchcoder.persistance.CSVRepositoryWriter;
import it.geosolutions.geobatchcoder.persistance.LocationRepository;
import it.geosolutions.geobatchcoder.persistance.Output;

public class SearchEngine {

	private static Logger LOG = Logger.getLogger(SearchEngine.class
			.getCanonicalName());
	
	private LocationRepository repo;
	private Search searcher;
	private Output out;
	
	public SearchEngine(){
		//Inject from outside
		repo = new CSVRepositoryReader();
		searcher = new NominatimSearch();
		out = new CSVRepositoryWriter();
	}
	
	public void runSearch(){
		List<Location> locations = new ArrayList<Location>();
		repo.loadLocations();
		boolean outcome = false;
		List<String> discardedList = new ArrayList<String>();
		for(Location el : repo.getLocations()){
			outcome = searcher.calculateGeoData(el);
			locations.add(el);
			if(!outcome){
				discardedList.add(el.getName());
				LOG.info("Discarded " + el.getName());
			}
			else{
				LOG.info("geocoded " + el.getName());
			}
		}
		out.storeLocations(locations);
		for(String el : discardedList){
			LOG.info("Discarded " + el);
		}
		LOG.info("Discarded Number " + discardedList.size());
	}
	
	public static void main (String[] args){
		SearchEngine engine = new SearchEngine();
		engine.runSearch();
	}
	
	
}

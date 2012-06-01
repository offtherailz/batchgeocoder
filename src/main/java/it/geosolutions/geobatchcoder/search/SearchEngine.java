package it.geosolutions.geobatchcoder.search;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import it.geosolutions.geobatchcoder.model.Location;
import it.geosolutions.geobatchcoder.persistance.CSVRepositoryReader;
import it.geosolutions.geobatchcoder.persistance.CSVRepositoryWriter;
import it.geosolutions.geobatchcoder.persistance.Input;
import it.geosolutions.geobatchcoder.persistance.Output;
import it.geosolutions.geobatchcoder.persistance.OutputFileType;

public class SearchEngine {

	private static Logger LOG = Logger.getLogger(SearchEngine.class
			.getCanonicalName());
	
	private Input repo;
	private Search searcher;
	private Output listGeocoded;
	private Output outDiscarded;
	
	public SearchEngine(){
		repo = new CSVRepositoryReader();
		searcher = new NominatimSearch();
		listGeocoded = new CSVRepositoryWriter(OutputFileType.GEOCODED);
		outDiscarded = new CSVRepositoryWriter(OutputFileType.DISCARDED);
	}
	
	public void runSearch(){
		List<Location> geocodedList = new ArrayList<Location>();
		repo.loadLocations();
		boolean outcome = false;
		List<Location> discardedList = new ArrayList<Location>();
		for(Location el : repo.getLocations()){
			outcome = searcher.calculateGeoData(el);
			if(!outcome){
				discardedList.add(el);
				LOG.info("Discarded " + el.getName());
			}
			else{
				geocodedList.add(el);
				LOG.info("Geocoded " + el.getName());
			}
		}
		listGeocoded.storeLocations(geocodedList);
		outDiscarded.storeLocations(discardedList);
		LOG.info("Discarded Number " + discardedList.size());
		for(Location el : discardedList){
			LOG.info("Discarded " + el.getName());
		}
	}
}

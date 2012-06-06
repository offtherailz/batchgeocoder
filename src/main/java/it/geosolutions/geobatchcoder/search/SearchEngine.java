package it.geosolutions.geobatchcoder.search;

import it.geosolutions.geobatchcoder.model.Location;
import it.geosolutions.geobatchcoder.persistance.CSVRepositoryReader;
import it.geosolutions.geobatchcoder.persistance.CSVRepositoryWriter;
import it.geosolutions.geobatchcoder.persistance.Input;
import it.geosolutions.geobatchcoder.persistance.Output;
import it.geosolutions.geobatchcoder.persistance.OutputFileType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class SearchEngine {

	private static Logger LOG = Logger.getLogger(SearchEngine.class
			.getCanonicalName());
	
	private Input repo;
	private Search searcher;
	private Output listGeocoded;
	private Output outDiscarded;
	private Configuration conf;
	
	public SearchEngine(){
		try {
			conf = new PropertiesConfiguration("configuration.properties");
		} catch (ConfigurationException e) {
			LOG.log(Level.SEVERE, "failed to load configurations");
		}
		repo = new CSVRepositoryReader();
		searcher = new NominatimSearch(conf.getString("email"));
		listGeocoded = new CSVRepositoryWriter(OutputFileType.GEOCODED);
		outDiscarded = new CSVRepositoryWriter(OutputFileType.DISCARDED);
		
	}
	
	public void runSearch(){
		String email = conf.getString("email");
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
			
			try {
				Thread.sleep(1300);
			} catch (InterruptedException e) {
				new RuntimeException(e);
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

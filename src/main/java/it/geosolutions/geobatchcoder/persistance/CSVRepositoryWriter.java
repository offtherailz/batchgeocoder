package it.geosolutions.geobatchcoder.persistance;

import it.geosolutions.geobatchcoder.model.Location;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import au.com.bytecode.opencsv.CSVWriter;

import com.geosolutions.batchgeocoder.Canvas;

public class CSVRepositoryWriter implements Output {

	private static String OUTPUT = "src/main/resources/output.csv";
	private static Logger LOG = Logger.getLogger(Canvas.class.getCanonicalName());
	
	public CSVRepositoryWriter(){
		
	}
	
	public void storeLocations(List<Location> locations) {
		Writer writer = null;
		CSVWriter csvWriter = null;
		try {
			writer = new FileWriter(OUTPUT);
		
		csvWriter = new CSVWriter(writer, ';');
		writeHeader(csvWriter);
		List<String[]> strList = new ArrayList<String[]>();
		for(Location el : locations){
			String[] arr = new String[el.getLocationAsList().size()];
			strList.add(el.getLocationAsList().toArray(arr));
		}
		csvWriter.writeAll(strList);
		} 
		catch (IOException e) {
			LOG.log(Level.SEVERE,e.getLocalizedMessage(),e);
		}
		finally {
			try {
				writer.close();
				csvWriter.close();
			} catch (IOException e) {
				LOG.log(Level.FINE,e.getLocalizedMessage(),e);
			}
			
		}
	}

	public void appendLocations(List<Location> locations) {
		// TODO Auto-generated method stub
		
	}

	public void appendLocation(Location location) {
		// TODO Auto-generated method stub
		
	}

	private void writeHeader(CSVWriter csvWriter){
		String [] header = new String[7];
		header[0] = "NAME";
		header[1] = "LATITUDE";
		header[2] = "LONGITUDE";
		header[3] = "NORTH";
		header[4] = "SOUTH";
		header[5] = "EAST";
		header[6] = "WEST";
		csvWriter.writeNext(header);
	}

}

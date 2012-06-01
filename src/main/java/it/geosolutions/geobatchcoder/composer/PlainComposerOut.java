package it.geosolutions.geobatchcoder.composer;

import it.geosolutions.geobatchcoder.model.Location;

import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;



public class PlainComposerOut implements OutputComposer{

	public List<String[]> composeAll(List<Location> locations) {
		List<String[]> strList = new ArrayList<String[]>();
		strList.add(composeHeader());
		for(Location el : locations){
			String[] arr = new String[el.getLocationAsList().size()];
			strList.add(el.getLocationAsList().toArray(arr));
		}
		return strList;
	}
	
	public String[] composeHeader(){
		String [] header = new String[7];
		header[0] = "NAME";
		header[1] = "LATITUDE";
		header[2] = "LONGITUDE";
		header[3] = "NORTH";
		header[4] = "SOUTH";
		header[5] = "EAST";
		header[6] = "WEST";
		return header;
	}

}

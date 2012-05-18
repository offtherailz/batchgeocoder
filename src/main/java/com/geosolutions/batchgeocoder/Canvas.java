package com.geosolutions.batchgeocoder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import au.com.bytecode.opencsv.CSVReader;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKTWriter;

import fr.dudie.nominatim.client.JsonNominatimClient;
import fr.dudie.nominatim.client.NominatimClient;
import fr.dudie.nominatim.model.Address;
import fr.dudie.nominatim.model.PolygonPoint;


public class Canvas {

	
	private static Logger LOG = Logger.getLogger(Canvas.class.getCanonicalName());
	private static String STATE_SUFFIX = ", Italia";
	
	
	
	/**
	 * 
	 * @param indexKey
	 * @param indexValue
	 * @param indexValueAlternative
	 * @param filename
	 * @return
	 */
	private static Map<String, String[]> buildMap(Integer indexKey, Integer indexValue, Integer indexAlternativeValue, String filename){
		
		List<String[]> myEntries = null;
		try {
			CSVReader reader = new CSVReader(new FileReader(filename),';');
			myEntries = reader.readAll();
		} catch (IOException e) {
			LOG.severe(e.getStackTrace().toString());
		}
		Map<String, String[]> map = new HashMap<String, String[]>();
		for(String[] el : myEntries){
			if(el!=null && el.length>1){
				String[] values = new String[2];
				values[0] = el[indexValue];
				values[1] = (indexAlternativeValue==null)?null:el[indexAlternativeValue];
				map.put(el[indexKey], values);
			}
		}
		return map;
	}
	
	private static Polygon buildJTSPolygon(Address addr){
		
		PolygonPoint[] points = addr.getPolygonpoints();
		GeometryFactory geometryFactory = new GeometryFactory();

		PolygonPoint[] nominPoints = addr.getPolygonpoints();
		Coordinate[] coords = new Coordinate[nominPoints.length];
		
		int i=0;
		for(PolygonPoint el : nominPoints){
			coords[i] = new Coordinate(el.getLongitude(), el.getLatitude());
			i++;
		}
		
		LinearRing ring = geometryFactory.createLinearRing( coords );
		LinearRing holes[] = null; // use LinearRing[] to represent holes
		Polygon polygon = geometryFactory.createPolygon(ring, holes );
		return polygon;
		
	}
	
	private static void writeOnWKTFile(Address addr, Writer file){
		Polygon jtsPoly = buildJTSPolygon(addr);
		WKTWriter writer = new WKTWriter();
		try {
			writer.write(jtsPoly, file);
		} catch (IOException e) {
			LOG.log(Level.SEVERE,e.getLocalizedMessage(),e);
		}
	}
	
	private static boolean doSearch(NominatimClient client, String place, String altPlace){
		List<Address> result;
		try {
			result = client.search(place + STATE_SUFFIX);
			if(result.size()==0){
				if(altPlace!=null){
					result = client.search(altPlace + STATE_SUFFIX);
				}
				else{
					return false;
				}
				if(result.size()==0){
					return false;
				}
			}
		} catch (IOException e) {
			LOG.log(Level.SEVERE,e.getLocalizedMessage(),e);
		}
		return true;
	}
	
	
	public static void main(String [] args){
		
		Writer writer = null;
		try {
			writer = new FileWriter("D:/work/code/CulturaItalia/towngeocoder/src/main/resources/output.wkt");
		} catch (IOException e1) {
			LOG.severe(e1.getStackTrace().toString());
		}
			
		HttpClient httpClient = new DefaultHttpClient();
		NominatimClient client = new JsonNominatimClient(httpClient, "");
		
		Map<String, String[]> comuni = buildMap(2, 8, 9, "D:/work/code/CulturaItalia/towngeocoder/src/main/resources/comuni_italiani_2012.csv");
		Map<String, String[]> province = buildMap(8, 10, null, "D:/work/code/CulturaItalia/towngeocoder/src/main/resources/ripartizioni_regioni_province_2012.csv");
		Map<String, String[]> regioni = buildMap(0,2, null, "D:/work/code/CulturaItalia/towngeocoder/src/main/resources/regioni_2012.csv");
		
		Map<String, String[]> allData = new HashMap<String, String[]>();
		allData.putAll(comuni);
		allData.putAll(province);
		allData.putAll(regioni);
		
		
		int faultNumber = 0;
		List<String> faultList = new ArrayList<String>();
		int exceptions = 0;
		List<Address> result = null;
		
		for(String[] el : allData.values()){
			LOG.info("geocodecoding --> " + el[0]);
			if(doSearch(client, el[0], el[1])){
				//writeOnWKTFile(result.get(0), writer);
			}
			else{
				faultNumber++;
				faultList.add(el[0]);
			}
			
		}
		
		LOG.info("Faults number: " + faultNumber);
		LOG.info("Exceptions number: " + exceptions);
			
			
		
	}
	
	
}

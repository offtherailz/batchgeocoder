package com.geosolutions.batchgeocoder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
import au.com.bytecode.opencsv.CSVWriter;

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

	private static Logger LOG = Logger.getLogger(Canvas.class
			.getCanonicalName());
	
	private static String BASE_PATH = "D:/work/code/CulturaItalia/towngeocoder/src/main/resources/";
	private static List<String> notNull = new ArrayList<String>();
	
	private OutputCSV out;
	
	public Canvas(OutputCSV out, FileWriter writer){
		out = new OutputCSV(writer);
	}
	

	private static Polygon buildJTSPolygon(Address addr) {

		PolygonPoint[] points = addr.getPolygonpoints();
		GeometryFactory geometryFactory = new GeometryFactory();

		PolygonPoint[] nominPoints = addr.getPolygonpoints();
		Coordinate[] coords = new Coordinate[nominPoints.length];

		int i = 0;
		for (PolygonPoint el : nominPoints) {
			coords[i] = new Coordinate(el.getLongitude(), el.getLatitude());
			i++;
		}

		LinearRing ring = geometryFactory.createLinearRing(coords);
		LinearRing holes[] = null; // use LinearRing[] to represent holes
		Polygon polygon = geometryFactory.createPolygon(ring, holes);
		return polygon;

	}

//	private static void writeOnWKTFile(Address addr, Writer file) {
//		Polygon jtsPoly = buildJTSPolygon(addr);
//		WKTWriter writer = new WKTWriter();
//		try {
//			writer.write(jtsPoly, file);
//		} catch (IOException e) {
//			LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
//		}
//	}
	
//	private static void writeOnCSV(Address addr, Writer file) {
//		CSVWriter writer = new CSVWriter(file, ';');
//		writer.
//	}
	

	

	

	public static void main(String[] args) {

		Writer writer = null;
		try {
			writer = new FileWriter(
					BASE_PATH + "output.wkt");
		} catch (IOException e1) {
			LOG.log(Level.SEVERE,e1.getLocalizedMessage(),e1);
		}
		GeoDecoder geoDecoder = new GeoDecoder();
		OutputCSV out = new OutputCSV(writer);
		
		HttpClient httpClient = new DefaultHttpClient();
		NominatimClient client = new JsonNominatimClient(httpClient, "");
		Map<String, String[]> comuni = geoDecoder.buildMap(2,8,9,BASE_PATH + "comuni_italiani_2012.csv");
		Map<String, String[]> province = geoDecoder.buildMap(8,10,null,BASE_PATH + "ripartizioni_regioni_province_2012.csv");
		Map<String, String[]> regioni = geoDecoder.buildMap(0, 2, null,BASE_PATH + "regioni_2012.csv");

		Map<String, String[]> allData = new HashMap<String, String[]>();
		allData.putAll(comuni);
		allData.putAll(province);
		allData.putAll(regioni);

		int faultNumber = 0;
		List<String> faultList = new ArrayList<String>();
		int exceptions = 0;

		for (String[] el : allData.values()) {
			LOG.info("geocodecoding --> " + el[0]);
			List<Address> results = geoDecoder.doSearch(client, el[0], el[1]);
			if (results.size() > 0) {
				CSVEntryBean bean = new CSVEntryBean();
				Address addr = results.get(0);
				bean.setName(el[0]);
				bean.setLatitude(addr.getLatitude());
				bean.setLatitude(addr.getLongitude());
				bean.setBoundingNorth(addr.getBoundingBox().getNorth());
				bean.setBoundingSouth(addr.getBoundingBox().getSouth());
				bean.setBoundingEast(addr.getBoundingBox().getEast());
				bean.setBoundingWest(addr.getBoundingBox().getWest());
				out.addToList(bean);
				// checkPolygonPointNotNull(results);
				// writeOnWKTFile(results.get(0), writer);
			} else {
				faultNumber++;
				faultList.add(el[0]);
			}

		}
		out.writeOnCSV();
		LOG.info("Exceptions occurred : " + exceptions);
		LOG.info("Faults number       : " + faultNumber);
		LOG.info("Faults list : " + exceptions);
		for (String el : faultList) {
			LOG.info("\t-> : " + el);
		}

	}

	public static void checkPolygonPointNotNull(List<Address> list) {

		for (Address el : list) {
			if (el.getPolygonpoints() != null) {
				notNull.add(el.getDisplayName());
			}
		}
	}

}

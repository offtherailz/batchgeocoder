package com.geosolutions.batchgeocoder;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import au.com.bytecode.opencsv.CSVWriter;

public class OutputCSV {

	private static String BASE_PATH = "D:/work/code/CulturaItalia/towngeocoder/src/main/resources/";
	private static Logger LOG = Logger.getLogger(Canvas.class
			.getCanonicalName());
	private List<CSVEntryBean> list;
	private Writer writer;
	
	public OutputCSV(Writer writer){
		this.list = new ArrayList<CSVEntryBean>();
		this.writer = writer;
	}
	
	
	public void addToList(CSVEntryBean bean){
		list.add(bean);
	}
	
	public void writeOnCSV(){
		CSVWriter csvWriter = new CSVWriter(writer, ';');
		writeHeader(csvWriter);
		List<String[]> strList = new ArrayList<String[]>();
		for(CSVEntryBean el : list){
			strList.add(el.toStringArray());
		}
		csvWriter.writeAll(strList);
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

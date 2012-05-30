package it.geosolutions.geobatchcoder.model;

import java.util.ArrayList;
import java.util.List;


public class Description {

	private String[] data;
	
	public void setDescription(String[] data){
		this.data = data;
	}
	
//	public String getKey(){
//		return data[0];
//	}
	
	public List<String> getAllAlternatives(){
		List<String> list = new ArrayList<String>();
		list.add(data[1]);
		list.add(data[2]);
		return list;
	}
	
	public String getId(){
		return data[0];
	}
	
	public String getName(){
		return data[1];
	}
	
	public String getAlternativeName(){
		return data[2];
	}
}

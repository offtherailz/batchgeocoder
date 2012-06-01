package it.geosolutions.geobatchcoder.launcher;

import it.geosolutions.geobatchcoder.search.SearchEngine;

public class Start {
	
	public static void main (String[] args){
		SearchEngine engine = new SearchEngine();
		engine.runSearch();
	}
}

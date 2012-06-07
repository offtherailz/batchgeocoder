package it.geosolutions.batchgeocoder.launcher;

/**
 * The entry point for execute bulk translations.
 * @author DamianoG
 *
 */
public class Start {
	
	public static void main (String[] args){
		SearchEngine engine = new SearchEngine();
		engine.runSearch();
	}
}

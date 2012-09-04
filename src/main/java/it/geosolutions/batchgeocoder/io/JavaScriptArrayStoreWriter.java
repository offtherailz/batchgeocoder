package it.geosolutions.batchgeocoder.io;

import it.geosolutions.batchgeocoder.composer.OutputComposer;
import it.geosolutions.batchgeocoder.composer.PlainComposerOut;
import it.geosolutions.batchgeocoder.model.Location;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONException;
import org.json.JSONWriter;
/**
 * Generates a GeoReferences JavaScript Array.(Useful for MapStore)
 * @author lorenzo
 *
 */
public class JavaScriptArrayStoreWriter implements Output {

	private static Logger LOG = Logger.getLogger(JavaScriptArrayStoreWriter.class.getCanonicalName());
	private OutputComposer outComposer;
	private String outputFileName;

	public JavaScriptArrayStoreWriter(OutputFileType type) {
		outComposer = new PlainComposerOut();
		Configuration conf = null;
		try {
			conf = new PropertiesConfiguration("configuration.properties");
		} catch (ConfigurationException e) {
			LOG.log(Level.SEVERE, "failed to load configurations");
		}
		switch (type) {
		case GEOCODED:
			outputFileName = conf.getString("fileNameOut.geocoded");
			break;
		case DISCARDED:
			outputFileName = conf.getString("fileNameOut.discarded");
			break;
		}

	}

	@Override
	public void storeLocations(List<Location> locations) {
		Writer writer = null;
		JSONWriter jsonWriter = null;
		try {
			writer = new FileWriter(outputFileName);

			jsonWriter = new JSONWriter(writer);
			List<String[]> strList = outComposer.composeAll(locations);
			jsonWriter.array();
			for (String[] as : strList) {
				jsonWriter.array();
				jsonWriter.value(as[0]);
				jsonWriter.value(as[3]+","+as[6]+","+as[4]+","+as[5]);
				
				jsonWriter.endArray();
			}
			jsonWriter.endArray();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (JSONException e) {
			LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			try {
				writer.close();
				// jsonWriter.close();
			} catch (IOException e) {
				LOG.log(Level.FINE, e.getLocalizedMessage(), e);
			}

		}

	}

	@Override
	public void appendLocations(List<Location> locations) {
		// TODO Auto-generated method stub

	}

	@Override
	public void appendLocation(Location location) {
		// TODO Auto-generated method stub

	}

}

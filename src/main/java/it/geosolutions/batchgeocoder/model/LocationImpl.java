package it.geosolutions.batchgeocoder.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;

/**
 * 
 * @author DamianoG
 * Provide a default implementation for location Object
 */
public class LocationImpl implements Location{
	
	
	private Description description;
	private Position position;
	
	/*
	 * (non-Javadoc)
	 * @see it.geosolutions.geobatchcoder.model.Location#getLocationAsList()
	 */
	@Override
	public List<String> getLocationAsList() {
		List<String> list = new ArrayList<String>();
		list.add(description.getName());
		list.addAll(position.getPositionAsList());
		return list;
	}
	
	/*
	 * (non-Javadoc)
	 * @see it.geosolutions.geobatchcoder.model.Location#getAlternativeNames()
	 */
	@Override
	public List<String> getAlternativeNames() {
		return description.getAllAlternatives();
	}

	/*
	 * (non-Javadoc)
	 * @see it.geosolutions.geobatchcoder.model.Location#setPosition(it.geosolutions.geobatchcoder.model.Position)
	 */
	@Override
	public void setPosition(Position position) {
		this.position = position;
	}
	
	/*
	 * (non-Javadoc)
	 * @see it.geosolutions.geobatchcoder.model.Location#setDescription(it.geosolutions.geobatchcoder.model.Description)
	 */
	@Override
	public void setDescription(Description description) {
		this.description = description;
	}
	
	/*
	 * (non-Javadoc)
	 * @see it.geosolutions.geobatchcoder.model.Location#getName()
	 */
	@Override
	public String getName(){
		return this.description.getName();
	}
	
	/*
	 * TODO NOT FINISHED YET
	 */
	public Polygon getJTSBoundingBox(){
		Polygon jtsPoly = buildJTSPolygon();
		return jtsPoly;
	}
	
	/*
	 * TODO NOT FINISHED YET
	 */
	private Polygon buildJTSPolygon() {

		Map<String, Double> bounds = position.getBoundingBoxPoints();
		double lat = position.getLatitude();
		double lon = position.getLongitude();
		
		Coordinate[] coords = new Coordinate[4];
		coords[0] = new Coordinate(lon, lat - bounds.get("north"));
		coords[1] = new Coordinate(lon, lat + bounds.get("south"));
		coords[2] = new Coordinate(lon - bounds.get("weast"), lat);
		coords[3] = new Coordinate(lon + bounds.get("east"), lat);
		
		GeometryFactory geometryFactory = new GeometryFactory();
		
		LinearRing ring = geometryFactory.createLinearRing(coords);
		LinearRing holes[] = null; // use LinearRing[] to represent holes
		Polygon polygon = geometryFactory.createPolygon(ring, holes);
		return polygon;

	}
	
}

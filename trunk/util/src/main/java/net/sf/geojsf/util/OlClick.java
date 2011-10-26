package net.sf.geojsf.util;

import java.io.Serializable;

import net.sf.geojsf.xml.gml.Coordinates;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OlClick implements Serializable
{
	static Log logger = LogFactory.getLog(OlClick.class);
	
	public static final long serialVersionUID=1;
	
	private String lat,lon;
	private String scale,resolution;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<

	public OlClick()
	{
		
	}
	
	// >>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(" lon="+lon);
			sb.append(" lat="+lat);
			sb.append(" resolution="+resolution);
			sb.append(" scale="+scale);
		return sb.toString();
	}
	
	public double toLat() {return new Double(lat);}
	public double toLon() {return new Double(lon);}
	public double toScale() {return new Double(scale);}
	public double toResolution() {return new Double(resolution);}
	
	// >>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<
	
	public String getLat() {return lat;}
	public void setLat(String lat) {this.lat = lat;}

	public String getLon() {return lon;}
	public void setLon(String lon) {this.lon = lon;}
	
	public String getScale() {return scale;}
	public void setScale(String scale) {this.scale = scale;}

	public String getResolution() {return resolution;}
	public void setResolution(String resolution) {this.resolution = resolution;}
	
	public Coordinates toGmlCoordinates()
	{
		Coordinates coordinates = new Coordinates();
		coordinates.setValue(lon+","+lat);
		return coordinates;
	}
}

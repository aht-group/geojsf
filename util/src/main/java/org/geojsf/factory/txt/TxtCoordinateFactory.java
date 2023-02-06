package org.geojsf.factory.txt;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.geojsf.model.xml.geojsf.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.geom.Point;

public class TxtCoordinateFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtCoordinateFactory.class);
	
	private DecimalFormat df;
	
	public TxtCoordinateFactory()
	{
		df = new DecimalFormat("#.00");
		df.setRoundingMode(RoundingMode.HALF_EVEN);
	}
	
	public String build(Coordinate coordinate)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Latitude ").append(df.format(coordinate.getLat()));
		sb.append(" Longitude:").append(df.format(coordinate.getLon()));
		sb.append(" (Remember: lines of latitude run east/west, like equator with latitude 0)");
		return sb.toString();
	}
	
	public String build(Point point)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("lat:").append(df.format(point.getX()));
		sb.append(" lon:").append(df.format(point.getY()));
		sb.append(" ... we need to verify lat/log");
		return sb.toString();
	}
}
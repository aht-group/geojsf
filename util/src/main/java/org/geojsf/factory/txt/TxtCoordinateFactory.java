package org.geojsf.factory.txt;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.geojsf.xml.geojsf.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtCoordinateFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtCoordinateFactory.class);
	
	private DecimalFormat df;
	
	public TxtCoordinateFactory()
	{
		df = new DecimalFormat("#.0");
		df.setRoundingMode(RoundingMode.HALF_EVEN);
	}
	
	public String build(Coordinate coordinate)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("lat:").append(df.format(coordinate.getLat()));
		sb.append(" lon:").append(df.format(coordinate.getLon()));
		return sb.toString();
	}
}
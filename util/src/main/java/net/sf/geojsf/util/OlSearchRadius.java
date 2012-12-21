package net.sf.geojsf.util;

import java.io.Serializable;


import org.geojsf.xml.ogc.Distance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OlSearchRadius implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(OlSearchRadius.class);
	
	public static final long serialVersionUID=1;
	
	private int pixel;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<

	public OlSearchRadius()
	{
		pixel = 3;
	}
	
	// >>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(" pixel="+pixel);
		return sb.toString();
	}
	
	public Distance createSearchDistance(OlClick click)
	{	
		double d = pixel * click.toResolution();
		
		Distance distance = new Distance();
		distance.setUnits("degree");
		distance.setValue(Double.toString(d));
		
		return distance;
	}
	
	// >>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<
	
	public int getPixel() {return pixel;}
	public void setPixel(int pixel) {this.pixel = pixel;}
}

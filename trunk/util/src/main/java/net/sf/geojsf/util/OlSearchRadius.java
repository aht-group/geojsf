package net.sf.geojsf.util;

import java.io.Serializable;

import net.sf.geojsf.xml.ogc.Distance;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OlSearchRadius implements Serializable
{
	static Log logger = LogFactory.getLog(OlSearchRadius.class);
	
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

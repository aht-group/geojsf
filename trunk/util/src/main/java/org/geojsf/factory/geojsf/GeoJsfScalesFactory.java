package org.geojsf.factory.geojsf;

import org.geojsf.factory.xml.geojsf.XmlScaleFactory;
import org.geojsf.xml.geojsf.Scales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfScalesFactory
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfScalesFactory.class);
	
	public static Scales build(int... scales)
	{
		Scales xml = new Scales();
		for(int scale : scales)
		{
			xml.getScale().add(XmlScaleFactory.build(scale));
		}
		return xml;
	}
}
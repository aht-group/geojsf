package org.geojsf.factory.xml.geojsf;

import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.model.xml.geojsf.Scale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlScaleFactory <VP extends GeoJsfViewPort> 
{
	final static Logger logger = LoggerFactory.getLogger(XmlScaleFactory.class);

	public Scale build(VP ejb)
	{
		Scale scale = new Scale();
		scale.setValue((new Double(ejb.getScale()).intValue()));
		return scale;
	}
	
	public static Scale build(double scale)
	{
		Scale xml = new Scale();
		xml.setValue((new Double(scale).intValue()));
		return xml;
	}
}
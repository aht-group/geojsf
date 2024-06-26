package org.geojsf.factory.xml.specs.gml;

import java.io.Serializable;

import org.geojsf.model.xml.geojsf.Coordinate;
import org.geojsf.model.xml.specs.gml.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlCoordinatesFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlCoordinatesFactory.class);
	public static final long serialVersionUID=1;
	
	public static Coordinates build (Coordinate coordinate)
	{
		Coordinates xml = new Coordinates();
		

		 xml.setValue(coordinate.getLon()+","+coordinate.getLat());
		

		
		return xml;
	}
}

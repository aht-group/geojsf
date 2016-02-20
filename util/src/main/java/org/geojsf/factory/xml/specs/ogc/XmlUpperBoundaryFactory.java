package org.geojsf.factory.xml.specs.ogc;

import java.io.Serializable;

import org.geojsf.model.xml.specs.ogc.UpperBoundary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUpperBoundaryFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlUpperBoundaryFactory.class);
	public static final long serialVersionUID=1;
	
	public static UpperBoundary build(String property, double value)
	{
		UpperBoundary xml = new UpperBoundary();
		xml.setFunction(XmlFunctionFactory.env(property, ""+value));
		return xml;
	}
}
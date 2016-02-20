package org.geojsf.factory.xml.specs.ogc;

import java.io.Serializable;

import org.geojsf.model.xml.specs.ogc.LowerBoundary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlLowerBoundaryFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlLowerBoundaryFactory.class);
	public static final long serialVersionUID=1;
	
	public static LowerBoundary build(String property, double value)
	{
		LowerBoundary xml = new LowerBoundary();
		xml.setFunction(XmlFunctionFactory.env(property, ""+value));
		return xml;
	}
}
package org.geojsf.factory.xml.specs.ogc;

import java.io.Serializable;

import org.geojsf.model.xml.specs.ogc.PropertyIsBetween;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlPropertyIsBetweenFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlPropertyIsBetweenFactory.class);
	public static final long serialVersionUID=1;
	
	public static PropertyIsBetween build(String property, String lowerAttribute, double lowerValue, String upperAttribute, double upperValue)
	{
		PropertyIsBetween xml = new PropertyIsBetween();
		xml.setPropertyName(XmlPropertyNameFactory.build(property));
		xml.setLowerBoundary(XmlLowerBoundaryFactory.build(lowerAttribute, lowerValue));
		xml.setUpperBoundary(XmlUpperBoundaryFactory.build(upperAttribute, upperValue));
		return xml;
	}
}
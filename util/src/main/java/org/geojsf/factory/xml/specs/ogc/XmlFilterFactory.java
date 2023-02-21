package org.geojsf.factory.xml.specs.ogc;

import java.io.Serializable;

import org.geojsf.model.xml.specs.ogc.And;
import org.geojsf.model.xml.specs.ogc.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFilterFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlFilterFactory.class);
	public static final long serialVersionUID=1;

	public static Filter build(){return new Filter();}
	
	public static Filter interval(String property, String lowerAttribute, double lowerValue, String upperAttribute, double upperValue)
	{
		Filter xml = build();
		xml.setPropertyIsBetween(XmlPropertyIsBetweenFactory.build(property,lowerAttribute,lowerValue,upperAttribute,upperValue));
		return xml;
	}

	public static Filter equal(String property, long attribute){return equal(property, ""+attribute);}
	public static Filter equal(String property, String attribute)
	{
		Filter xml = build();
		xml.setPropertyIsEqualTo(XmlPropertyIsEqualToFactory.build(property, attribute));
		return xml;
	}

	public static Filter andFilter()
	{
		Filter xml = build();
		xml.setAnd(new And());
		return xml;

	}
}
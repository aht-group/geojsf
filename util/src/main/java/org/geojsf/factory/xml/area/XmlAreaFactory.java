package org.geojsf.factory.xml.area;

import java.io.Serializable;

import org.geojsf.model.xml.area.Area;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlAreaFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlAreaFactory.class);
	public static final long serialVersionUID=1;
	
	public static Area build() {return new Area();}
}

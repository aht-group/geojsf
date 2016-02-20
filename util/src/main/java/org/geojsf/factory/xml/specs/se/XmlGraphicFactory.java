package org.geojsf.factory.xml.specs.se;

import java.io.Serializable;

import org.geojsf.model.xml.specs.se.Graphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlGraphicFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlGraphicFactory.class);
	public static final long serialVersionUID=1;
	
	public static Graphic mark(String wkn)
	{
		Graphic xml = new Graphic();
		xml.setMark(XmlMarkFactory.build(wkn));
		return xml;
	}
	
	public static Graphic external(String href)
	{
		Graphic xml = new Graphic();
		xml.setExternalGraphic(XmlExternalGraphicFactory.svg(href));
		return xml;
	}
}
package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.model.xml.specs.sld.ExternalGraphic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlExternalGraphicFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlExternalGraphicFactory.class);
	public static final long serialVersionUID=1;
	
	public static ExternalGraphic svg(String href)
	{
		ExternalGraphic xml = new ExternalGraphic();
		xml.setOnlineResource(XmlOnlineResourceFactory.build(href));
		xml.setFormat(XmlFormatFactory.svg());
		return xml;
	}
}
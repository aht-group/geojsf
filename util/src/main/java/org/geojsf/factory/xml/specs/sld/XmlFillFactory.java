package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.model.xml.specs.sld.Fill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFillFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlFillFactory.class);
	public static final long serialVersionUID=1;
	
	public static Fill build() {return new Fill();}
	
	public static Fill build(String attribute)
	{
		Fill xml = build();
		
		xml.getCssParameter().add(XmlCssParameterFactory.function("fill",attribute, "#000000"));
		xml.getCssParameter().add(XmlCssParameterFactory.value("fill-opacity", "0.9"));
		
		return xml;
	}
}
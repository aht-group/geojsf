package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.model.xml.specs.sld.NamedLayer;
import org.geojsf.util.SldConfigurationProvider;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlNamedLayerFactory <L extends JeeslLang,
									LAYER extends GeoJsfLayer<L,?,?,?,?,?,?,SLD>,
									SLD extends GeoJsfSld<L,?,?,?,RULE,?,?>,
									RULE extends GeoJsfSldRule<L,?,?>>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlNamedLayerFactory.class);
	public static final long serialVersionUID=1;
	
	private XmlUserStyleFactory<L,SLD,RULE> xfUserStyle;
	
	public XmlNamedLayerFactory(final SldConfigurationProvider sldCp)
	{
		xfUserStyle = new XmlUserStyleFactory<>(sldCp);
	}
	
	public NamedLayer build(LAYER layer)
	{
		NamedLayer xml = build();
		xml.setName(XmlNameFactory.build(layer.getService().getCode()+":"+layer.getCode()));
		xml.setUserStyle(xfUserStyle.build(layer.getSld()));
		return xml;
	}
	
	public static NamedLayer build()
	{
		NamedLayer xml = new NamedLayer();
		return xml;
	}
}

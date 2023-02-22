package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.model.xml.specs.sld.NamedLayer;
import org.geojsf.util.SldConfigurationProvider;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlNamedLayerFactory <L extends JeeslLang,
									LAYER extends GeoJsfLayer<L,?,?,?,?,?,?,SLD>,
									SLD extends GeoJsfSld<L,?,?,?,RULE,LE,LA>,
									RULE extends GeoJsfSldRule<L,?,?>,
									LE extends JeeslRevisionEntity<L,?,?,?,LA,?>,
									LA extends JeeslRevisionAttribute<L,?,LE,?,?>>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlNamedLayerFactory.class);
	public static final long serialVersionUID=1;
	
	private final SldConfigurationProvider scp;
	private XmlUserStyleFactory<L,SLD,RULE,LE,LA> xfUserStyle;
	
	public XmlNamedLayerFactory(final SldConfigurationProvider scp)
	{
		this.scp=scp;
		xfUserStyle = new XmlUserStyleFactory<>(scp);
	}
	
	public static NamedLayer build()
	{
		NamedLayer xml = new NamedLayer();
		return xml;
	}
	
	public NamedLayer build(LAYER layer)
	{
		NamedLayer xml = build();
		xml.setName(XmlNameFactory.build(layer.getService().getCode()+":"+layer.getCode()));
		xml.setUserStyle(xfUserStyle.build(layer.getSld()));
		return xml;
	}
	
	public NamedLayer build(SLD sld)
	{
		NamedLayer xml = build();
		xml.setName(XmlNameFactory.build(sld.getName().get(scp.getLocaleCode()).getLang()));
		xml.setUserStyle(xfUserStyle.build(sld));
		return xml;
	}	
}
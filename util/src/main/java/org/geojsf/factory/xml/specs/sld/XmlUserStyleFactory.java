package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.model.xml.specs.sld.UserStyle;
import org.geojsf.util.SldConfigurationProvider;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUserStyleFactory <L extends JeeslLang,
									SLD extends GeoJsfSld<L,?,?,?,RULE,LE,LA>,
									RULE extends GeoJsfSldRule<L,?,?>,
									LE extends JeeslRevisionEntity<L,?,?,?,LA,?>,
									LA extends JeeslRevisionAttribute<L,?,LE,?,?>>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlUserStyleFactory.class);
	public static final long serialVersionUID=1;
	
	private final SldConfigurationProvider scp;
	
	private XmlFeatureTypeStyleFactory<L,SLD,RULE,LE,LA> xfFeatureTypeStyle;
	
	public XmlUserStyleFactory(final SldConfigurationProvider scp)
	{
		this.scp=scp;
		xfFeatureTypeStyle = new XmlFeatureTypeStyleFactory<>(scp);
	}
	
	public UserStyle build(SLD sld)
	{
		UserStyle xml = build();
		xml.setName(XmlNameFactory.build(sld.getName().get(scp.getLocaleCode()).getLang()));
		xml.setFeatureTypeStyle(xfFeatureTypeStyle.build(sld));
		return xml;
	}
	
	public static UserStyle build()
	{
		UserStyle xml = new UserStyle();
		return xml;
	}
}
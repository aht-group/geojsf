package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.model.xml.specs.sld.UserStyle;
import org.geojsf.util.SldConfigurationProvider;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUserStyleFactory <L extends JeeslLang,
									SLD extends GeoJsfSld<L,?,?,?,RULE>,
									RULE extends GeoJsfSldRule<L,?,?>>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlUserStyleFactory.class);
	public static final long serialVersionUID=1;
	
	private final SldConfigurationProvider sldCp;
	private XmlFeatureTypeStyleFactory<L,SLD,RULE> xfFeatureTypeStyle;
	
	public XmlUserStyleFactory(final SldConfigurationProvider sldCp)
	{
		this.sldCp=sldCp;
		xfFeatureTypeStyle = new XmlFeatureTypeStyleFactory<>(sldCp);
	}
	
	public UserStyle build(SLD sld)
	{
		UserStyle xml = build();
		xml.setName(XmlNameFactory.build(sld.getName().get(sldCp.getLocaleCode()).getLang()));
		xml.setFeatureTypeStyle(xfFeatureTypeStyle.build(sld));
		return xml;
	}
	
	public static UserStyle build()
	{
		UserStyle xml = new UserStyle();
		return xml;
	}
}
package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.factory.xml.specs.sld.rule.XmlRuleStatusFactory;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.geojsf.model.xml.specs.sld.FeatureTypeStyle;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFeatureTypeStyleFactory <L extends JeeslLang,
										SLD extends GeoJsfSld<L,?,?,?,RULE>,
										RULE extends GeoJsfSldRule<L,?,?>>
						implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlFeatureTypeStyleFactory.class);
	public static final long serialVersionUID=1;
	
	private XmlRuleStatusFactory<L,SLD,RULE> xfRuleStatus;
	
	public XmlFeatureTypeStyleFactory(final SldConfigurationProvider sldCp)
	{
		xfRuleStatus = new XmlRuleStatusFactory<>(sldCp);
	}
	
	public FeatureTypeStyle build(SLD sld)
	{
		FeatureTypeStyle xml = build();
		xml.setName(XmlNameFactory.build("xx"));
		
		if(sld.getType().getCode().equals(GeoJsfSldType.Type.status.toString()))
		{
			logger.info(sld.toString());
			xml.getRule().addAll(xfRuleStatus.build(sld));
		}
		
		return xml;
	}
	
	public static FeatureTypeStyle build()
	{
		FeatureTypeStyle xml = new FeatureTypeStyle();
		return xml;
	}
}
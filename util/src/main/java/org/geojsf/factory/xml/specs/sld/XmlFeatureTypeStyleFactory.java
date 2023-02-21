package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.model.xml.specs.sld.FeatureTypeStyle;
import org.geojsf.util.SldConfigurationProvider;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.io.label.entity.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFeatureTypeStyleFactory <L extends JeeslLang,
										SLD extends GeoJsfSld<L,?,?,?,RULE,LE,LA>,
										RULE extends GeoJsfSldRule<L,?,?>,
										LE extends JeeslRevisionEntity<L,?,?,?,LA,?>,
										LA extends JeeslRevisionAttribute<L,?,LE,?,?>>
						implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlFeatureTypeStyleFactory.class);
	public static final long serialVersionUID=1;
	
	private XmlRuleFactory<L,SLD,RULE,LE,LA> xfRule;
	
	public XmlFeatureTypeStyleFactory(final SldConfigurationProvider sldCp)
	{
		xfRule = new XmlRuleFactory<>(sldCp);
	}
	
	public static FeatureTypeStyle build() {return new FeatureTypeStyle();}
	public FeatureTypeStyle build(SLD sld)
	{
		FeatureTypeStyle xml = build();
		xml.setName(XmlNameFactory.build("xx"));
		
		xml.getRule().addAll(xfRule.build(sld));
		
		return xml;
	}
	
	
}
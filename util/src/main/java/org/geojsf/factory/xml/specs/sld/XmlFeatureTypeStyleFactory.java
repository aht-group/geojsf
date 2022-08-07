package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.factory.xml.specs.sld.rule.XmlRuleStatusFactory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.model.sld.GeoJsfSldType;
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.geojsf.model.xml.specs.sld.FeatureTypeStyle;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicComponent;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFeatureTypeStyleFactory <L extends JeeslLang,D extends JeeslDescription,
										G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
										F extends JeeslGraphicComponent<L,D,G,GT,F,FS>, FS extends JeeslStatus<L,D,FS>,
										LAYER extends GeoJsfLayer<L,D,?,?,?,?,SLD>,
										MAP extends GeoJsfMap<L,D,?,?,?>,
										SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
										SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
										SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
										RULE extends GeoJsfSldRule<L,D,G>>
						implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlFeatureTypeStyleFactory.class);
	public static final long serialVersionUID=1;
	
	private XmlRuleStatusFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> xfRuleStatus;
	
	
	public XmlFeatureTypeStyleFactory(final SldConfigurationProvider sldCp)
	{
		xfRuleStatus = new XmlRuleStatusFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE>(sldCp);
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
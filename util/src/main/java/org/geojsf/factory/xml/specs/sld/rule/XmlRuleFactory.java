package org.geojsf.factory.xml.specs.sld.rule;

import java.io.Serializable;

import org.geojsf.factory.xml.specs.sld.XmlDescriptionFactory;
import org.geojsf.factory.xml.specs.sld.XmlNameFactory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.geojsf.model.xml.specs.sld.Rule;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlRuleFactory <L extends JeeslLang,D extends JeeslDescription,
								G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
								F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<L,D,FS>,
								SERVICE extends GeoJsfService<L,D,LAYER>,
								LAYER extends GeoJsfLayer<L,D,?,SERVICE,?,?,SLD>,
								MAP extends GeoJsfMap<L,D,?,VIEW,?>,
								SCALE extends GeoJsfScale<L,D>,
								VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
								SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
								SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
								SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
								RULE extends GeoJsfSldRule<L,D,G>> 
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlRuleFactory.class);
	public static final long serialVersionUID=1;
	
	public XmlRuleFactory(final SldConfigurationProvider sldCp)
	{
		
	}
	
	public static Rule build(String name)
	{
		Rule xml = new Rule();
		xml.setName(XmlNameFactory.build(name));
		return xml;
	}
	
	public static Rule build(String name, String title)
	{
		Rule xml = new Rule();
		xml.setName(XmlNameFactory.build(name));
		if(title!=null) {xml.setDescription(XmlDescriptionFactory.title(title));}
		return xml;
	}
}
package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.geojsf.model.xml.specs.sld.UserStyle;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUserStyleFactory <L extends JeeslLang,D extends JeeslDescription,
									G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
									F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends JeeslStatus<L,D,FS>,
									LAYER extends GeoJsfLayer<L,D,?,?,?,?,SLD>,
									MAP extends GeoJsfMap<L,D,?,?,?>,
									SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
									SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,
									SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
									RULE extends GeoJsfSldRule<L,D,G>>
				implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlUserStyleFactory.class);
	public static final long serialVersionUID=1;
	
	private final SldConfigurationProvider sldCp;
	private XmlFeatureTypeStyleFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> xfFeatureTypeStyle;
	
	public XmlUserStyleFactory(final SldConfigurationProvider sldCp)
	{
		this.sldCp=sldCp;
		xfFeatureTypeStyle = new XmlFeatureTypeStyleFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE>(sldCp);
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

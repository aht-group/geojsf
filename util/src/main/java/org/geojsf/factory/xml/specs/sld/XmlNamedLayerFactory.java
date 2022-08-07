package org.geojsf.factory.xml.specs.sld;

import java.io.Serializable;

import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.interfaces.provider.SldConfigurationProvider;
import org.geojsf.model.xml.specs.sld.NamedLayer;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicComponent;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlNamedLayerFactory <L extends JeeslLang,D extends JeeslDescription,
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
	final static Logger logger = LoggerFactory.getLogger(XmlNamedLayerFactory.class);
	public static final long serialVersionUID=1;
	
	private XmlUserStyleFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE> xfUserStyle;
	
	public XmlNamedLayerFactory(final SldConfigurationProvider sldCp)
	{
		xfUserStyle = new XmlUserStyleFactory<L,D,G,GT,F,FS,LAYER,MAP,SLDTEMPLATE,SLDTYPE,SLD,RULE>(sldCp);
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

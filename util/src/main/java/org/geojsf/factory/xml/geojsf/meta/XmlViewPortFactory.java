package org.geojsf.factory.xml.geojsf.meta;

import java.io.Serializable;

import org.geojsf.factory.xml.geojsf.XmlScaleFactory;
import org.geojsf.interfaces.model.core.GeoJsfCategory;
import org.geojsf.interfaces.model.core.GeoJsfLayer;
import org.geojsf.interfaces.model.core.GeoJsfMap;
import org.geojsf.interfaces.model.core.GeoJsfService;
import org.geojsf.interfaces.model.core.GeoJsfView;
import org.geojsf.interfaces.model.meta.GeoJsfDataSource;
import org.geojsf.interfaces.model.meta.GeoJsfScale;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSld;
import org.geojsf.interfaces.model.sld.GeoJsfSldRule;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.model.xml.geojsf.Query;
import org.geojsf.model.xml.geojsf.ViewPort;
import org.jeesl.interfaces.model.system.graphic.component.JeeslGraphicComponent;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicType;
import org.jeesl.interfaces.model.system.locale.JeeslDescription;
import org.jeesl.interfaces.model.system.locale.JeeslLang;
import org.jeesl.interfaces.model.system.locale.status.JeeslStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlViewPortFactory implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewPortFactory.class);
	
	public static final long serialVersionUID=1;

	private ViewPort q;
	
	public XmlViewPortFactory(Query query){this(query.getViewPort());}
	public XmlViewPortFactory(ViewPort q)
	{
		this.q=q;
	}
	
	public <L extends JeeslLang,D extends JeeslDescription,
			G extends JeeslGraphic<L,D,GT,F,FS>, GT extends JeeslGraphicType<L,D,GT,G>,
			F extends JeeslGraphicComponent<L,D,G,GT,F,FS>, FS extends JeeslStatus<L,D,FS>,
			CATEGORY extends GeoJsfCategory<L,D,LAYER>,
			SERVICE extends GeoJsfService<L,D,LAYER>,
			LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
			MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
			SCALE extends GeoJsfScale<L,D>, 
			VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
			VP extends GeoJsfViewPort,
			DS extends GeoJsfDataSource<L,D,LAYER>,
			SLDTYPE extends JeeslStatus<L,D,SLDTYPE>,SLDSTYLE extends JeeslStatus<L,D,SLDSTYLE>,
			SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTEMPLATE,SLDTYPE>,
			SLD extends GeoJsfSld<L,D,SLDTEMPLATE,SLDTYPE,RULE>,
			RULE extends GeoJsfSldRule<L,D,G>> 
		ViewPort build (VP ejb)
	{
		ViewPort xml = new ViewPort();
		
		if(q.isSetId()){xml.setId(ejb.getId());}
		
		if(q.isSetLat()){xml.setLat(ejb.getLat());}
		if(q.isSetLon()){xml.setLon(ejb.getLon());}
		
		if(q.isSetLeft()){xml.setLeft(ejb.getMarginLeft());}
		if(q.isSetRight()){xml.setRight(ejb.getMarginRight());}
		if(q.isSetTop()){xml.setTop(ejb.getMarginTop());}
		if(q.isSetBottom()){xml.setBottom(ejb.getMarginBottom());}
		
		if(q.isSetScale())
		{
			xml.setScale(XmlScaleFactory.build(ejb));
		}
		
		return xml;
	}
	
	public static ViewPort build(){return new ViewPort();}
	public static ViewPort build(double lon, double lat)
	{
		ViewPort xml = build();
		xml.setLon(lon);
		xml.setLat(lat);
		return xml;
	}
}

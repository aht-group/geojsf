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
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphic;
import org.jeesl.interfaces.model.system.graphic.core.JeeslGraphicFigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

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
	
	public <L extends UtilsLang,D extends UtilsDescription,
			G extends JeeslGraphic<L,D,G,GT,F,FS>, GT extends UtilsStatus<GT,L,D>,
			F extends JeeslGraphicFigure<L,D,G,GT,F,FS>, FS extends UtilsStatus<FS,L,D>,
			CATEGORY extends GeoJsfCategory<L,D,LAYER>,
			SERVICE extends GeoJsfService<L,D,LAYER>,
			LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,VP,DS,SLD>,
			MAP extends GeoJsfMap<L,D,CATEGORY,VIEW,VP>,
			SCALE extends GeoJsfScale<L,D>, 
			VIEW extends GeoJsfView<LAYER,MAP,VIEW>,
			VP extends GeoJsfViewPort,
			DS extends GeoJsfDataSource<L,D,LAYER>,
			SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDSTYLE extends UtilsStatus<SLDSTYLE,L,D>,
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

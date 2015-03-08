package org.geojsf.factory.xml.geojsf;

import java.io.Serializable;

import net.sf.ahtutils.model.interfaces.status.UtilsDescription;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.status.UtilsStatus;

import org.geojsf.interfaces.model.GeoJsfCategory;
import org.geojsf.interfaces.model.GeoJsfLayer;
import org.geojsf.interfaces.model.GeoJsfMap;
import org.geojsf.interfaces.model.GeoJsfService;
import org.geojsf.interfaces.model.GeoJsfView;
import org.geojsf.interfaces.model.GeoJsfViewPort;
import org.geojsf.interfaces.model.sld.GeoJsfSldTemplate;
import org.geojsf.xml.geojsf.Query;
import org.geojsf.xml.geojsf.ViewPort;
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
	
	public <L extends UtilsLang,D extends UtilsDescription,CATEGORY extends GeoJsfCategory<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SERVICE extends GeoJsfService<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, LAYER extends GeoJsfLayer<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,MAP extends GeoJsfMap<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VIEW extends GeoJsfView<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>, VP extends GeoJsfViewPort<L,D,CATEGORY,SERVICE,LAYER,MAP,VIEW,VP,SLDTYPE,SLDTEMPLATE>,SLDTYPE extends UtilsStatus<SLDTYPE,L,D>,SLDTEMPLATE extends GeoJsfSldTemplate<L,D,SLDTYPE,SLDTEMPLATE>>
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

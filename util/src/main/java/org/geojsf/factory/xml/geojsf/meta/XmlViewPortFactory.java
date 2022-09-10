package org.geojsf.factory.xml.geojsf.meta;

import java.io.Serializable;

import org.geojsf.factory.xml.geojsf.XmlScaleFactory;
import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.model.xml.geojsf.Query;
import org.geojsf.model.xml.geojsf.ViewPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlViewPortFactory <VP extends GeoJsfViewPort> implements Serializable
{
	final static Logger logger = LoggerFactory.getLogger(XmlViewPortFactory.class);
	
	public static final long serialVersionUID=1;

	private ViewPort q;
	
	private XmlScaleFactory<VP> xfScale;
	
	public XmlViewPortFactory(Query query){this(query.getViewPort());}
	public XmlViewPortFactory(ViewPort q)
	{
		this.q=q;
		
		if(q.isSetScale()) {xfScale = new XmlScaleFactory<>();}
	}
	
	public ViewPort build (VP ejb)
	{
		ViewPort xml = new ViewPort();
		
		if(q.isSetId()){xml.setId(ejb.getId());}
		
		if(q.isSetLat()){xml.setLat(ejb.getLat());}
		if(q.isSetLon()){xml.setLon(ejb.getLon());}
		
		if(q.isSetLeft()){xml.setLeft(ejb.getMarginLeft());}
		if(q.isSetRight()){xml.setRight(ejb.getMarginRight());}
		if(q.isSetTop()){xml.setTop(ejb.getMarginTop());}
		if(q.isSetBottom()){xml.setBottom(ejb.getMarginBottom());}
		
		if(q.isSetScale()) {xml.setScale(xfScale.build(ejb));}
		
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

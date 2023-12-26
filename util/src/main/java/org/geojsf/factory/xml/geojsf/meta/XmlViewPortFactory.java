package org.geojsf.factory.xml.geojsf.meta;

import java.io.Serializable;
import java.util.Objects;

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
		
		if(Objects.nonNull(q.getScale())) {xfScale = new XmlScaleFactory<>();}
	}
	
	public ViewPort build (VP ejb)
	{
		ViewPort xml = new ViewPort();
		
		if(Objects.nonNull(q.getId())) {xml.setId(ejb.getId());}
		
		if(Objects.nonNull(q.getLat())) {xml.setLat(ejb.getLat());}
		if(Objects.nonNull(q.getLon())) {xml.setLon(ejb.getLon());}
		
		if(Objects.nonNull(q.getLeft())) {xml.setLeft(ejb.getMarginLeft());}
		if(Objects.nonNull(q.getRight())) {xml.setRight(ejb.getMarginRight());}
		if(Objects.nonNull(q.getTop())) {xml.setTop(ejb.getMarginTop());}
		if(Objects.nonNull(q.getBottom())) {xml.setBottom(ejb.getMarginBottom());}
		
		if(Objects.nonNull(q.getScale())) {xml.setScale(xfScale.build(ejb));}
		
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

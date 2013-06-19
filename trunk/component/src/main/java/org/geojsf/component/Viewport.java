package org.geojsf.component;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.geojsf.util.GeoJsfJsLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="org.geojsf.component.Viewport")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Viewport extends UINamingContainer implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Viewport.class);
	
	private Double lat = 0.0;
	private Double lon = 0.0;
	private Integer zoom = 5;
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		if(event instanceof PostAddToViewEvent)
		{
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"viewport.js");
		}
		super.processEvent(event);
	}
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		ResponseWriter writer = ctx.getResponseWriter();
		writer.writeText("GeoJsfViewport.center(" +lat +"," +lon +"," +zoom +");", null);
	}

	public Double getLat() {return lat;}
	public void setLat(Double lat) {this.lat = lat;}
	
	public Double getLon() {return lon;}
	public void setLon(Double lon) {this.lon = lon;}

	public Integer getZoom() {return zoom;}
	public void setZoom(Integer zoom) {this.zoom = zoom;}
}
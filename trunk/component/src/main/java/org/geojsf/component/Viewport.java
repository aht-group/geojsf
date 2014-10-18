package org.geojsf.component;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;
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
public class Viewport extends UIPanel implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Viewport.class);
	
	private static enum Attribute {lat,lon,zoom}
	
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
		logger.info("Rendering Viewport.");
		Map<String,Object> map = this.getAttributes();
		double lat = new Double(map.get(Attribute.lat.toString()).toString());
		double lon = new Double(map.get(Attribute.lon.toString()).toString());
		int zoom = new Integer(map.get(Attribute.zoom.toString()).toString());
		
		ResponseWriter writer = ctx.getResponseWriter();
		try {
		writer.startElement("script", this);
		writer.writeText("GeoJsfViewport.center(" +lon +"," +lat +"," +zoom +");", null); 
		writer.endElement("script");
		}
		catch (Exception e)
		{
			logger.error("Could not render ViewPort! Reason: " +e.toString());
		}
	}
}
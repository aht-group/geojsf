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

import net.sf.ahtutils.jsf.util.ComponentAttribute;

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
		double lat             = ComponentAttribute.getDouble("lat", 0.0 ,ctx,  this);
		double lon             = ComponentAttribute.getDouble("lon", 0.0 ,ctx,  this);
		int zoom               = ComponentAttribute.getInteger("zoom", 0 ,ctx,  this);
		
		ResponseWriter writer = ctx.getResponseWriter();
		writer.startElement("script", this);
		writer.writeText("GeoJsfViewport.center(" +lon +"," +lat +"," +zoom +");", null); 
		writer.endElement("script");
	}
}
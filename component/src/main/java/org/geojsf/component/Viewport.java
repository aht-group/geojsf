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

import org.geojsf.interfaces.model.meta.GeoJsfViewPort;
import org.geojsf.util.GeoJsfJsLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="org.geojsf.component.Viewport")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Viewport extends UIPanel implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Viewport.class);
	
	private static enum Attribute {lat,lon,resolution,zoom, value}
	
	private GeoJsfViewPort value;
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		if(event instanceof PostAddToViewEvent)
		{
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"viewport.js");
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"GeoJsfUtil.js");
		}
		super.processEvent(event);
	}
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		logger.debug("Rendering Viewport.");
		
		Double  lat             = null;
		Double  lon             = null;
		Integer zoom            = null;
		Integer resolution      = null;
		
		ResponseWriter writer = ctx.getResponseWriter();
		writer.startElement("script", this);
		
		Map<String,Object> map = this.getAttributes();
		this.value = (GeoJsfViewPort) map.get(Attribute.value.toString());
		
		if (this.value != null)
		{
			lat             = this.value.getLat();
			lon             = this.value.getLon();
                        
			// If there is no scale given, set it to 100 km
			if (this.value.getScale() == 0) 
			{
				this.value.setScale(100000);
			}
			else 
			{
				this.value.setScale(this.value.getScale());
			}
			
			writer.writeText("GeoJsfViewport.center(" +lon +"," +lat +"," +this.value.getScale() +");" +System.getProperty("line.separator"), null);		
		}
		else
		{
			lat             = ComponentAttribute.getDouble(Attribute.lat.toString(), 0.0 ,ctx,  this);
			lon             = ComponentAttribute.getDouble(Attribute.lon.toString(), 0.0 ,ctx,  this);
			zoom            = ComponentAttribute.getInteger(Attribute.zoom.toString(), 0 ,ctx,  this);
			resolution      = ComponentAttribute.getInteger(Attribute.resolution.toString(), 0 ,ctx,  this);
			writer.writeText("GeoJsfViewport.center(" +lon +"," +lat +"," +resolution +"," +zoom +");" +System.getProperty("line.separator"), null);
		}
		 
		writer.endElement("script");
	}
}	
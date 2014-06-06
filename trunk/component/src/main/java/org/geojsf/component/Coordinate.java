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

@FacesComponent(value="org.geojsf.component.Coordinate")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Coordinate extends UIPanel implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Coordinate.class);
	
	private static enum Attribute {orientation, precision}
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		if(event instanceof PostAddToViewEvent)
		{
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"coordinate.js");
		}
		super.processEvent(event);
	}
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		Map<String,Object> map = this.getAttributes();
		String orientation = map.get(Attribute.orientation.toString()).toString();
		
		// Precision defaults to 5
		String precision   = null;
		if (null!=map.get(Attribute.precision.toString()))
		{
		    precision   = map.get(Attribute.precision.toString()).toString();
		}
		else
		{
			precision   = "5";
		}
		
		ResponseWriter writer = ctx.getResponseWriter();
		writer.startElement("script", this);
		writer.writeText("GeoJsfCoordinate.addCoordinatesControl('"+orientation+"'," +precision +");", null); 
		writer.endElement("script");
	}
}
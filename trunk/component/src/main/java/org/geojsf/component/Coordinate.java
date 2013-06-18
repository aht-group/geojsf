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

@FacesComponent(value="org.geojsf.component.Coordinate")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Coordinate extends UINamingContainer implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Coordinate.class);
	
	private String orientation = null;
	
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
		logger.info("entering encodebegin");
		ResponseWriter writer = ctx.getResponseWriter();
		
		writer.startElement("script", this);
			writer.writeText("GeoJsfCoordinate.addCoordinatesControl('" +getOrientation() +"');", null);
 		writer.endElement("script");   
	}

	public String getOrientation() {return orientation;}
	public void setOrientation(String orientation) {this.orientation = orientation;}
}
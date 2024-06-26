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

@FacesComponent(value="org.geojsf.component.Graticule")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Graticule extends UINamingContainer implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Graticule.class);
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		if(event instanceof PostAddToViewEvent)
		{
			GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"control.js");
		}
		super.processEvent(event);
	}
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		logger.debug("Adding Graticule.");
//		Map<String,Object> map = this.getAttributes();
		
		ResponseWriter writer = ctx.getResponseWriter();
		writer.startElement("script", this);
		writer.writeText("GeoJsfControl.addGraticule();", null);
		writer.endElement("script");
	}
}
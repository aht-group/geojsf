package org.geojsf.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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

@FacesComponent(value="org.geojsf.component.ToolTip")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class ToolTip extends UINamingContainer implements ClientBehaviorHolder
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
		logger.info("Adding ToolTip control.");
	    String jsCall = "";
	    logger.info(jsCall);


		ResponseWriter writer = ctx.getResponseWriter();
		if (this.getParent() instanceof Map)
		{
			JsfRenderUtil renderer = new JsfRenderUtil(writer, this.getParent());
			renderer.renderLinebreaks(2);

			//First, render the JavaScript code to initialize the popup
			writer.startElement("script", this);
			renderer.renderToolTipInitialization(this.getFacesContext());
			writer.endElement("script");
		}
		else
		{
			logger.info("Adding ToolTip control. --> not possible---> because parent element is not a map");
		}
	}

	@Override
	public Collection<String> getEventNames()
	{
	    ArrayList<String> events = new ArrayList<String>();
		events.add("featureSelected");
	    return events;
	}

	@Override
	public String getDefaultEventName() {return "";}



}
package org.geojsf.component;

import java.io.IOException;
import java.util.Map;

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

@FacesComponent(value="org.geojsf.component.ScaleBar")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class ScaleBar extends UINamingContainer implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(ScaleBar.class);
	
	private static enum Attribute {orientation, system, major, sub}
	
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
		logger.debug("Adding scale bar.");
		Map<String,Object> map = this.getAttributes();
		
		String orientation = "north";
		if (null!=map.get(Attribute.orientation.toString())){orientation = map.get(Attribute.orientation.toString()).toString();}
		
		String system = "metric";
		if (null!=map.get(Attribute.system.toString())){system = map.get(Attribute.system.toString()).toString();}
		
		String major  = "2";
		if (null!=map.get(Attribute.major.toString())){major = map.get(Attribute.major.toString()).toString();}
		
		String sub    = "2";
		if (null!=map.get(Attribute.sub.toString())){sub = map.get(Attribute.sub.toString()).toString();}
		
		ResponseWriter writer = ctx.getResponseWriter();
		writer.startElement("script", this);
		writer.writeText("GeoJsfControl.addScaleBar('"+orientation+"','" +system +"'," +major +"," +sub+");", null);
		writer.endElement("script");
	}
}
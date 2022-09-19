package org.geojsf.component;

import java.io.IOException;
import java.util.Map;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;

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

@ResourceDependencies({
@ResourceDependency(library = "js.geojsf", name = "markers.js", target = "head")})
@FacesComponent(value="org.geojsf.component.MarkerLayer")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class MarkerLayer extends UINamingContainer implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(MarkerLayer.class);
	
	private static enum Attribute {data, iconUrl, name}
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
	    if(event instanceof PostAddToViewEvent)
	    {
		GeoJsfJsLoader.pushJsToHead(this.getFacesContext(),"markers.js");
	    }
	    super.processEvent(event);
	}
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
	    logger.info("Adding a marker layer.");
	    Map<String,Object> map = this.getAttributes();

	    String data = "{}";
	    if (null!=map.get(Attribute.data.toString())){data = map.get(Attribute.data.toString()).toString();}

	    String iconUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/88/Map_marker.svg/780px-Map_marker.svg.png";
	    if (null!=map.get(Attribute.iconUrl.toString())){iconUrl = map.get(Attribute.iconUrl.toString()).toString();}

	    String name  = "markerLayer";
	    if (null!=map.get(Attribute.name.toString())){name = map.get(Attribute.name.toString()).toString();}

	    ResponseWriter writer = ctx.getResponseWriter();
	    writer.startElement("script", this);
	    writer.writeText("GeoJsfMarkerLayer.addMarkerLayer('"+data+"','" +iconUrl +"','" +name +"');", null);
	    writer.endElement("script");
	}
}
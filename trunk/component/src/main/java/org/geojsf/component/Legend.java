package org.geojsf.component;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.UIPanel;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.geojsf.interfaces.model.GeoJsfView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="org.geojsf.component.Legend")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Legend extends UIPanel implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Legend.class);
	
	private static enum Attribute {view}
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		super.processEvent(event);
	}
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		Map<String,Object> map = this.getAttributes();
		
		// Read the information from the attribute
		GeoJsfView view        = (GeoJsfView) map.get(Attribute.view.toString());
		String serviceUrl      = view.getLayer().getService().getUrl();
		String layerName       = view.getLayer().getCode();
		String imageUrl        = serviceUrl +"?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&TRANSPARENT=true&STRICT=false&layer=" +layerName +"";
		
		// Show the image by rendering an IMG tag directly
		ResponseWriter writer = ctx.getResponseWriter();
		writer.startElement("img", this);
		writer.writeAttribute("src", imageUrl, null);
		writer.endElement("img");
	}
}
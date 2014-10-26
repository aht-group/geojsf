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
		
		// Attach a component to display an image showing the Legend offered by the Service
		UIGraphic legendImage  = new UIGraphic();
		legendImage.setUrl(serviceUrl +"?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&TRANSPARENT=true&WIDTH=20&HEIGHT=20&STRICT=false&layer=" +layerName +"");
		this.getChildren().add(legendImage);
	}
}
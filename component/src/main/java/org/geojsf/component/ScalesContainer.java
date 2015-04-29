package org.geojsf.component;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.geojsf.factory.geojsf.GeoJsfScalesFactory;
import org.geojsf.xml.geojsf.Scale;
import org.geojsf.xml.geojsf.Scales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="org.geojsf.component.ScalesContainer")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class ScalesContainer extends UINamingContainer
{	
	final static Logger logger = LoggerFactory.getLogger(ScalesContainer.class);
	
	private static enum Attribute {value}
	
	private Scales value;

	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		if(event instanceof PostAddToViewEvent)
		{
			Map<String,Object> map = this.getAttributes();
			this.value   = (Scales) map.get(Attribute.value.toString());
		}
		super.processEvent(event);
	}
	
	public Scales getValue() {
		return this.value;
	}
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		logger.info("Adding scales in " +this.value.getUnit());
		for (Scale scale : this.value.getScale())
		{
			logger.info("" +scale.getValue());
		}
	}
}

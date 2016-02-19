package org.geojsf.component;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.geojsf.model.xml.geojsf.Scale;
import org.geojsf.model.xml.geojsf.Scales;
import org.geojsf.util.component.GeoJsfScalesUtil;
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
		GeoJsfScalesUtil util = new GeoJsfScalesUtil(this.value);
		StringBuffer sb = new StringBuffer();
		sb.append("Adding scales in " +this.value.getUnit());
		for (Scale scale : this.value.getScale()){sb.append(" "+scale.getValue());}
		logger.info(sb.toString());
		
		ResponseWriter writer = ctx.getResponseWriter();
		writer.startElement("script", this);
		writer.write("var array = [" +util.getScaleList() +"];" +System.getProperty("line.separator"));
		writer.write("GeoJSF.setScaleValues(array);"  +System.getProperty("line.separator"));
		writer.endElement("script");
	}
}

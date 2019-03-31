package org.geojsf.component;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="org.geojsf.component.Sld")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class Sld extends UIComponentBase
{
	final static Logger logger = LoggerFactory.getLogger(Sld.class);
	
	private static enum Attribute {value}
	
	private String value; public void setValue(String value) {this.value = value;} public String getValue() {return value;}

	@Override public String getFamily() {return null;}
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		if(event instanceof PostAddToViewEvent)
		{
		}
		super.processEvent(event);
	}
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		Map<String,Object> map = this.getAttributes();
		this.value   = (String) map.get(Attribute.value.toString());
		String command = "<script>GeoJSF.updateSld('" +value +"');</script>";
		ctx.getResponseWriter().write(command);
		logger.info("Styled Layer Description (SLD) updated in Map. JavaScript Command: " +command);
	}


	
}
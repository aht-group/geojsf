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

@FacesComponent(value="org.geojsf.component.SldParameter")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class SldParameter extends UIComponentBase
{
	final static Logger logger = LoggerFactory.getLogger(SldParameter.class);
	
	private static enum Attribute {value}
	
	private String value;
	
	@Override
	public void processEvent(ComponentSystemEvent event) throws AbortProcessingException
	{
		if(event instanceof PostAddToViewEvent)
		{
			
		}
		super.processEvent(event);
	}
	
	@Override
	public void encodeAll(FacesContext ctx) throws IOException
	{
		Map<String,Object> map = this.getAttributes();
		this.value   = (String) map.get(Attribute.value.toString());
		String command = "<script>GeoJSF.updateSldParams('" +value +"');</script>";
		ctx.getResponseWriter().write(command);
		logger.info("Styled Layer Description (SLD) Parameter updated in Map. JavaScript Command: " +command);
	}

	public void setValue(String value) {this.value = value;}
	public String getValue() {return value;}

	@Override
	public String getFamily() {
		return null;
	}
}
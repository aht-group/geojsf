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

@FacesComponent(value="org.geojsf.component.SqlViewParameter")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class SqlViewParameter extends UIComponentBase
{
	final static Logger logger = LoggerFactory.getLogger(SqlViewParameter.class);
	
	private static enum Attribute {key, value}
	
	private String key;
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
		this.key     = (String) map.get(Attribute.key.toString());
		this.value   = (String) map.get(Attribute.value.toString());
		String command = "<script>GeoJSF.updateSqlViewParams('" +key +": " +value +";');</script>";
		ctx.getResponseWriter().write(command);
		logger.info("SQL View Parameter updated in Map. JavaScript Command: " +command);
	}

	public void setValue(String value) {this.value = value;}
	public String getValue() {return value;}

	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}

	@Override
	public String getFamily() {
		return null;
	}
}
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

@FacesComponent(value="org.geojsf.component.DtSelectCallback")
@ListenerFor(systemEventClass=PostAddToViewEvent.class)
public class DtSelectCallback extends UIComponentBase
{
	final static Logger logger = LoggerFactory.getLogger(DtSelectCallback.class);

	private static enum Attribute {x, y}

	private String x; public String getX() {return x;} public void setX(String x) {this.x = x;}
	private String y; public String getY() {return y;} public void setY(String y) {this.y = y;}

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
		this.x   = (String) map.get(Attribute.x.toString());
		this.y   = (String) map.get(Attribute.y.toString());
		String command = "<script>dtSelectCallback.createMarker(" + x +", " + y +");</script>";
		ctx.getResponseWriter().write(command);
		logger.info("DataTableSelectCallback createMarker initalized in Map. JavaScript Command: " +command);
	}
}
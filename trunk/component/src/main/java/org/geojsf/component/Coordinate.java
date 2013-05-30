package org.geojsf.component;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="org.geojsf.component.Coordinate")
public class Coordinate extends UINamingContainer implements ClientBehaviorHolder
{
	final static Logger logger = LoggerFactory.getLogger(Coordinate.class);
	
	private String orientation = null;
	
	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		logger.info("entering encodebegin");
		ResponseWriter writer = ctx.getResponseWriter();
		
		writer.startElement("script", this);
			writer.writeText("GeoJSF.addCoordinatesControl('" +getOrientation() +"');", null);
 		writer.endElement("script");   
	}

	public String getOrientation() {return orientation;}
	public void setOrientation(String orientation) {this.orientation = orientation;}
}
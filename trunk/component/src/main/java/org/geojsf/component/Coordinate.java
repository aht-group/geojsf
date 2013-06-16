package org.geojsf.component;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ResourceDependencies({
	@ResourceDependency(library="js.geojsf", name="coordinate.js", target="head")
})
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
			writer.writeText("GeoJsfCoordinate.addCoordinatesControl('" +getOrientation() +"');", null);
 		writer.endElement("script");   
	}

	public String getOrientation() {return orientation;}
	public void setOrientation(String orientation) {this.orientation = orientation;}
}
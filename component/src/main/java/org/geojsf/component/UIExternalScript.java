package org.geojsf.component;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent(value="org.geojsf.component.UIExternalScript")
public class UIExternalScript  extends UINamingContainer
{	
	final static Logger logger = LoggerFactory.getLogger(UIExternalScript.class);
	
	//Define attributes of the component
	private String src          = null;

	@Override
	public void encodeBegin(FacesContext ctx) throws IOException
	{
		ResponseWriter writer = ctx.getResponseWriter();
		logger.info("Adding "+src +" as external JS resource to header.");

		writer.startElement("script", this);
		writer.writeAttribute("src", this.getSrc(), null);
		writer.endElement("script");
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
	
	
}

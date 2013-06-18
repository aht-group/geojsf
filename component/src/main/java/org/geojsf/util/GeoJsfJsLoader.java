package org.geojsf.util;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfJsLoader
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfJsLoader.class);
	
	private static String contextParameter = "org.geojsf.DEVELOPMENT";
	private static Boolean useJsMin;
	
	public static void pushJsToHead(FacesContext context, String name) throws AbortProcessingException
	{		
		if(useJsMin==null){determineContextVariable(context);}
		UIOutput js = new UIOutput();
		js.setRendererType("javax.faces.resource.Script");
		js.getAttributes().put("library", "js.geojsf");
		if(useJsMin){js.getAttributes().put("name", "GeoJsf.min.js");}
		else{js.getAttributes().put("name", name);}
		
		context.getViewRoot().addComponentResource(context, js, "head");
	}
	
	private static synchronized void determineContextVariable(FacesContext context)
	{
		boolean geojsfDevelopment = false;
		String s = context.getExternalContext().getInitParameter(contextParameter);
		
		if(s!=null)
		{
			geojsfDevelopment = new Boolean(s);
		}
		
		logger.info(contextParameter+": "+geojsfDevelopment);
		
		useJsMin = !geojsfDevelopment;
	}
}

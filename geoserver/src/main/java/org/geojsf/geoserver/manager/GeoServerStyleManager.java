package org.geojsf.geoserver.manager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.geojsf.interfaces.rest.geoserver.GeoServerStyleRest;
import org.geojsf.model.xml.geoserver.Style;
import org.geojsf.model.xml.geoserver.Styles;
import org.geojsf.model.xml.geoserver.Workspace;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerStyleManager 
{		
	final static Logger logger = LoggerFactory.getLogger(GeoServerStyleManager.class);
	
	private GeoServerStyleRest rest;
	
	public GeoServerStyleManager(GeoServerStyleRest rest)
	{
		this.rest=rest;
	}
	
	public Document getStyle(String workspace, String style) throws IOException
	{
		return rest.getStyle(workspace, style);
	}
	
	public boolean isAvailable(Workspace ws, String style) throws IOException
	{
		Styles styles = rest.getStyles(ws.getName());
		Set<String> sStyle = new HashSet<String>();
		for(Style s : styles.getStyle()){sStyle.add(s.getName());}
		return sStyle.contains(style);
	}
	
	public void createStyle(String workspace, Document style) throws IOException
	{
		rest.createStyle(workspace, style);
	}
}
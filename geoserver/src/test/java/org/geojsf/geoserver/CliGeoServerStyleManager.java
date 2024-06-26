package org.geojsf.geoserver;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.geojsf.api.rest.geoserver.GeoServerRest;
import org.geojsf.geoserver.manager.GeoServerStyleManager;
import org.geojsf.model.xml.geoserver.Styles;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class CliGeoServerStyleManager
{
	final static Logger logger = LoggerFactory.getLogger(CliGeoServerStyleManager.class);
	
	private GeoServerStyleManager styleManager;
	
	public CliGeoServerStyleManager(Configuration config, GeoServerRest rest)
	{
		styleManager = init(rest,new File("target"),"target");
	}

//jeesl.highlight:init
	public GeoServerStyleManager init(GeoServerRest rest, File styleDir, String stylePath)
	{
		GeoServerStyleManager styleManager = new GeoServerStyleManager(rest);
		return styleManager;
	}
//jeesl.highlight:init
	
//jeesl.highlight:basic
	public void basic(GeoServerRest rest, Document docStyle) throws IOException
	{
		Styles styles = rest.styles();
		Styles stylesInWorkspace = rest.styles("ws");
		Document sld = rest.style("grass");
		Document sldInWorkspace = rest.style("ws","grass");
		rest.deleteStyle("grass");
		rest.deleteStyle("ws", "grass");
//		rest.updateStyle(docStyle);
//		rest.updateStyle("ws",docStyle);
	}
//jeesl.highlight:basic
	
	
}
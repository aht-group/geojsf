package org.geojsf.geoserver;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.geojsf.controller.interfaces.rest.GeoServerRest;
import org.geojsf.xml.geoserver.Style;
import org.geojsf.xml.geoserver.Styles;
import org.jdom.Document;
import org.jdom.output.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerStyleManager 
{		
	final static Logger logger = LoggerFactory.getLogger(GeoServerStyleManager.class);
	
	private GeoServerRest rest;
	
	public GeoServerStyleManager(GeoServerRest rest)
	{
		this.rest=rest;
	}
	
	public void exportStyles(File directory) throws IOException
	{
		Styles styles = rest.styles();
		exportStyles(styles.getStyle(),directory);
	}
	
	private void exportStyles(List<Style> list, File directory) throws IOException
	{
		for(Style style : list)
		{
			exportStyle(style.getName(), directory);
		}
	}
	
	public void exportStyle(String name, File directory) throws IOException
	{
		if(!directory.exists() || !directory.isDirectory())
		{
			throw new IOException("dir");
		}
		Document doc = rest.style(name);
		JDomUtil.save(doc, new File(directory,name+".sld"), Format.getPrettyFormat());
	}
}

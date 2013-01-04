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
	private File styleDir;
	
	public File getStyleDir() {
		return styleDir;
	}
	public void setStyleDir(File styleDir) {
		this.styleDir = styleDir;
	}
	public GeoServerStyleManager(GeoServerRest rest)
	{
		this(rest,null);
	}
	public GeoServerStyleManager(GeoServerRest rest, File styleDir)
	{
		this.rest=rest;
		this.styleDir=styleDir;
	}
	
	private void checkDir() throws IOException
	{
		if(styleDir==null || !styleDir.exists() || !styleDir.isDirectory())
		{
			throw new IOException("Directory does not exist: "+styleDir.getCanonicalPath());
		}
	}
	
	public void exportStyles() throws IOException
	{
		checkDir();
		Styles styles = rest.styles();
		exportStyles(null,styles.getStyle());
	}
	
	public void exportStyles(String workspace) throws IOException
	{
		checkDir();
		Styles styles = rest.styles(workspace);
		exportStyles(workspace,styles.getStyle());
	}
	
	private void exportStyles(String workspace, List<Style> list) throws IOException
	{
		for(Style style : list)
		{
			exportStyle(workspace, style.getName());
		}
	}
	
	public void exportStyle(String workspace, String name) throws IOException
	{
		Document doc;
		if(workspace==null){doc = rest.style(name);}
		else {doc = rest.style(workspace, name);}
		JDomUtil.save(doc, new File(styleDir,name+".sld"), Format.getPrettyFormat());
	}
}

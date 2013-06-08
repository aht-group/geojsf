package org.geojsf.geoserver.manager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.lang.SystemUtils;
import org.geojsf.controller.interfaces.rest.GeoServerRest;
import org.geojsf.xml.geoserver.Style;
import org.geojsf.xml.geoserver.Styles;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerStyleManager 
{		
	final static Logger logger = LoggerFactory.getLogger(GeoServerStyleManager.class);
	
	private GeoServerRest rest;
	private File styleDir;
	private String stylePath;
	
	public GeoServerStyleManager(GeoServerRest rest, File styleDir, String stylePath)
	{
		this.rest=rest;
		this.styleDir=styleDir;
		this.stylePath=stylePath;
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
		JaxbUtil.save(new File(styleDir,"global.xml"), styles, true);
		exportStyles(null,styles.getStyle());
	}
	
	public void exportStyles(String workspace) throws IOException
	{
		checkDir();
		Styles styles = rest.styles(workspace);
		JaxbUtil.save(new File(styleDir,workspace+".xml"), styles, true);
		exportStyles(workspace,styles.getStyle());
	}
	
	private void exportStyles(String workspace, List<Style> list) throws IOException
	{
		File dir;;
		if(workspace==null){dir = new File(styleDir,"global");}
		else {dir = new File(styleDir,workspace);}
		
		for(File f : dir.listFiles())
		{
			if(f.exists() && f.isFile())
			{
				f.delete();
			}
		}
		
		for(Style style : list)
		{
			exportStyle(workspace, style.getName(),dir);
		}
	}
	
	public void exportStyle(String workspace, String name, File dir) throws IOException
	{
		Document doc;
		if(workspace==null){doc = rest.style(name);workspace="global";}
		else {doc = rest.style(workspace, name);}
		
		JDomUtil.save(doc, new File(dir,name+".sld"), Format.getPrettyFormat());
	}
	
	public void updateStyles() throws IOException
	{
		Styles styles = JaxbUtil.loadJAXB(stylePath+"/global.xml", Styles.class);
		updateStyles(null,styles,stylePath+SystemUtils.FILE_SEPARATOR+"global");
	}
	public void updateStyles(String workspace) throws IOException
	{
		Styles styles = JaxbUtil.loadJAXB(stylePath+"/"+workspace+".xml", Styles.class);
		updateStyles(workspace,styles,stylePath+"/"+workspace);
	}
	
	
	private void updateStyles(String workspace, Styles styles, String path) throws IOException
	{
		logger.info("Updating from "+path);
		for(Style style : styles.getStyle())
		{
			Document sld = JDomUtil.load(path+"/"+style.getName()+".sld");
			if(workspace==null){}
			else{rest.updateStyle(workspace, sld);}
		}
	}
}
package net.sf.geojsf.util;

import java.io.FileNotFoundException;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.util.wfs.OpenLayers;
import org.geojsf.xml.openlayers.Repository;
import org.geojsf.xml.openlayers.View;
import org.geojsf.xml.openlayers.Views;
import org.geojsf.xml.xpath.OpenLayersXpath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstOpenLayers
{
	final static Logger logger = LoggerFactory.getLogger(TstOpenLayers.class);
	
	private Repository repo;

	public TstOpenLayers()
	{

		try {repo = (Repository)JaxbUtil.loadJAXB("src/test/resources/xml/openlayers/repository.xml", Repository.class);}
		catch (FileNotFoundException e) {e.printStackTrace();}
	}
	
	public void debug()
	{
		JaxbUtil.debug(repo);
	}
	
	public void view(String viewCode) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		Views views = repo.getViews();
		JaxbUtil.debug(views);
		View view = OpenLayersXpath.getView(repo, viewCode);
		JaxbUtil.debug(view);
	}
	
	public void testOpenLayers(String viewCode)
	{
		OpenLayers ol = new OpenLayers(repo, viewCode);
		logger.debug("Params: "+ol.getWmsLayer().getValue().getParams().toString());
		logger.debug("URL: "+ol.getWmsLayer().getValue().getUrl());
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();
			
		String layerCode = "v1";
		
		TstOpenLayers test = new TstOpenLayers();
//		test.debug();
		test.view(layerCode);
		test.testOpenLayers(layerCode);
	}
}
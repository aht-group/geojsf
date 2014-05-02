package org.geojsf.doc;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.doc.ofx.OfxLayerTableFactory;
import org.geojsf.doc.ofx.OfxServiceListFactory;
import org.geojsf.xml.geojsf.Layers;
import org.geojsf.xml.geojsf.Repository;
import org.geojsf.xml.geojsf.Service;
import org.openfuxml.exception.OfxAuthoringException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfLatexDocumentationFactory
{	
	final static Logger logger = LoggerFactory.getLogger(GeoJsfLatexDocumentationFactory.class);
	
	private final static String dirDescriptions = "description/geojsf";
	private final static String dirLayer = "tab/geojsf/layer";
	
	private Configuration config;
	
	private String baseLatexDir;
	private Translations translations;
	private String[] langs;
	
	private Repository repository;
	private Layers layers;
	
	public GeoJsfLatexDocumentationFactory(Configuration config, Translations translations,String[] langs)
	{
		this.config=config;
		this.translations=translations;
		this.langs=langs;
		baseLatexDir=config.getString(GeoJsfDocumentation.keyBaseDocDir);
		logger.info("Using basedir "+baseLatexDir+" for GeoJsf configuration");
	}
	
	public void loadRepository(String fileName) throws FileNotFoundException
	{
		repository = JaxbUtil.loadJAXB(fileName,Repository.class);
		JaxbUtil.trace(repository);
	}
	
	public void loadLayers(String fileName) throws FileNotFoundException
	{
		layers = JaxbUtil.loadJAXB(fileName,Layers.class);
		JaxbUtil.info(layers);
	}
	
	public void saveServiceDescription() throws UtilsConfigurationException
	{
		logger.info("Creating Layer.Descriptions");
		for(String lang : langs)
		{
			File f = new File(baseLatexDir,lang+"/"+dirDescriptions+"/services.tex");
			
			try
			{
				OfxServiceListFactory latexFactory = new OfxServiceListFactory(config,lang,translations);
				String content = latexFactory.toLatex(repository.getService());
				StringIO.writeTxt(f, content);
			}
			catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
		}
	}
	
	public void saveLayerTables(String idPrefix) throws UtilsConfigurationException
	{
		logger.info("Creating Layer.Descriptions");
		
		String[] headerKeys = new String[2];
		headerKeys[0] = "geoJsfTableLayerName";
		headerKeys[1] = "geoJsfTableLayerDescription";
		
		for(String lang : langs)
		{
			for(Service service : repository.getService())
			{
				File f = new File(baseLatexDir,lang+"/"+dirLayer+"/"+service.getCode()+".tex");
				try
				{
					OfxLayerTableFactory latexFactory = new OfxLayerTableFactory(config,lang);
					String content = latexFactory.toLatex(idPrefix+".layer."+service.getCode(),service,layers,headerKeys);
					StringIO.writeTxt(f, content);
				}
				catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
			}
		}
	}
}
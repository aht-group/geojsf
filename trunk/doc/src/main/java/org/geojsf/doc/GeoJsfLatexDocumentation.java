package org.geojsf.doc;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.ahtutils.doc.latex.AbstractLatexDocumentationBuilder;
import net.sf.ahtutils.doc.latex.UtilsLatexDocumentationBuilder.InstallationArchitecture;
import net.sf.ahtutils.doc.latex.UtilsLatexDocumentationBuilder.InstallationCode;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.doc.ofx.OfxLayerTableFactory;
import org.geojsf.doc.ofx.OfxMapSectionFactory;
import org.geojsf.doc.ofx.OfxServiceListFactory;
import org.geojsf.xml.geojsf.Layer;
import org.geojsf.xml.geojsf.Layers;
import org.geojsf.xml.geojsf.Map;
import org.geojsf.xml.geojsf.Maps;
import org.geojsf.xml.geojsf.Repository;
import org.geojsf.xml.geojsf.Service;
import org.geojsf.xml.geojsf.View;
import org.geojsf.xml.xpath.GeoJsfXpath;
import org.openfuxml.exception.OfxAuthoringException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfLatexDocumentation extends AbstractLatexDocumentationBuilder
{	
	final static Logger logger = LoggerFactory.getLogger(GeoJsfLatexDocumentation.class);
	
	private final static String dirDescriptions = "description/geojsf";
	private final static String dirLayer = "tab/geojsf/layer";
	private final static String dirMap = "section/geojsf/map";
	
	public static enum InstallationCode {instGeoserver}

	private Translations translations;
	
	private Repository repository;
	private Layers layers;
	private Maps maps;
	
	private String[] headerKeysLayer,headerKeysMapView;
	
	public GeoJsfLatexDocumentation(Configuration config, Translations translations,String[] langs)
	{
		super(config,langs,null);
		this.translations=translations;
		
		headerKeysLayer = new String[3];
		headerKeysLayer[0] = "geoJsfTableLayerCode";
		headerKeysLayer[1] = "geoJsfTableLayerName";
		headerKeysLayer[2] = "geoJsfTableLayerDescription";
		
		headerKeysMapView = new String[2];
		headerKeysMapView[0] = "geoJsfTableMapViewNr";
		headerKeysMapView[1] = "geoJsfTableMapViewLayerName";
//		headerKeysMapView[2] = "geoJsfTableLayerDescription";
	}
	
	@Override protected void applyBaseLatexDir()
	{
		baseLatexDir=config.getString(GeoJsfDocumentation.keyBaseDocDir);
	}
	
	@Override protected void applyConfigCodes()
	{
		logger.debug("Setting fixed config-codes");
		
		//Installation
		addConfig(InstallationCode.instGeoserver.toString(),"ofx.geojsf/installation/geoserver.xml","admin/installation/geoserver");
	}
	
	public void loadRepository(String fileName) throws FileNotFoundException
	{
		repository = JaxbUtil.loadJAXB(fileName,Repository.class);
		JaxbUtil.trace(repository);
	}
	
	public void loadLayers(String fileName) throws FileNotFoundException
	{
		layers = JaxbUtil.loadJAXB(fileName,Layers.class);
		JaxbUtil.trace(layers);
	}
	
	public void loadMaps(String fileName) throws FileNotFoundException
	{
		maps = JaxbUtil.loadJAXB(fileName,Maps.class);
		JaxbUtil.trace(maps);
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
		for(String lang : langs)
		{
			for(Service service : repository.getService())
			{
				File f = new File(baseLatexDir,lang+"/"+dirLayer+"/"+service.getCode()+".tex");
				try
				{
					OfxLayerTableFactory latexFactory = new OfxLayerTableFactory(config,lang,translations);
					String content = latexFactory.toLatex(idPrefix+".layer."+service.getCode(),service,layers,headerKeysLayer);
					StringIO.writeTxt(f, content);
				}
				catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
			}
		}
	}
	
	public void saveMapSections(int sectionLevel,String idPrefix) throws UtilsConfigurationException
	{
		logger.info("Creating Sections (Maps)");	
		for(Map map : maps.getMap())
		{
			for(View view : map.getView())
			{
				try
				{
					JaxbUtil.trace(view);
					Layer layer  = GeoJsfXpath.getView(layers, view.getLayer().getCode());
					JaxbUtil.trace(layer);
					view.setLayer(layer);
				}
				catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
				catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
			}
			JaxbUtil.trace(map);
			
			for(String lang : langs)
			{
			
				File f = new File(baseLatexDir,lang+"/"+dirMap+"/"+map.getCode()+".tex");
				logger.info(f.getAbsolutePath());
					
				try
				{
					OfxMapSectionFactory latexFactory = new OfxMapSectionFactory(config,lang,translations);
					String content = latexFactory.toLatex(sectionLevel,map,headerKeysMapView);
					StringIO.writeTxt(f, content);
				}
				catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
			}
		}
	}
	
	public void render(InstallationCode code) throws UtilsConfigurationException{render(code.toString());}
	public void render(InstallationCode code, InstallationArchitecture... architectures) throws UtilsConfigurationException
	{
		String[] classifier = new String[architectures.length];
		for(int i=0;i<architectures.length;i++){classifier[i]=architectures[i].toString();}
		render(code.toString(),classifier);
	}
}
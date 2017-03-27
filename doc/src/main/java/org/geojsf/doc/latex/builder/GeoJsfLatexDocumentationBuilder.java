package org.geojsf.doc.latex.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.geojsf.doc.GeoJsfDocumentation;
import org.geojsf.doc.ofx.OfxCategoryLayerSectionFactory;
import org.geojsf.doc.ofx.OfxMapSectionFactory;
import org.geojsf.doc.ofx.OfxServiceListFactory;
import org.geojsf.doc.ofx.sld.OfxSldTemplateTableFactory;
import org.geojsf.factory.xml.geojsf.XmlLayerFactory;
import org.geojsf.factory.xml.geojsf.XmlMapFactory;
import org.geojsf.factory.xml.geojsf.XmlViewFactory;
import org.geojsf.model.xml.geojsf.Category;
import org.geojsf.model.xml.geojsf.Layer;
import org.geojsf.model.xml.geojsf.Layers;
import org.geojsf.model.xml.geojsf.Map;
import org.geojsf.model.xml.geojsf.Maps;
import org.geojsf.model.xml.geojsf.Repository;
import org.geojsf.model.xml.geojsf.Service;
import org.geojsf.model.xml.geojsf.View;
import org.geojsf.util.query.xpath.GeoJsfXpath;
import org.geojsf.util.wms.WmsTileDownloader;
import org.jeesl.doc.latex.builder.AbstractLatexDocumentationBuilder;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.util.filter.OfxLangFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

public class GeoJsfLatexDocumentationBuilder extends AbstractLatexDocumentationBuilder
{	
	final static Logger logger = LoggerFactory.getLogger(GeoJsfLatexDocumentationBuilder.class);
	
	private final static String dirDescriptions = "description/geojsf";
	private final static String dirTable = "table/geojsf";
	private final static String dirLayer = "section/geojsf/layer";
	private final static String dirMap = "section/geojsf/map";
	
	public static String glossary = "ofx.geojsf/editorial/glossary/geo.xml";
	public static String acronym = "ofx.geojsf/editorial/acronym/geo.xml";
	
	public static enum InstallationCode {instGeoserver}
	public static enum ConfigurationCode {confDs}
	public static enum SldCode {sldIntroduction,sldModel,sldRule,sldTemplates}
	public static enum InstallationType {standalone}
	
	public static enum GisCode {ogcStandards}
	public static enum GeoJsfCode {datastructure,aServices,aMaps}

	private Translations translations;
	
	private Repository repository,categories,sldTemplates;
	private Layers layers;
	private Maps maps;
	
	private List<String> headerKeysLayer,headerKeysMapView;
	
	public GeoJsfLatexDocumentationBuilder(Configuration config, Translations translations,String[] langs, ConfigurationProvider cp)
	{
		super(config,translations,langs,cp);
		this.translations=translations;
		
		headerKeysLayer = new ArrayList<String>();
		headerKeysLayer.add("geoJsfTableLayerNameCode");
//		headerKeysLayer[1] = "geoJsfTableLayerName";
		headerKeysLayer.add("geoJsfTableLayerDescription");
		
		headerKeysMapView = new ArrayList<String>();
		headerKeysMapView.add("geoJsfTableMapViewNr");
		headerKeysMapView.add("geoJsfTableMapViewLayerName");
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
		
		//GIS
		addConfig(GisCode.ogcStandards.toString(),"ofx.geojsf/geojsf/gis/ogc/introduction.xml","geojsf/gis/ogc");
		
		//GeoJSF
		addConfig(GeoJsfCode.datastructure.toString(),"ofx.geojsf/geojsf/datastructure.xml","geojsf/datastructure");
		addConfig(GeoJsfCode.aServices.toString(),"ofx.geojsf/prototype/settings/services.xml","geojsf/admin/settings/services");
		addConfig(GeoJsfCode.aMaps.toString(),"ofx.geojsf/prototype/settings/maps.xml","geojsf/admin/settings/maps");
		
		//Configuration
		addConfig(ConfigurationCode.confDs.toString(),"ofx.geojsf/geojsf/datasource.xml","geojsf/admin/datasource");
		
		//SLD
		addConfig(SldCode.sldIntroduction.toString(),"ofx.geojsf/geojsf/sld/introduction.xml","geojsf/sld/introduction");
		addConfig(SldCode.sldModel.toString(),"ofx.geojsf/geojsf/sld/model.xml","geojsf/sld/model");
		addConfig(SldCode.sldRule.toString(),"ofx.geojsf/geojsf/sld/rule.xml","geojsf/sld/rule");
		addConfig(SldCode.sldTemplates.toString(),"ofx.geojsf/geojsf/sld/templates.xml","geojsf/sld/templates");
		
	}
		
	public void loadRepository(String fileName) throws FileNotFoundException {repository = JaxbUtil.loadJAXB(fileName,Repository.class);JaxbUtil.trace(repository);}
	public void loadCategories(String fileName) throws FileNotFoundException {categories = JaxbUtil.loadJAXB(fileName,Repository.class);JaxbUtil.trace(repository);}
	public void loadLayers(String fileName) throws FileNotFoundException{layers = JaxbUtil.loadJAXB(fileName,Layers.class);JaxbUtil.trace(layers);}
	public void loadMaps(String fileName) throws FileNotFoundException{maps = JaxbUtil.loadJAXB(fileName,Maps.class);JaxbUtil.trace(maps);}
	public void loadSldTemplates(String fileName) throws FileNotFoundException{sldTemplates = JaxbUtil.loadJAXB(fileName,Repository.class);JaxbUtil.trace(sldTemplates);}
	
	public void writesldTemplates() throws OfxAuthoringException, IOException, UtilsConfigurationException
	{
		OfxSldTemplateTableFactory ofx = new OfxSldTemplateTableFactory(config, langs, translations);
		Table table = ofx.build("table.admin.geojsf.sld.templates", sldTemplates);
		for(String lang : langs)
		{
			OfxLangFilter mlf = new OfxLangFilter(lang);
			File f = new File(baseLatexDir,lang+"/"+dirTable+"/sld/templates.tex");
			this.writeTable(mlf.filterLang(table), f);
		}
		
	}
	
	public void saveServiceDescription() throws UtilsConfigurationException
	{
		logger.info("Creating "+Service.class.getSimpleName()+"."+Descriptions.class.getSimpleName());
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
	
	public void writeLayerSections(int sectionLevel,String idPrefix) throws UtilsConfigurationException
	{
		logger.info("Creating "+Map.class.getSimpleName()+"."+Section.class.getSimpleName());
		
		for(Category category : categories.getCategory())
		{
			logger.info("Category: "+category.getCode());
			for(Layer layer : layers.getLayer())
			{
				if(layer.getCategory().getCode().equals(category.getCode()))
				{
					category.getLayer().add(layer);
				}
			}
			for(String lang : langs)
			{
				writeLayerSection(sectionLevel,idPrefix, lang,category);
			}
		}
	}
	
	private void writeLayerSection(int sectionLevel,String idPrefix, String lang, Category category) throws UtilsConfigurationException
	{
		File fTex = new File(baseLatexDir,lang+"/"+dirLayer+"/"+category.getCode()+".tex");
		
		try
		{
			OfxCategoryLayerSectionFactory lfSectionLayer = new OfxCategoryLayerSectionFactory(config,lang,translations);
			Section section = lfSectionLayer.create(category, headerKeysMapView);
			this.writeSection(sectionLevel,section,fTex);
		}
		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (OfxConfigurationException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	public void saveMapSections(int sectionLevel,String idPrefix) throws UtilsConfigurationException
	{
		logger.info("Creating "+Map.class.getSimpleName()+"."+Section.class.getSimpleName());
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
					Section section = latexFactory.create(map, headerKeysMapView);
					this.writeSection(sectionLevel,section, f);
				}
				catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
				catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
				catch (OfxConfigurationException e) {throw new UtilsConfigurationException(e.getMessage());}
			}
		}
	}
	
	public void downloadTilesMap(File fBase)
	{
		WmsTileDownloader tileDownloader = new WmsTileDownloader(repository.getService().get(0));
		tileDownloader.setWidth(60);
		tileDownloader.setHeight(40);
		logger.info("Downloading Map Tiles");
		for(Map map : maps.getMap())
		{
			if(!map.getCode().equals("landBushfire"))
			{
				logger.info("  "+map.getCode());
				tileDownloader.download(map,new File(fBase,map.getCode()+".png"));
			}
			
		}
	}
	
	public void downloadTilesLayer(File fBase)
	{
		WmsTileDownloader tileDownloader = new WmsTileDownloader(repository.getService().get(0));
		logger.info("Downloading Layer Tiles");
		for(Layer layer : layers.getLayer())
		{
			if(!layer.getCategory().getCode().equals("development"))
			{
				JaxbUtil.trace(layer);
				Map map = XmlMapFactory.build(layer.getViewPort());
				map.getView().add(XmlViewFactory.build(1, true, false, XmlLayerFactory.build(layer.getCode())));
				
				logger.info("  "+layer.getCode());
				File f = new File(fBase,layer.getCode().replaceAll("\\.", "")+".png");
				JaxbUtil.trace(map);
				tileDownloader.download(map,f);
			}
		}
	}
	
	public void render(int lvl,GisCode code) throws UtilsConfigurationException, OfxConfigurationException{render(lvl,code.toString());}
	public void render(int lvl,SldCode code) throws UtilsConfigurationException, OfxConfigurationException{render(lvl,code.toString());}
	public void render(ConfigurationCode code) throws UtilsConfigurationException, OfxConfigurationException{render(code.toString());}
	public void render(InstallationCode code) throws UtilsConfigurationException, OfxConfigurationException{render(code.toString());}
	public void render(InstallationCode code, InstallationType... types) throws UtilsConfigurationException, OfxConfigurationException
	{
		String[] classifier = new String[types.length];
		for(int i=0;i<types.length;i++){classifier[i]=types[i].toString();}
		render(code.toString(),classifier);
	}
	
	public void render(int lvl,GeoJsfCode code) throws UtilsConfigurationException, OfxConfigurationException
	{
		render(lvl,code.toString());
	}
	public void render(GeoJsfCode code) throws UtilsConfigurationException, OfxConfigurationException
	{
		render(code.toString());
	}
}
package org.geojsf.client.doc;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.configuration.Configuration;
import org.geojsf.test.GeoJsfDocTestBootstrap;
import org.jeesl.exception.processing.UtilsConfigurationException;
import org.jeesl.model.xml.system.io.db.Db;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.media.cross.LatexCrossMediaManager;
import org.openfuxml.util.media.MediaSourceModificationTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.db.xml.UtilsDbXmlSeedUtil;
import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.doc.ofx.status.OfxStatusTableFactory;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.xml.JaxbUtil;

@SuppressWarnings("unused")
public class GeoJsfOfxDocumentation
{	
	final static Logger logger = LoggerFactory.getLogger(GeoJsfOfxDocumentation.class);
	
	private Configuration config;
	
	private final String[] langs = {"en"};
	
	private String appCode = "dss";
	
	private UtilsDbXmlSeedUtil seedUtil;
	private Translations translations;
	
	private CrossMediaManager cmm;
	private DefaultSettingsManager dsm;
	
	private File baseLatexDir;
	private File baseOfxDir;
	
	public GeoJsfOfxDocumentation(Configuration config, Db dbSeed, Translations translations)
	{
		this.config=config;
		this.translations=translations;
		seedUtil = new UtilsDbXmlSeedUtil(dbSeed,null);
		
		baseLatexDir = new File(config.getString(UtilsDocumentation.keyBaseLatexDir));
		baseOfxDir = new File(config.getString(UtilsDocumentation.keyBaseOfxDir));
		File trackerDb = new File(config.getString("doc.ofx.mediaSourceTracker"));
		MediaSourceModificationTracker msmt = new MediaSourceModificationTracker(trackerDb);
		cmm = new LatexCrossMediaManager(new File(config.getString(UtilsDocumentation.keyBaseLatexDir)),msmt);
		dsm = new GeoJsfOfxSettingsManager();
		
	}
	
	public void optionTablesOfx() throws FileNotFoundException, UtilsConfigurationException
	{
//		Aht sldStyles = JaxbUtil.loadJAXB(seedUtil.getExtractName(DataSource.ide, DefaultGeoJsfSldStyle.class.getSimpleName()), Aht.class);
//		Aht sldTypes = JaxbUtil.loadJAXB(seedUtil.getExtractName(DataSource.ide, DefaultGeoJsfSldType.class.getSimpleName()), Aht.class);
		
//		table(sldStyles,null,"geojsf/sld/style","src/main/resources/ofx.geojsf/geojsf/sld/table/style.xml",15,20,30);
//		table(sldTypes,null,"geojsf/sld/type","src/main/resources/ofx.geojsf/geojsf/sld/table/type.xml",15,20,30);
	}
		
	private void table(Aht athStatus, Aht ahtParents, String srcXml, String dstXml, int... colWidths) throws UtilsConfigurationException
	{	
		try
		{
			logger.info(srcXml);
			OfxStatusTableFactory fOfx = new OfxStatusTableFactory(config,langs,translations);
			fOfx.setColWidths(colWidths);
			
			if(ahtParents!=null){fOfx.activateParents(ahtParents);}
//			fOfx.renderColumn(Code.icon, withIcon);
			
			Table table = fOfx.buildLatexTable(srcXml.replaceAll("/", "."),athStatus);
			JaxbUtil.save(new File(dstXml), table, true);
		}
		catch (OfxAuthoringException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = GeoJsfDocTestBootstrap.init();
		
		config.setProperty(UtilsDocumentation.keyTranslationFile, "msg.geojsf/doc/ofx.xml");
		config.setProperty(UtilsDocumentation.keyBaseLatexDir, "src/main/latex/common");
		config.setProperty(UtilsDocumentation.keyBaseOfxDir, "src/main/resources/ofx.geojsf");
		
		String dbSeedFile = config.getString("db.seed");
		logger.debug("Using seed: "+dbSeedFile);
		Db dbSeed = JaxbUtil.loadJAXB(dbSeedFile, Db.class);
		
		Translations translations = JaxbUtil.loadJAXB(config.getString(UtilsDocumentation.keyTranslationFile), Translations.class);

		GeoJsfOfxDocumentation ofxDocumentan = new GeoJsfOfxDocumentation(config,dbSeed,translations);
		ofxDocumentan.optionTablesOfx();
	}
}
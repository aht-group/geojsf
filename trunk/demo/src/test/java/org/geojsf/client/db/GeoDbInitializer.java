package org.geojsf.client.db;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import net.sf.ahtutils.controller.UtilsJbossFacadeLookup;
import net.sf.ahtutils.db.xml.AhtDbXmlInit;
import net.sf.ahtutils.db.xml.AhtDbXmlInit.Priority;
import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.db.xml.AhtXmlInitIdMapper;
import net.sf.ahtutils.exception.ejb.UtilsIntegrityException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.dbseed.Db;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.client.GeoBootstrap;
import org.geojsf.client.db.init.AdminInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoDbInitializer
{
	final static Logger logger = LoggerFactory.getLogger(GeoDbInitializer.class);

	private List<AhtDbXmlInit> lTgIniter;
	private UtilsJbossFacadeLookup  fl;
	private Db dbSeed;
	private AhtXmlInitIdMapper idMapper;
	private AhtStatusDbInit asdi;
	
	public GeoDbInitializer(Db dbSeed, UtilsJbossFacadeLookup  fl) throws NamingException
	{
		this.dbSeed=dbSeed;
		this.fl=fl;
		lTgIniter = new ArrayList<AhtDbXmlInit>();
		initAhtStatusDbInit();
		initIdMapper();
	}
	
	private void initAhtStatusDbInit() throws NamingException
	{
		asdi = new AhtStatusDbInit();
	}
	
	private void initIdMapper()
	{
		idMapper = new AhtXmlInitIdMapper();
	}
	
	public void populateDb() throws FileNotFoundException, UtilsIntegrityException, UtilsConfigurationException, NamingException
	{		
		logger.info("The GeoJSF Database will be populated with default values");
		
		lTgIniter.add(new AdminInit(dbSeed,fl,idMapper,asdi));

		List<Priority> listPriority = new ArrayList<Priority>();
		listPriority.add(Priority.statics);
		listPriority.add(Priority.required);
		listPriority.add(Priority.mandatory);
		listPriority.add(Priority.optional);
		
		for(Priority priority : listPriority)
		{
			for(AhtDbXmlInit dbInit : lTgIniter)
			{
				dbInit.initFromXml(priority);
			}
		}
		
	}
	
	public static void main(String args[]) throws Exception
	{
		Configuration config = GeoBootstrap.init();	
		
		String dbSeedFile = config.getString("db.seed");
		logger.debug("Using seed: "+dbSeedFile);
		Db dbSeed = (Db)JaxbUtil.loadJAXB(dbSeedFile, Db.class);
		
		try
		{
			UtilsJbossFacadeLookup uf = GeoBootstrap.buildFacadeLookup(config);
			GeoDbInitializer dbInit = new GeoDbInitializer(dbSeed,uf);
			dbInit.populateDb();
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (UtilsIntegrityException e) {e.printStackTrace();}
	}	
}
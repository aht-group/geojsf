package org.geojsf.client.db.init;

import net.sf.ahtutils.controller.UtilsJbossFacadeLookup;
import net.sf.ahtutils.controller.factory.ejb.status.EjbLangFactory;
import net.sf.ahtutils.db.xml.AbstractAhtDbXmlInit;
import net.sf.ahtutils.db.xml.AhtStatusDbInit;
import net.sf.ahtutils.db.xml.AhtXmlInitIdMapper;
import net.sf.ahtutils.model.interfaces.status.UtilsLang;
import net.sf.ahtutils.xml.dbseed.Db;
import net.sf.geojsf.demo.model.util.GeoLang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractErpInit <L extends UtilsLang> extends AbstractAhtDbXmlInit
{
	final static Logger logger = LoggerFactory.getLogger(AbstractErpInit.class);
	
	protected UtilsJbossFacadeLookup fl;
	protected EjbLangFactory<GeoLang> ejbLangFactory;
	protected EjbLangFactory<L> genricEjbLangFactory;
	
	final Class<L> langClass;
	
	public AbstractErpInit(final Class<L> langClass, Db dbSeed, UtilsJbossFacadeLookup fl, AhtXmlInitIdMapper idMapper, AhtStatusDbInit asdi)
	{
		super(dbSeed,DataSource.ide,idMapper,asdi);
		this.langClass=langClass;
		this.fl=fl;
		ejbLangFactory = EjbLangFactory.createFactory(GeoLang.class);
		genricEjbLangFactory = EjbLangFactory.createFactory(langClass);
	}
}
package org.geojsf.test;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.test.AbstractXmlTest;
import org.exlp.util.jx.JaxbUtil;
import org.geojsf.model.xml.GeoJsfNsPrefixMapper;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class AbstractGeoJsfXmlTest <T extends Object> extends AbstractXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsfXmlTest.class);
	
	public AbstractGeoJsfXmlTest(Class<T> cXml, Path pSuffix)
	{
		super(cXml,Paths.get("geojsf","system","io","jaxb"),pSuffix);
	}
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerBootstrap.instance().path("geojsf/system/io/log").init();
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
	}
}
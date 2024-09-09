package org.geojsf.test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

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
	
	protected static Collection<Object[]> initFileNames(String srcDir, String fileSuffix)
	{
		Collection<Object[]> c = new ArrayList<Object[]>();
		File dirSrc = new File(srcDir);
		for(File f : dirSrc.listFiles())
		{
			if(f.getName().endsWith(fileSuffix))
			{
				Object[] o = new Object[] {f};
				c.add(o);
			}
		}
		return c;
	}
}
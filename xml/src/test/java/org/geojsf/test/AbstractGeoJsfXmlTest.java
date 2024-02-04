package org.geojsf.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.exlp.util.io.log.LoggerInit;
import org.exlp.util.jx.JaxbUtil;
import org.geojsf.model.xml.GeoJsfNsPrefixMapper;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;

@Ignore
public class AbstractGeoJsfXmlTest <T extends Object> extends AbstractAhtUtilsXmlTest<T>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractGeoJsfXmlTest.class);
	
	public AbstractGeoJsfXmlTest(){this(null,null);}
	public AbstractGeoJsfXmlTest(Class<T> cXml,String xmlDirSuffix)
	{
		super(cXml,xmlDirSuffix);
	}
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.path("config.geojsf-entities.test");
		loggerInit.init();
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
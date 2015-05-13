package org.geojsf.model.xml.water.surface;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlBasins extends AbstractXmWaterSurfaceTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlBasins.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Basins.class);}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Basins actual = create(true);
    	Basins expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Basins.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Basins create(boolean withChilds)
    {
    	Basins xml = new Basins();
    	
    	if(withChilds)
    	{
    		xml.getBasin().add(TestXmlBasin.create(false));
    		xml.getBasin().add(TestXmlBasin.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlBasins.initFiles();	
		TestXmlBasins test = new TestXmlBasins();
		test.save();
    }
}
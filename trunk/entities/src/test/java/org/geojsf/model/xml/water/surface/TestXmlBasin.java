package org.geojsf.model.xml.water.surface;

import java.io.FileNotFoundException;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.geojsf.xml.geojsf.TestXmlWkt;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlBasin extends AbstractXmWaterSurfaceTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlBasin.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Basin.class);}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Basin actual = create(true);
    	Basin expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Basin.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Basin create(boolean withChilds)
    {
    	Basin xml = new Basin();
    	xml.setId(123);
    	xml.setCode("abc");
    	xml.setLabel("label");
    	
    	if(withChilds)
    	{
    		xml.setWkt(TestXmlWkt.create(false));
    		xml.setLangs(XmlLangsFactory.build());
    		xml.setDescriptions(XmlDescriptionsFactory.build());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlBasin.initFiles();	
		TestXmlBasin test = new TestXmlBasin();
		test.save();
    }
}
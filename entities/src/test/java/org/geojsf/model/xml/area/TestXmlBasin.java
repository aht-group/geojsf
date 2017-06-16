package org.geojsf.model.xml.area;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.model.xml.area.Basin;
import org.geojsf.model.xml.geojsf.TestXmlWkt;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.jeesl.factory.xml.system.lang.XmlDescriptionsFactory;
import org.jeesl.factory.xml.system.lang.XmlLangsFactory;
import org.jeesl.factory.xml.system.status.XmlModelFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlBasin extends AbstractXmAreaTest
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
    	xml.setSize(345.67);
    	xml.setLabel("label");
    	
    	if(withChilds)
    	{
    		xml.setWkt(TestXmlWkt.create(false));
    		xml.setLangs(XmlLangsFactory.build());
    		xml.setDescriptions(XmlDescriptionsFactory.build());
    		xml.setModel(XmlModelFactory.build("myCode"));
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
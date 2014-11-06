package org.geojsf.xml.geojsf;

import java.io.FileNotFoundException;

import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlService extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlService.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Service.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Service actual = create(true);
    	Service expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Service.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Service create(boolean withChilds)
    {
    	Service xml = new Service();
    	xml.setId(1);
    	xml.setCode("myCode");
    	xml.setWms("wms");
    	xml.setWcs("wcs");
    	    	
    	if(withChilds)
    	{
    		xml.getLayer().add(TestXmlLayer.create(false));xml.getLayer().add(TestXmlLayer.create(false));
    		xml.setLangs(new Langs());
    		xml.setDescriptions(new Descriptions());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlService.initFiles();	
		TestXmlService test = new TestXmlService();
		test.save();
    }
}
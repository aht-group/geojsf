package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.jeesl.model.xml.io.locale.status.Descriptions;
import org.jeesl.model.xml.io.locale.status.Langs;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestXmlLayer extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLayer.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Layer.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Layer actual = create(true);
    	Layer expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Layer.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Layer create(boolean withChilds)
    {
    	Layer xml = new Layer();
    	xml.setId(1l);
    	xml.setCode("myCode");
    	xml.setWorkspace("myWorkspace");
    	xml.setName("myName");
    	xml.setTemporal(true);
    	xml.setSql(true);
    	
    	if(withChilds)
    	{
    		xml.setLegend(TestXmlLegend.create(false));
    		xml.setLangs(new Langs());
    		xml.setDescriptions(new Descriptions());
    		
    		xml.setService(TestXmlService.create(false));
    		xml.setCategory(TestXmlCategory.create(false));
    		
    		xml.setViewPort(TestXmlViewPort.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlLayer.initFiles();	
		TestXmlLayer test = new TestXmlLayer();
		test.save();
    }
}
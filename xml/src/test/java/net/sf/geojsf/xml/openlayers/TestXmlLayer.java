package net.sf.geojsf.xml.openlayers;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.geojsf.test.GeoJsfXmlTstBootstrap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestXmlLayer extends AbstractXmlOpenlayersTest
{
	static Log logger = LogFactory.getLog(TestXmlLayer.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"layer.xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Layer actual = create();
    	Layer expected = (Layer)JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Layer.class);
    	assertJaxbEquals(expected, actual);
    }
    
    private static Layer create() {return create(true);}
    public static Layer create(boolean withChilds)
    {
    	Layer xml = new Layer();
    	xml.setId(1);
    	xml.setCode("myCode");
    	xml.setWorkspace("myWorkspace");
    	xml.setName("myName");
    	xml.setVisible(true);
    	xml.setShowLegend(true);
    	
    	if(withChilds)
    	{
    		xml.setLegend(TestXmlLegend.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlLayer.initFiles();	
		TestXmlLayer test = new TestXmlLayer();
		test.save();
    }
}
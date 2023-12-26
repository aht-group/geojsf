package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlView extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlView.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, View.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	View actual = create(true);
    	View expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), View.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static View create(boolean withChilds)
    {
    	View xml = new View();
    	xml.setId(123l);
    	xml.setNr(1);
    	xml.setVisible(true);
    	xml.setLegend(false);
     	
    	if(withChilds)
    	{
    		xml.setLayer(TestXmlLayer.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlView.initFiles();	
		TestXmlView test = new TestXmlView();
		test.save();
    }
}
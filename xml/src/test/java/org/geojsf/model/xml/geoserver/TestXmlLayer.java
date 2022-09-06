package org.geojsf.model.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.model.xml.geoserver.Layer;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLayer extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLayer.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Layer.class.getSimpleName()+".xml");
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
    	xml.setName("myName");
    	xml.setType("myType");
    	xml.setEnabled(true);
    		
    	if(withChilds)
    	{
    		xml.setCoverageStore(TestXmlCoverageStore.create(false));
    		xml.setFeatureType(TestXmlFeatureType.create(false));
    		xml.setStyles(TestXmlStyles.create(false));
    		xml.setStyle(TestXmlStyle.create(false));
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
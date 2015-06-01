package org.geojsf.model.xml.geojsf;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.model.xml.geojsf.Coordinate;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCoordinate extends AbstractXmlGeojsfTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCoordinate.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, "coordinate");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Coordinate actual = create(true);
    	Coordinate expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Coordinate.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Coordinate create(boolean withChilds)
    {
    	Coordinate xml = new Coordinate();
    	xml.setLat(1.234);
    	xml.setLon(5.678);
    	
    	if(withChilds)
    	{
    	
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlCoordinate.initFiles();	
		TestXmlCoordinate test = new TestXmlCoordinate();
		test.save();
    }
}
package org.geojsf.model.xml.monitoring;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.model.xml.monitoring.Stations;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStations extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStations.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Stations.class);}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Stations actual = create(true);
    	Stations expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Stations.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Stations create(boolean withChilds)
    {
    	Stations xml = new Stations();
    	
    	if(withChilds)
    	{
    		xml.getStation().add(TestXmlStation.create(false));
    		xml.getStation().add(TestXmlStation.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlStations.initFiles();	
		TestXmlStations test = new TestXmlStations();
		test.save();
    }
}
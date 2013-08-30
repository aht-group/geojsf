package org.geojsf.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.net.Database;
import net.sf.exlp.xml.net.Host;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlConnection extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlConnection.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Connection.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Connection actual = create(true);
    	Connection expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Connection.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Connection create(boolean withChilds)
    {
    	Connection xml = new Connection();
    	xml.setTimeout(10);
    	xml.setMax(20);
    	xml.setMin(30);
    	xml.setFetchSize(40);
    	xml.setMaxPreparedStatements(50);
    	xml.setValidate(true);
    	
    	if(withChilds)
    	{
    		xml.setHost(new Host());
    		xml.setDatabase(new Database());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlConnection.initFiles();	
		TestXmlConnection test = new TestXmlConnection();
		test.save();
    }
}
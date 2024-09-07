package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlPostgis extends AbstractXmlGeoserverTest<Postgis>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlPostgis.class);
	
	public TestXmlPostgis() {super(Postgis.class);}
	public static Postgis create(boolean withChildren){return (new TestXmlPostgis()).build(withChildren);}
 
    
    public Postgis build(boolean withChilds)
    {
    	Postgis xml = new Postgis();
    	
    	if(withChilds)
    	{
    		xml.setConnection(TestXmlConnection.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
    
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.init();	
  		TestXmlPostgis test = new TestXmlPostgis();
  		test.saveReferenceXml();
	}
}
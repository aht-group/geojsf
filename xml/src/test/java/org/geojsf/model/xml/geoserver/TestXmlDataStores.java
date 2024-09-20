package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDataStores extends AbstractXmlGeoserverTest<DataStores>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDataStores.class);
	
	public TestXmlDataStores() {super(DataStores.class);}
	public static DataStores create(boolean withChildren) {return (new TestXmlDataStores()).build(withChildren);}
 
    
    public DataStores build(boolean withChilds)
    {
    	DataStores xml = new DataStores();
    	
    	if(withChilds)
    	{
    		xml.getDataStore().add(TestXmlDataStore.create(false));
    		xml.getDataStore().add(TestXmlDataStore.create(false));
    	}
    	
    	return xml;
    }
	
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.init();	
  		TestXmlDataStores test = new TestXmlDataStores();
  		test.saveReferenceXml();
	}
}
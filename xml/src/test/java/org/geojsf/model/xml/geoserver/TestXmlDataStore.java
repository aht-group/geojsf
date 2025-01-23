package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDataStore extends AbstractXmlGeoserverTest<DataStore>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDataStore.class);
	
	public TestXmlDataStore() {super(DataStore.class);}
	public static DataStore create(boolean withChildren) {return (new TestXmlDataStore()).build(withChildren);}
 
    
    public DataStore build(boolean withChilds)
    {
    	DataStore xml = new DataStore();
    	xml.setName("myName");
    	xml.setDescription("myDescription");
    	xml.setType("myType");
    	xml.setEnabled(true);
    	
    	if(withChilds)
    	{
    		xml.setWorkspace(TestXmlWorkspace.create(false));
    		xml.setPostgis(TestXmlPostgis.create(false));
    		xml.setShapeDir(TestXmlShapeDir.create(false));
    	}
    	
    	return xml;
    }
	
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.init();	
  		TestXmlDataStore test = new TestXmlDataStore();
  		test.saveReferenceXml();
	}
}
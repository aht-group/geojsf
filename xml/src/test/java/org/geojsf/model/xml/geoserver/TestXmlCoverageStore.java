package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCoverageStore extends AbstractXmlGeoserverTest<CoverageStore>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCoverageStore.class);
	
	public TestXmlCoverageStore() {super(CoverageStore.class);}
	public static CoverageStore create(boolean withChildren){return (new TestXmlCoverageStore()).build(withChildren);}
 
    
    public CoverageStore build(boolean withChilds)
    {
    	CoverageStore xml = new CoverageStore();
    	xml.setName("myName");
    	xml.setDescription("myDescription");
    	xml.setType("myType");
    	xml.setEnabled(true);
    	xml.setUrl("myUrl");
    	
    	if(withChilds)
    	{
    		xml.setCoverages(TestXmlCoverages.create(false));
    	}
    	
    	return xml;
    }
	
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.wrap();	
  		TestXmlCoverageStore test = new TestXmlCoverageStore();
  		test.saveReferenceXml();
	}
}
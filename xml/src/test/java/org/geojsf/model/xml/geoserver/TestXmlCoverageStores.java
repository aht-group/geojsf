package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCoverageStores extends AbstractXmlGeoserverTest<CoverageStores>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCoverageStores.class);
	
	public TestXmlCoverageStores() {super(CoverageStores.class);}
	public static CoverageStores create(boolean withChildren){return (new TestXmlCoverageStores()).build(withChildren);}
 
    
    public CoverageStores build(boolean withChilds)
    {
    	CoverageStores xml = new CoverageStores();
    	
    	if(withChilds)
    	{
    		xml.getCoverageStore().add(TestXmlCoverageStore.create(false));
    		xml.getCoverageStore().add(TestXmlCoverageStore.create(false));
    	}
    	
    	return xml;
    }
	
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.init();	
  		TestXmlCoverageStores test = new TestXmlCoverageStores();
  		test.saveReferenceXml();
	}
}
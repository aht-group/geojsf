package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCoverages extends AbstractXmlGeoserverTest<Coverages>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCoverages.class);
	
	public TestXmlCoverages() {super(Coverages.class);}
	public static Coverages create(boolean withChildren){return (new TestXmlCoverages()).build(withChildren);}
 
    
    public Coverages build(boolean withChilds)
    {
    	Coverages xml = new Coverages();
    	
    	if(withChilds)
    	{
    		xml.getCoverage().add(TestXmlCoverage.create(false));
    		xml.getCoverage().add(TestXmlCoverage.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.init();	
  		TestXmlCoverages test = new TestXmlCoverages();
  		test.saveReferenceXml();
	}
}
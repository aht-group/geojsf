package org.geojsf.model.xml.specs.ogc;

import org.geojsf.model.xml.specs.gml.TestXmlPoint;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DWithinXmlTest extends AbstractXmlOgcTest<DWithin>
{
	final static Logger logger = LoggerFactory.getLogger(DWithinXmlTest.class);
	
	public DWithinXmlTest(){super(DWithin.class);}
	public static DWithin create(boolean withChildren){return (new DWithinXmlTest()).build(withChildren);}
    
    public DWithin build(boolean withChilds)
    {
    	DWithin xml = new DWithin();

    	if(withChilds)
    	{
    		xml.setPropertyName(TestXmlPropertyName.create(false));
    		xml.setPoint(TestXmlPoint.create(false));
    		xml.setDistance(TestXmlDistance.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		DWithinXmlTest test = new DWithinXmlTest();
		test.saveReferenceXml();
    }
}
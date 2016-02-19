package org.geojsf.model.xml.specs.ogc;

import org.geojsf.model.xml.ogc.DWithin;
import org.geojsf.model.xml.specs.gml.TestXmlPoint;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDWithin extends AbstractXmlOgcTest<DWithin>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDWithin.class);
	
	public TestXmlDWithin(){super(DWithin.class);}
	public static DWithin create(boolean withChildren){return (new TestXmlDWithin()).build(withChildren);}
    
    public DWithin build(boolean withChilds)
    {
    	DWithin xml = new DWithin();

    	if(withChilds)
    	{
    		xml.setPoint(TestXmlPoint.create(false));
    		xml.setDistance(TestXmlDistance.create(false));
    		xml.setPropertyName(TestXmlPropertyName.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlDWithin test = new TestXmlDWithin();
		test.saveReferenceXml();
    }
}
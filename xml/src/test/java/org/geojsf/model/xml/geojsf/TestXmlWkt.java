package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlWkt extends AbstractXmlGeojsfTest<Wkt>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlWkt.class);
	
	public TestXmlWkt(){super(Wkt.class);}
	public static Wkt create(boolean withChildren){return (new TestXmlWkt()).build(withChildren);}

    
    public Wkt build(boolean withChilds)
    {
    	Wkt xml = new Wkt();
    	xml.setType("myType");
    	xml.setValue("myWKT");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlWkt test = new TestXmlWkt();
		test.saveReferenceXml();
    }
}
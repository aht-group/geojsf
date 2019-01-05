package org.geojsf.model.xml.area;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlAreas extends AbstractXmAreaTest<Areas>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlAreas.class);
	
	public TestXmlAreas(){super(Areas.class);}
	public static Areas create(boolean withChildren){return (new TestXmlAreas()).build(withChildren);}
    
    public Areas build(boolean withChilds)
    {
    	Areas xml = new Areas();
    	
    	if(withChilds)
    	{
    		xml.getBasin().add(TestXmlBasin.create(false));
    		xml.getBasin().add(TestXmlBasin.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlAreas test = new TestXmlAreas();
		test.saveReferenceXml();
    }
}
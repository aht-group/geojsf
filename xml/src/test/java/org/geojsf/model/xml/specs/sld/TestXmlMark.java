package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMark extends AbstractXmlSldTest<Mark>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMark.class);
	
	public TestXmlMark(){super(Mark.class);}
	public static Mark create(boolean withChildren){return (new TestXmlMark()).build(withChildren);}
    
    public Mark build(boolean withChilds)
    {
    	Mark xml = new Mark();
    	
    	if(withChilds)
    	{
    		xml.setWellKnownName(TestXmlWellKnownName.create(false));
    		xml.setFill(TestXmlFill.create(false));
    		xml.setStroke(TestXmlStroke.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		TestXmlMark test = new TestXmlMark();
		test.saveReferenceXml();
    }
}
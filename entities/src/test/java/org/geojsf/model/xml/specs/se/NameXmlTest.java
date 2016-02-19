package org.geojsf.model.xml.specs.se;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameXmlTest extends AbstractXmlSeTest<Name>
{
	final static Logger logger = LoggerFactory.getLogger(NameXmlTest.class);
	
	public NameXmlTest(){super(Name.class);}
	public static Name create(boolean withChildren){return (new NameXmlTest()).build(withChildren);}
    
    public Name build(boolean withChilds)
    {
    	Name xml = new Name();
    	xml.setValue("myName");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		NameXmlTest test = new NameXmlTest();
		test.saveReferenceXml();
    }
}
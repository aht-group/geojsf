package org.geojsf.model.xml.specs.se;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TitleXmlTest extends AbstractXmlSeTest<Title>
{
	final static Logger logger = LoggerFactory.getLogger(TitleXmlTest.class);
	
	public TitleXmlTest(){super(Title.class);}
	public static Title create(boolean withChildren){return (new TitleXmlTest()).build(withChildren);}
    
    public Title build(boolean withChilds)
    {
    	Title xml = new Title();
    	xml.setValue("myTitle");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TitleXmlTest test = new TitleXmlTest();
		test.saveReferenceXml();
    }
}
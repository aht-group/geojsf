package org.geojsf.model.xml.specs.se;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DescriptionXmlTest extends AbstractXmlSeTest<Description>
{
	final static Logger logger = LoggerFactory.getLogger(DescriptionXmlTest.class);
	
	public DescriptionXmlTest(){super(Description.class);}
	public static Description create(boolean withChildren){return (new DescriptionXmlTest()).build(withChildren);}
    
    public Description build(boolean withChilds)
    {
    	Description xml = new Description();
    	
    	if(withChilds)
    	{
    		xml.setTitle(TitleXmlTest.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		DescriptionXmlTest test = new DescriptionXmlTest();
		test.saveReferenceXml();
    }
}
package org.geojsf.model.xml.specs.sld;

import org.geojsf.model.xml.specs.se.AbstractXmlSeTest;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFeatureTypeStyle extends AbstractXmlSeTest<FeatureTypeStyle>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFeatureTypeStyle.class);
	
	public TestXmlFeatureTypeStyle(){super(FeatureTypeStyle.class);}
	public static FeatureTypeStyle create(boolean withChildren){return (new TestXmlFeatureTypeStyle()).build(withChildren);}
    
    public FeatureTypeStyle build(boolean withChilds)
    {
    	FeatureTypeStyle xml = new FeatureTypeStyle();
    	
    	if(withChilds)
    	{
    		xml.getRule().add(TestXmlRule.create(false));
    		xml.getRule().add(TestXmlRule.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlFeatureTypeStyle test = new TestXmlFeatureTypeStyle();
		test.saveReferenceXml();
    }
}
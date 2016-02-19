package org.geojsf.model.xml.specs.se;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeatureTypeStyleXmlTest extends AbstractXmlSeTest<FeatureTypeStyle>
{
	final static Logger logger = LoggerFactory.getLogger(FeatureTypeStyleXmlTest.class);
	
	public FeatureTypeStyleXmlTest(){super(FeatureTypeStyle.class);}
	public static FeatureTypeStyle create(boolean withChildren){return (new FeatureTypeStyleXmlTest()).build(withChildren);}
    
    public FeatureTypeStyle build(boolean withChilds)
    {
    	FeatureTypeStyle xml = new FeatureTypeStyle();
    	
    	if(withChilds)
    	{
    		xml.getRule().add(RuleXmlTest.create(false));
    		xml.getRule().add(RuleXmlTest.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		FeatureTypeStyleXmlTest test = new FeatureTypeStyleXmlTest();
		test.saveReferenceXml();
    }
}
package org.geojsf.model.xml.specs.sld;

import org.geojsf.model.xml.specs.ogc.TestXmlFilter;
import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRule extends AbstractXmlSldTest<Rule>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRule.class);
	
	public TestXmlRule(){super(Rule.class);}
	public static Rule create(boolean withChildren){return (new TestXmlRule()).build(withChildren);}
    
    public Rule build(boolean withChilds)
    {
    	Rule xml = new Rule();
    	
    	if(withChilds)
    	{
    		xml.setName(TestXmlName.create(false));
    		xml.setDescription(TestXmlDescription.create(false));
    		xml.setFilter(TestXmlFilter.create(false));
    		xml.setPointSymbolizer(TestXmlPointSymbolizer.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlRule test = new TestXmlRule();
		test.saveReferenceXml();
    }
}
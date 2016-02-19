package org.geojsf.model.xml.specs.se;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleXmlTest extends AbstractXmlSeTest<Rule>
{
	final static Logger logger = LoggerFactory.getLogger(RuleXmlTest.class);
	
	public RuleXmlTest(){super(Rule.class);}
	public static Rule create(boolean withChildren){return (new RuleXmlTest()).build(withChildren);}
    
    public Rule build(boolean withChilds)
    {
    	Rule xml = new Rule();
    	
    	if(withChilds)
    	{
    		xml.setName(NameXmlTest.create(false));
    		xml.setDescription(DescriptionXmlTest.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		RuleXmlTest test = new RuleXmlTest();
		test.saveReferenceXml();
    }
}
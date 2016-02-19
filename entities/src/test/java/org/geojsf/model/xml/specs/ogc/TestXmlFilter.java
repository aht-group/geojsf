package org.geojsf.model.xml.specs.ogc;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFilter extends AbstractXmlOgcTest<Filter>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlFilter.class);
	
	public TestXmlFilter(){super(Filter.class);}
	public static Filter create(boolean withChildren){return (new TestXmlFilter()).build(withChildren);}
    
    public Filter build(boolean withChilds)
    {
    	Filter xml = new Filter();

    	if(withChilds)
    	{
    		xml.setDWithin(TestXmlDWithin.create(false));
    		xml.setPropertyIsBetween(TestXmlPropertyIsBetween.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlFilter test = new TestXmlFilter();
		test.saveReferenceXml();
    }
}
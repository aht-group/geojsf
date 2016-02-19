package org.geojsf.xml.wfs;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.geojsf.xml.ogc.TestXmlFilter;
import org.geojsf.xml.ogc.TestXmlPropertyName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlQuery extends AbstractXmlWfsTest<Query>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuery.class);
	
	public TestXmlQuery(){super(Query.class);}
	public static Query create(boolean withChildren){return (new TestXmlQuery()).build(withChildren);}
    
    public Query build(boolean withChilds)
    {
    	Query xml = new Query();
    	xml.setTypeName("myTypeName");
    	
    	if(withChilds)
    	{
    		xml.setFilter(TestXmlFilter.create(false));
    		xml.getPropertyName().add(TestXmlPropertyName.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlQuery test = new TestXmlQuery();
		test.saveReferenceXml();
    }
}
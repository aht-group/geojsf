package org.geojsf.model.xml.specs.wfs;

import org.geojsf.model.xml.specs.ogc.TestXmlFilter;
import org.geojsf.model.xml.specs.ogc.TestXmlPropertyName;
import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryXmlTest extends AbstractXmlWfsTest<Query>
{
	final static Logger logger = LoggerFactory.getLogger(QueryXmlTest.class);
	
	public QueryXmlTest(){super(Query.class);}
	public static Query create(boolean withChildren){return (new QueryXmlTest()).build(withChildren);}
    
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
		GeoJsfBootstrap.init();
		QueryXmlTest test = new QueryXmlTest();
		test.saveReferenceXml();
    }
}
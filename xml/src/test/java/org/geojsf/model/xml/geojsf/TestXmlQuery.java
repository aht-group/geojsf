package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlQuery extends AbstractXmlGeojsfTest<Query>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlQuery.class);
	
	public TestXmlQuery() {super(Query.class);}
	public static Query create(boolean withChildren){return (new TestXmlQuery()).build(withChildren);}

    
    public Query build(boolean withChilds)
    {
    	Query xml = new Query();

    	if(withChilds)
    	{
    		xml.setService(TestXmlService.create(false));
    		xml.setCategory(TestXmlCategory.create(false));
    		xml.setRepository(TestXmlRepository.create(false));
    		xml.setLayer(TestXmlLayer.create(false));
    		xml.setMap(TestXmlMap.create(false));
    		xml.setView(TestXmlView.create(false));
    		xml.setViewPort(TestXmlViewPort.create(false));
    		xml.setSldTemplate(TestXmlSldTemplate.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlQuery test = new TestXmlQuery();
		test.saveReferenceXml();
    }
}
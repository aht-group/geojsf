package org.geojsf.model.xml.specs.wfs;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetFeatureXmlTest extends AbstractXmlWfsTest<GetFeature>
{
	final static Logger logger = LoggerFactory.getLogger(GetFeatureXmlTest.class);
	
	public GetFeatureXmlTest(){super(GetFeature.class);}
	public static GetFeature create(boolean withChildren){return (new GetFeatureXmlTest()).build(withChildren);}
    
    public GetFeature build(boolean withChilds)
    {
    	GetFeature xml = new GetFeature();
    	xml.setService("myService");
    	xml.setVersion("myVersion");
    	xml.setOutputFormat("myoutputFormat");
    	xml.setViewParams("myViewParams");
    	
    	if(withChilds)
    	{
    		xml.setQuery(QueryXmlTest.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		GetFeatureXmlTest test = new GetFeatureXmlTest();
		test.saveReferenceXml();
    }
}
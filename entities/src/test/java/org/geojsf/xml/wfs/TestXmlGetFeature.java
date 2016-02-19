package org.geojsf.xml.wfs;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlGetFeature extends AbstractXmlWfsTest<GetFeature>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlGetFeature.class);
	
	public TestXmlGetFeature(){super(GetFeature.class);}
	public static GetFeature create(boolean withChildren){return (new TestXmlGetFeature()).build(withChildren);}
    
    public GetFeature build(boolean withChilds)
    {
    	GetFeature xml = new GetFeature();
    	xml.setService("myService");
    	xml.setVersion("myVersion");
    	xml.setOutputFormat("myoutputFormat");
    	xml.setViewParams("myViewParams");
    	
    	if(withChilds)
    	{
    		xml.setQuery(TestXmlQuery.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlGetFeature test = new TestXmlGetFeature();
		test.saveReferenceXml();
    }
}
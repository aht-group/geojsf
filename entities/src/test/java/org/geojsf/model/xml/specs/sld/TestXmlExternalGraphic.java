package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlExternalGraphic extends AbstractXmlSldTest<ExternalGraphic>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlExternalGraphic.class);
	
	public TestXmlExternalGraphic(){super(ExternalGraphic.class);}
	public static ExternalGraphic create(boolean withChildren){return (new TestXmlExternalGraphic()).build(withChildren);}
    
    public ExternalGraphic build(boolean withChildren)
    {
    	ExternalGraphic xml = new ExternalGraphic();
    	if(withChildren)
    	{
    		xml.setOnlineResource(TestXmlOnlineResource.create(false));
    		xml.setFormat(TestXmlFormat.create(false));	
    	}
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlExternalGraphic test = new TestXmlExternalGraphic();
		test.saveReferenceXml();
    }
}
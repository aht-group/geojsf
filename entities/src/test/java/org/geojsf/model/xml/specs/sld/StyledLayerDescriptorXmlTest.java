package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StyledLayerDescriptorXmlTest extends AbstractXmlSldTest<StyledLayerDescriptor>
{
	final static Logger logger = LoggerFactory.getLogger(StyledLayerDescriptorXmlTest.class);
	
	public StyledLayerDescriptorXmlTest(){super(StyledLayerDescriptor.class);}
	public static StyledLayerDescriptor create(boolean withChildren){return (new StyledLayerDescriptorXmlTest()).build(withChildren);}
    
    public StyledLayerDescriptor build(boolean withChilds)
    {
    	StyledLayerDescriptor xml = new StyledLayerDescriptor();
    	
    	if(withChilds)
    	{
    		xml.setNamedLayer(NamedLayerXmlTest.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		StyledLayerDescriptorXmlTest test = new StyledLayerDescriptorXmlTest();
		test.saveReferenceXml();
    }
}
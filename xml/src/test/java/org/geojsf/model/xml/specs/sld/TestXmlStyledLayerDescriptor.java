package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStyledLayerDescriptor extends AbstractXmlSldTest<StyledLayerDescriptor>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStyledLayerDescriptor.class);
	
	public TestXmlStyledLayerDescriptor(){super(StyledLayerDescriptor.class);}
	public static StyledLayerDescriptor create(boolean withChildren){return (new TestXmlStyledLayerDescriptor()).build(withChildren);}
    
    public StyledLayerDescriptor build(boolean withChilds)
    {
    	StyledLayerDescriptor xml = new StyledLayerDescriptor();
    	xml.setVersion("1.0.0");
    	
    	if(withChilds)
    	{
    		xml.getNamedLayer().add(TestXmlNamedLayer.create(false));
    		xml.getNamedLayer().add(TestXmlNamedLayer.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		TestXmlStyledLayerDescriptor test = new TestXmlStyledLayerDescriptor();
		test.saveReferenceXml();
    }
}
package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlPointSymbolizer extends AbstractXmlSldTest<PointSymbolizer>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlPointSymbolizer.class);
	
	public TestXmlPointSymbolizer(){super(PointSymbolizer.class);}
	public static PointSymbolizer create(boolean withChildren){return (new TestXmlPointSymbolizer()).build(withChildren);}
    
    public PointSymbolizer build(boolean withChilds)
    {
    	PointSymbolizer xml = new PointSymbolizer();
    	
    	if(withChilds)
    	{
    		xml.setGraphic(TestXmlGraphic.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlPointSymbolizer test = new TestXmlPointSymbolizer();
		test.saveReferenceXml();
    }
}
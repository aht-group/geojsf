package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlGraphic extends AbstractXmlSldTest<Graphic>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlGraphic.class);
	
	public TestXmlGraphic(){super(Graphic.class);}
	public static Graphic create(boolean withChildren){return (new TestXmlGraphic()).build(withChildren);}
    
    public Graphic build(boolean withChilds)
    {
    	Graphic xml = new Graphic();
    	
    	if(withChilds)
    	{
    		xml.setMark(TestXmlMark.create(false));
    		xml.setExternalGraphic(TestXmlExternalGraphic.create(false));
    		xml.setSize(TestXmlSize.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		TestXmlGraphic test = new TestXmlGraphic();
		test.saveReferenceXml();
    }
}
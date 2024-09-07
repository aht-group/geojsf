package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLayers extends AbstractXmlGeoserverTest<Layers>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLayers.class);
	
	public TestXmlLayers() {super(Layers.class);}
	public static Layers create(boolean withChildren){return (new TestXmlLayers()).build(withChildren);}
 
    
    public Layers build(boolean withChilds)
    {
    	Layers xml = new Layers();
    		
    	if(withChilds)
    	{
    		xml.getLayer().add(TestXmlLayer.create(false));xml.getLayer().add(TestXmlLayer.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.init();	
  		TestXmlLayers test = new TestXmlLayers();
  		test.saveReferenceXml();
	}
}
package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLayer extends AbstractXmlGeoserverTest<Layer>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLayer.class);
	
	public TestXmlLayer() {super(Layer.class);}
	public static Layer create(boolean withChildren){return (new TestXmlLayer()).build(withChildren);}
 
    
    public Layer build(boolean withChilds)
    {
    	Layer xml = new Layer();
    	xml.setName("myName");
    	xml.setType("myType");
    	xml.setEnabled(true);
    		
    	if(withChilds)
    	{
    		xml.setCoverageStore(TestXmlCoverageStore.create(false));
    		xml.setFeatureType(TestXmlFeatureType.create(false));
    		xml.setStyles(TestXmlStyles.create(false));
    		xml.setStyle(TestXmlStyle.create(false));
    	}
    	
    	return xml;
    }
	
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.init();	
  		TestXmlLayer test = new TestXmlLayer();
  		test.saveReferenceXml();
	}
}
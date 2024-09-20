package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.jeesl.model.xml.io.locale.status.Descriptions;
import org.jeesl.model.xml.io.locale.status.Langs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMap extends AbstractXmlGeojsfTest<Map>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlMap.class);
	
	public TestXmlMap() {super(Map.class);}
	public static Map create(boolean withChildren){return (new TestXmlMap()).build(withChildren);}

    
    public Map build(boolean withChilds)
    {
    	Map xml = new Map();
    	xml.setId(123l);
    	xml.setCode("myCode");
     	
    	if(withChilds)
    	{
    		xml.getView().add(TestXmlView.create(false));
    		xml.setLangs(new Langs());
    		xml.setDescriptions(new Descriptions());
    		
    		xml.setViewPort(TestXmlViewPort.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlMap test = new TestXmlMap();
		test.saveReferenceXml();
    }
}
package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.jeesl.model.xml.io.locale.status.Descriptions;
import org.jeesl.model.xml.io.locale.status.Langs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestXmlLayer extends AbstractXmlGeojsfTest<Layer>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLayer.class);
	
	public TestXmlLayer(){super(Layer.class);}
	public static Layer create(boolean withChildren){return (new TestXmlLayer()).build(withChildren);}

    
    public Layer build(boolean withChilds)
    {
    	Layer xml = new Layer();
    	xml.setId(1l);
    	xml.setCode("myCode");
    	xml.setWorkspace("myWorkspace");
    	xml.setName("myName");
    	xml.setTemporal(true);
    	xml.setSql(true);
    	
    	if(withChilds)
    	{
    		xml.setLegend(TestXmlLegend.create(false));
    		xml.setLangs(new Langs());
    		xml.setDescriptions(new Descriptions());
    		
    		xml.setService(TestXmlService.create(false));
    		xml.setCategory(TestXmlCategory.create(false));
    		
    		xml.setViewPort(TestXmlViewPort.create(false));
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
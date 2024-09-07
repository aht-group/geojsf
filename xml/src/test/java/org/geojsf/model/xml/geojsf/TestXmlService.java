package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.jeesl.model.xml.io.locale.status.Descriptions;
import org.jeesl.model.xml.io.locale.status.Langs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlService extends AbstractXmlGeojsfTest<Service>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlService.class);
	
	public TestXmlService() {super(Service.class);}
	public static Service create(boolean withChildren){return (new TestXmlService()).build(withChildren);}

    
    public Service build(boolean withChilds)
    {
    	Service xml = new Service();
    	xml.setId(1l);
    	xml.setCode("myCode");
    	xml.setWms("wms");
    	xml.setWcs("wcs");
    	    	
    	if(withChilds)
    	{
    		xml.getLayer().add(TestXmlLayer.create(false));xml.getLayer().add(TestXmlLayer.create(false));
    		xml.setLangs(new Langs());
    		xml.setDescriptions(new Descriptions());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlService test = new TestXmlService();
		test.saveReferenceXml();
    }
}
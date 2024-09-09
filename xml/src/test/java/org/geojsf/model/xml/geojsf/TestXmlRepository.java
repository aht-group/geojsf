package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRepository extends AbstractXmlGeojsfTest<Repository>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlRepository.class);
	
	public TestXmlRepository() {super(Repository.class);}
	public static Repository create(boolean withChildren){return (new TestXmlRepository()).build(withChildren);}

    
    public Repository build(boolean withChilds)
    {
    	Repository xml = new Repository();

    	if(withChilds)
    	{
    		xml.setMaps(TestXmlMaps.create(false));
    		xml.setLayers(TestXmlLayers.create(false));
    		xml.getService().add(TestXmlService.create(false));xml.getService().add(TestXmlService.create(false));
    		xml.getCategory().add(TestXmlCategory.create(false));xml.getCategory().add(TestXmlCategory.create(false));
    		xml.getSldTemplate().add(TestXmlSldTemplate.create(false));xml.getSldTemplate().add(TestXmlSldTemplate.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlRepository test = new TestXmlRepository();
		test.saveReferenceXml();
    }
}
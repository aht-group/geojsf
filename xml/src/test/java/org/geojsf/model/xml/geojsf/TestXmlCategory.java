package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.jeesl.model.xml.io.locale.status.Descriptions;
import org.jeesl.model.xml.io.locale.status.Langs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCategory extends AbstractXmlGeojsfTest<Category>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlCategory.class);
	
	public TestXmlCategory(){super(Category.class);}
	public static Category create(boolean withChildren){return (new TestXmlCategory()).build(withChildren);}

    
    public Category build(boolean withChilds)
    {
    	Category xml = new Category();
    	xml.setId(1l);
    	xml.setCode("myCode");
    	    	
    	if(withChilds)
    	{
    		xml.getLayer().add(TestXmlLayer.create(false));xml.getLayer().add(TestXmlLayer.create(false));
    		xml.setLangs(new Langs());
    		xml.setDescriptions(new Descriptions());
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		TestXmlCategory test = new TestXmlCategory();
		test.saveReferenceXml();
    }
}
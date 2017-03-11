package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlUserStyle extends AbstractXmlSldTest<UserStyle>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlUserStyle.class);
	
	public TestXmlUserStyle(){super(UserStyle.class);}
	public static UserStyle create(boolean withChildren){return (new TestXmlUserStyle()).build(withChildren);}
    
    public UserStyle build(boolean withChilds)
    {
    	UserStyle xml = new UserStyle();
    	
    	if(withChilds)
    	{
    		xml.setName(TestXmlName.create(false));
    		xml.setFeatureTypeStyle(TestXmlFeatureTypeStyle.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		TestXmlUserStyle test = new TestXmlUserStyle();
		test.saveReferenceXml();
    }
}
package org.geojsf.model.xml.specs.sld;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserStyleXmlTest extends AbstractXmlSldTest<UserStyle>
{
	final static Logger logger = LoggerFactory.getLogger(UserStyleXmlTest.class);
	
	public UserStyleXmlTest(){super(UserStyle.class);}
	public static UserStyle create(boolean withChildren){return (new UserStyleXmlTest()).build(withChildren);}
    
    public UserStyle build(boolean withChilds)
    {
    	UserStyle xml = new UserStyle();
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
		UserStyleXmlTest test = new UserStyleXmlTest();
		test.saveReferenceXml();
    }
}
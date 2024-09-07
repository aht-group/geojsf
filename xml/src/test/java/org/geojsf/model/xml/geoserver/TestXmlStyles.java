package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlStyles extends AbstractXmlGeoserverTest<Styles>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlStyles.class);
	
	public TestXmlStyles() {super(Styles.class);}
	public static Styles create(boolean withChildren){return (new TestXmlStyles()).build(withChildren);}
 
    
    public Styles build(boolean withChilds)
    {
    	Styles xml = new Styles();
    		
    	if(withChilds)
    	{
    		xml.getStyle().add(TestXmlStyle.create(false));xml.getStyle().add(TestXmlStyle.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
    
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.init();	
  		TestXmlStyles test = new TestXmlStyles();
  		test.saveReferenceXml();
	}
}
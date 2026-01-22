package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlScales extends AbstractXmlGeojsfTest<Scales>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlScales.class);
	
	public TestXmlScales(){super(Scales.class);}
	public static Scales create(boolean withChildren){return (new TestXmlScales()).build(withChildren);}

    
    public Scales build(boolean withChilds)
    {
    	Scales xml = new Scales();
    	xml.setUnit("meter");
    	
    	if(withChilds)
    	{
    		xml.getScale().add(TestXmlScale.create(false));
    		xml.getScale().add(TestXmlScale.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		TestXmlScales test = new TestXmlScales();
		test.saveReferenceXml();
    }
}
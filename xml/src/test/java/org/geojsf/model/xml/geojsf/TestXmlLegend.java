package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLegend extends AbstractXmlGeojsfTest<Legend>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlLegend.class);
	
	public TestXmlLegend() {super(Legend.class);}
	public static Legend create(boolean withChildren){return (new TestXmlLegend()).build(withChildren);}
    
    public Legend build(boolean withChilds)
    {
    	Legend xml = new Legend();
    	xml.setId(1l);
    	xml.setUrl("myUrl");
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlLegend test = new TestXmlLegend();
		test.saveReferenceXml();
    }
}
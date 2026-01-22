package org.geojsf.model.xml.geojsf;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlView extends AbstractXmlGeojsfTest<View>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlView.class);
	
	public TestXmlView() {super(View.class);}
	public static View create(boolean withChildren){return (new TestXmlView()).build(withChildren);}

    
    public View build(boolean withChilds)
    {
    	View xml = new View();
    	xml.setId(123l);
    	xml.setNr(1);
    	xml.setVisible(true);
    	xml.setLegend(false);
     	
    	if(withChilds)
    	{
    		xml.setLayer(TestXmlLayer.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.wrap();
		TestXmlView test = new TestXmlView();
		test.saveReferenceXml();
    }
}
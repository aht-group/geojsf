package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlWorkspace extends AbstractXmlGeoserverTest<Workspace>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlWorkspace.class);
	
	public TestXmlWorkspace() {super(Workspace.class);}
	public static Workspace create(boolean withChildren){return (new TestXmlWorkspace()).build(withChildren);}
 
    
    public Workspace build(boolean withChilds)
    {
    	Workspace xml = new Workspace();
    	xml.setName("myName");
    	xml.setNamespace("myNamespace");
    		
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
    
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.init();	
  		TestXmlWorkspace test = new TestXmlWorkspace();
  		test.saveReferenceXml();
	}
}
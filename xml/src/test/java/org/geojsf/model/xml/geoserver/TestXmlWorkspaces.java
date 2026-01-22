package org.geojsf.model.xml.geoserver;

import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlWorkspaces extends AbstractXmlGeoserverTest<Workspaces>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlWorkspaces.class);
	
	public TestXmlWorkspaces() {super(Workspaces.class);}
	public static Workspaces create(boolean withChildren){return (new TestXmlWorkspaces()).build(withChildren);}
 
    
    public Workspaces build(boolean withChilds)
    {
    	Workspaces xml = new Workspaces();
    		
    	if(withChilds)
    	{
    		xml.getWorkspace().add(TestXmlWorkspace.create(false));xml.getWorkspace().add(TestXmlWorkspace.create(false));
    	}
    	
    	return xml;
    }
    
  	public static void main(String[] args)
	{
  		GeoJsfBootstrap.wrap();	
  		TestXmlWorkspaces test = new TestXmlWorkspaces();
  		test.saveReferenceXml();
	}
}
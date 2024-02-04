package org.geojsf.model.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import org.exlp.util.jx.JaxbUtil;
import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlWorkspace extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlWorkspace.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Workspace.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Workspace actual = create(true);
    	Workspace expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Workspace.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Workspace create(boolean withChilds)
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
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlWorkspace.initFiles();	
		TestXmlWorkspace test = new TestXmlWorkspace();
		test.save();
    }
}
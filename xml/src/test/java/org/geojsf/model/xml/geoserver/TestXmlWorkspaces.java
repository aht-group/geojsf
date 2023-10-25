package org.geojsf.model.xml.geoserver;

import java.io.File;
import java.io.FileNotFoundException;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestXmlWorkspaces extends AbstractXmlGeoserverTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlWorkspaces.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Workspaces.class.getSimpleName()+".xml");
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	Workspaces actual = create(true);
    	Workspaces expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Workspaces.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static Workspaces create(boolean withChilds)
    {
    	Workspaces xml = new Workspaces();
    		
    	if(withChilds)
    	{
    		xml.getWorkspace().add(TestXmlWorkspace.create(false));xml.getWorkspace().add(TestXmlWorkspace.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlWorkspaces.initFiles();	
		TestXmlWorkspaces test = new TestXmlWorkspaces();
		test.save();
    }
}
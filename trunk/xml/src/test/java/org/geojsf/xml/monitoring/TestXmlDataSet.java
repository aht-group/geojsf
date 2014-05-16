package org.geojsf.xml.monitoring;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDataSet extends AbstractXmlMonitoringTest
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDataSet.class);
	
	@BeforeClass
	public static void initFiles()
	{
        setXmlFile(dirSuffix, DataSet.class);
	}
    
    @Test
    public void test() throws FileNotFoundException
    {
    	DataSet actual = create(true);
    	DataSet expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), DataSet.class);
    	assertJaxbEquals(expected, actual);
    }
    
    public static DataSet create(boolean withChilds)
    {
    	DataSet xml = new DataSet();
    	
    	if(withChilds)
    	{
    		xml.getData().add(TestXmlData.create(false));
    		xml.getData().add(TestXmlData.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfXmlTstBootstrap.init();
			
		TestXmlDataSet.initFiles();	
		TestXmlDataSet test = new TestXmlDataSet();
		test.save();
    }
}
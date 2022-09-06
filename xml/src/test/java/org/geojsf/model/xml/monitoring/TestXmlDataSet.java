package org.geojsf.model.xml.monitoring;

import org.geojsf.test.GeoJsfXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDataSet extends AbstractXmlMonitoringTest<DataSet>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlDataSet.class);
	
	public TestXmlDataSet(){super(DataSet.class);}
	public static DataSet create(boolean withChildren){return (new TestXmlDataSet()).build(withChildren);}
    
    public DataSet build(boolean withChilds)
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
		TestXmlDataSet test = new TestXmlDataSet();
		test.saveReferenceXml();
    }
}
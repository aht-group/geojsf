package org.geojsf.model.xml.geoserver;

import org.exlp.model.xml.net.Database;
import org.exlp.model.xml.net.Host;
import org.geojsf.test.GeoJsfBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlConnection extends AbstractXmlGeoserverTest<Connection>
{
	final static Logger logger = LoggerFactory.getLogger(TestXmlConnection.class);
	
	public TestXmlConnection(){super(Connection.class);}
	public static Connection create(boolean withChildren){return (new TestXmlConnection()).build(withChildren);}
  
    public Connection build(boolean withChilds)
    {
    	Connection xml = new Connection();
    	xml.setTimeout(10);
    	xml.setMax(20);
    	xml.setMin(30);
    	xml.setFetchSize(40);
    	xml.setMaxPreparedStatements(50);
    	xml.setValidate(true);
    	xml.setPreparedStatements(true);
    	xml.setLooseBbox(true);
    	xml.setEncodeFunctions(true);
    	xml.setExposePrimaryKeys(true);
    	xml.setEstimatedExtends(true);
    	
    	if(withChilds)
    	{
    		xml.setHost(new Host());
    		xml.setDatabase(new Database());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true), fXml);}
	
	public static void main(String[] args)
    {
		GeoJsfBootstrap.init();
		TestXmlConnection test = new TestXmlConnection();
		test.saveReferenceXml();
    }
}
package org.geojsf.geoserver.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerCapabilities 
{		
	final static Logger logger = LoggerFactory.getLogger(GeoServerCapabilities.class);
	
	String url;
	
	public GeoServerCapabilities(String url)
	{
		this.url=url;
	}
	
	public void debug()
	{
		// Prepare HTTP post
//		HttpPost post = new HttpPost(url+"/wcs");
		HttpGet get = new HttpGet(url+"/wfs?service=wfs&version=1.1.0&request=GetCapabilities");
		HttpResponse result = null;
	        
		
	        
		get.setHeader("Content-type", "text/xml; charset=UTF-8");
	        
	    // Get HTTP client
		HttpClient httpclient = new DefaultHttpClient();
	        
		// Execute request
		try
		{
			try
			{
				result = httpclient.execute(get);
			}
			catch (ClientProtocolException e) {e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();}
	            
	        // Display status code
			logger.debug("Response status code: " + result.getStatusLine());
		}
		finally
		{
	    }
	    
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		
		try
		{
			doc = builder.build(new InputStreamReader(result.getEntity().getContent()));
		}
		catch (IllegalStateException e) {e.printStackTrace();}
		catch (JDOMException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
			
		XMLOutputter serializer= new XMLOutputter(Format.getPrettyFormat());
		logger.info(serializer.outputString(doc));
	}
}
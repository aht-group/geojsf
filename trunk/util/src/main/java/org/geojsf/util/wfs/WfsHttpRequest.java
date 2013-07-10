package org.geojsf.util.wfs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.geojsf.xml.wfs.GetFeature;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WfsHttpRequest
{
	final static Logger logger = LoggerFactory.getLogger(WfsHttpRequest.class);
	
	private String url;
		
	public WfsHttpRequest(String url)
	{
		this.url = url;
		logger.info("Using URL: "+url);
	}
	
	public List<Integer> ejbIdRequest(GetFeature gf, String elementPrefix, String elementName)
	{
		List<Integer> lIds = new ArrayList<Integer>();
		
		Document doc = request(gf);
		
		try
		{
			XPath xpath = XPath.newInstance( "/wfs:FeatureCollection/gml:featureMember/"+elementPrefix+":"+elementName);
			xpath.addNamespace(Namespace.getNamespace("wfs", "http://www.opengis.net/wfs"));
			xpath.addNamespace(Namespace.getNamespace("gml", "http://www.opengis.net/gml"));
			xpath.addNamespace(Namespace.getNamespace("LCBC", "http://www.cblt.org"));
			
			List<Element> elements = (List<Element>)xpath.selectNodes(doc);
			logger.debug("Size: "+elements.size());
			for(Element e : elements)
			{
				String fid = e.getAttribute("fid").getValue();
				String fid2 = fid.substring(fid.indexOf(".")+1);
				lIds.add(new Integer(fid2));
			}
			
		}
		catch (JDOMException e) {logger.error(e.getMessage());}
		
		return lIds;
	}
	
	public Document request(GetFeature gf)
	{
		logger.info("Requesting URL: "+url);
		HttpPost post = new HttpPost(url);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		JaxbUtil.output(os, gf, true);
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		post.setEntity(new InputStreamEntity(is,os.size()));
        post.setHeader("Content-type", "text/xml; charset=UTF-8");
		
		HttpResponse result = null;
		HttpClient httpclient = new DefaultHttpClient();
        
        try{result = httpclient.execute(post);}
        catch (ClientProtocolException e) {logger.error(e.getMessage());}
        catch (IOException e) {logger.error(e.getMessage());}
               
        Document doc = null;
        
        try 
        {
        	SAXBuilder builder = new SAXBuilder();
			doc = builder.build(new InputStreamReader(result.getEntity().getContent()));
		}
        catch (IllegalStateException e) {logger.error(e.getMessage());}
        catch (JDOMException e) {logger.error(e.getMessage());}
        catch (IOException e) {logger.error(e.getMessage());}
		
//        JDomUtil.debug(doc);
        
		return doc;
	}
}
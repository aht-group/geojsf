package org.geojsf.util.query.xpath;

import java.util.List;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.geojsf.xml.geojsf.Layer;
import org.geojsf.xml.geojsf.Layers;
import org.geojsf.xml.geojsf.Map;
import org.geojsf.xml.geojsf.Maps;
import org.geojsf.xml.geojsf.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoJsfXpath
{
	final static Logger logger = LoggerFactory.getLogger(GeoJsfXpath.class);
		
	public static Map getView(Repository repo,String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		return getMap(repo.getMaps(),code);
	}
	
	public static Map getMap(Maps maps, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(maps);
		
		@SuppressWarnings("unchecked")
		List<Map> listResult = (List<Map>)context.selectNodes("/map[@code='"+code+"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Map.class.getSimpleName()+" for code="+code);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Map.class.getSimpleName()+" for code="+code);}
		return listResult.get(0);
	}
	
	public static Layer getView(Layers layers, String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(layers);
			
		@SuppressWarnings("unchecked")
		List<Layer> listResult = (List<Layer>)context.selectNodes("/layer[@code='"+code+"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Map.class.getSimpleName()+" for code="+code);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Map.class.getSimpleName()+" for code="+code);}
		return listResult.get(0);
	}
}
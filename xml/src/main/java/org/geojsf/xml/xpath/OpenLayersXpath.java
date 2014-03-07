package org.geojsf.xml.xpath;

import java.util.List;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.geojsf.xml.geojsf.Map;
import org.geojsf.xml.geojsf.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenLayersXpath
{
	final static Logger logger = LoggerFactory.getLogger(OpenLayersXpath.class);
		
	public static synchronized Map getView(Repository repo,String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(repo);
			
		@SuppressWarnings("unchecked")
		List<Map> listResult = (List<Map>)context.selectNodes("/maps/map[@code='"+code+"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+Map.class.getSimpleName()+" for code="+code);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+Map.class.getSimpleName()+" for code="+code);}
		return listResult.get(0);
	}
}
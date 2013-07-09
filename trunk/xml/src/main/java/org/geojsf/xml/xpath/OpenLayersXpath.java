package org.geojsf.xml.xpath;

import java.util.List;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

import org.apache.commons.jxpath.JXPathContext;
import org.geojsf.xml.openlayers.Repository;
import org.geojsf.xml.openlayers.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenLayersXpath
{
	final static Logger logger = LoggerFactory.getLogger(OpenLayersXpath.class);
		
	public static synchronized View getView(Repository repo,String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(repo);
			
		@SuppressWarnings("unchecked")
		List<View> listResult = (List<View>)context.selectNodes("/views/view[@code='"+code+"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+View.class.getSimpleName()+" for code="+code);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+View.class.getSimpleName()+" for code="+code);}
		return listResult.get(0);
	}
}
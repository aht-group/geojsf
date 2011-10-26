package net.sf.geojsf.xml.xpath;

import java.util.List;

import net.sf.exlp.util.exception.ExlpXpathNotFoundException;
import net.sf.exlp.util.exception.ExlpXpathNotUniqueException;
import net.sf.geojsf.xml.openlayers.View;
import net.sf.geojsf.xml.openlayers.Views;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OpenLayersXpath
{
	static Log logger = LogFactory.getLog(OpenLayersXpath.class);
		
	public static synchronized View getView(Views views,String code) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		JXPathContext context = JXPathContext.newContext(views);
			
		@SuppressWarnings("unchecked")
		List<View> listResult = (List<View>)context.selectNodes("view[@code='"+code+"']");
		if(listResult.size()==0){throw new ExlpXpathNotFoundException("No "+View.class.getSimpleName()+" for code="+code);}
		else if(listResult.size()>1){throw new ExlpXpathNotUniqueException("Multiple "+View.class.getSimpleName()+" for code="+code);}
		return listResult.get(0);
	}
}
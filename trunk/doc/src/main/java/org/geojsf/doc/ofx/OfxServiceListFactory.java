package org.geojsf.doc.ofx;

import java.io.IOException;
import java.io.StringWriter;

import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.xml.geojsf.Service;
import org.openfuxml.content.list.Item;
import org.openfuxml.content.list.List;
import org.openfuxml.content.list.Type;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.renderer.latex.content.list.LatexListRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxServiceListFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxServiceListFactory.class);
	
	private String lang;
	
	public OfxServiceListFactory(Configuration config,String lang, Translations translations)
	{
		this.lang=lang;
	}
	
	public String toLatex(java.util.List<Service> lRc) throws OfxAuthoringException
	{
		try
		{
			LatexListRenderer renderer = new LatexListRenderer(false);
			renderer.render(create(lRc),new LatexSectionRenderer(0,null));
			StringWriter sw = new StringWriter();
			renderer.write(sw);
			return sw.toString();
		}
		catch (IOException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	
	public List create(java.util.List<Service> lRc)
	{
		Comment comment = XmlCommentFactory.build();
		DocumentationCommentBuilder.doNotModify(comment);
		
		List list = buildList();
		list.setComment(comment);
		
		for(Service service : lRc)
		{
			try {list.getItem().add(renderListItem(service));}
			catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
			catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		}
		
		JaxbUtil.info(list);
		
		return list;
	}
	
	private List buildList()
	{
		Type type = new Type();
		type.setDescription(true);
		
		List list = new List();
		list.setType(type);
		
		return list;
	}
	
	private Item renderListItem(Service service) throws ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		String description,text;
		
		try
		{
			Lang l = StatusXpath.getLang(service.getLangs(), lang);
			description = l.getTranslation();
		}
		catch (ExlpXpathNotFoundException e){description = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){description = e.getMessage();}
		
		try
		{
			Description d = StatusXpath.getDescription(service.getDescriptions(), lang);
			text = d.getValue();
		}
		catch (ExlpXpathNotFoundException e){text = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){text = e.getMessage();}
		
		Paragraph p = new Paragraph();
		p.getContent().add(text);
		
		Item item = new Item();
		item.setName(description);
		item.getContent().add(p);
		
		return item;
	}
}
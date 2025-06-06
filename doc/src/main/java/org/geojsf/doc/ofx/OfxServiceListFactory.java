package org.geojsf.doc.ofx;

import java.io.IOException;
import java.io.StringWriter;

import org.exlp.controller.handler.system.property.ConfigLoader;
import org.exlp.util.jx.JaxbUtil;
import org.geojsf.model.xml.geojsf.Service;
import org.jeesl.model.xml.io.locale.status.Description;
import org.jeesl.model.xml.io.locale.status.Lang;
import org.jeesl.model.xml.io.locale.status.Translations;
import org.jeesl.util.query.xpath.StatusXpath;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.ConfigurationProviderFacotry;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.model.xml.core.list.Item;
import org.openfuxml.model.xml.core.list.List;
import org.openfuxml.model.xml.core.list.Type;
import org.openfuxml.model.xml.core.ofx.Comment;
import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.openfuxml.renderer.latex.content.list.LatexListRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.util.OfxCommentBuilder;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class OfxServiceListFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxServiceListFactory.class);
	
	private String lang;
	
	private ConfigurationProvider cp;
	
	public OfxServiceListFactory(org.exlp.interfaces.system.property.Configuration config, String lang, Translations translations)
	{
		this.lang=lang;
		CrossMediaManager cmm = new NoOpCrossMediaManager();
		DefaultSettingsManager dsm = new OfxDefaultSettingsManager();
		cp = ConfigurationProviderFacotry.build(cmm, dsm);
	}
	
	public String toLatex(java.util.List<Service> lRc) throws OfxAuthoringException
	{
		try
		{
			LatexListRenderer renderer = new LatexListRenderer(cp,false);
			renderer.render(create(lRc),new LatexSectionRenderer(cp,0,null));
			StringWriter sw = new StringWriter();
			renderer.write(sw);
			return sw.toString();
		}
		catch (IOException e) {throw new OfxAuthoringException(e.getMessage());}
	}
	
	public List create(java.util.List<Service> lRc)
	{
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.doNotModify(comment);
		
		List list = buildList();
		list.setComment(comment);
		
		for(Service service : lRc)
		{
			try {list.getItem().add(renderListItem(service));}
			catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
			catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
		}
		
		JaxbUtil.trace(list);
		
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
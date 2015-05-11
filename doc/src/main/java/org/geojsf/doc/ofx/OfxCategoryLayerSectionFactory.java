package org.geojsf.doc.ofx;

import java.util.List;

import net.sf.ahtutils.doc.DocumentationCommentBuilder;
import net.sf.ahtutils.doc.ofx.AbstractUtilsOfxDocumentationFactory;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.geojsf.model.xml.geojsf.Category;
import org.geojsf.model.xml.geojsf.Layer;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.layout.XmlWidthFactory;
import org.openfuxml.factory.xml.media.XmlMediaFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxCategoryLayerSectionFactory extends AbstractUtilsOfxDocumentationFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxCategoryLayerSectionFactory.class);
	
	private int[] colWidths = {5,100};
	private String keyTableTitle = "geoJsfTableMapTableTitlePrefix";
	
	public OfxCategoryLayerSectionFactory(Configuration config,String lang, Translations translations)
	{
		super(config,lang,translations);
	}
	
	public Section create(Category category,List<String> headerKeys) throws OfxAuthoringException
	{
		Comment comment = XmlCommentFactory.build();
		DocumentationCommentBuilder.doNotModify(comment);
		
		Section section = XmlSectionFactory.build();
		
		try
		{
			Lang l = StatusXpath.getLang(category.getLangs(), lang);
			section.getContent().add(XmlTitleFactory.build(l.getTranslation()));
		}
		catch (ExlpXpathNotFoundException e){throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e){throw new OfxAuthoringException(e.getMessage());}
		
		section.getContent().add(buildIntrodruction(category));
		
/*		OfxLayerTableFactory fTable = new OfxLayerTableFactory(config,lang,translations);
		Table table = fTable.build("test",category,headerKeys);
		JaxbUtil.trace(table);
		section.getContent().add(table);
*/	
		
		for(Layer layer : category.getLayer())
		{
			section.getContent().add(buildLayer(layer));
		}
		
		JaxbUtil.trace(section);
		return section;
	}
	
	private Paragraph buildIntrodruction(Category category) throws OfxAuthoringException
	{
		Paragraph p = XmlParagraphFactory.build();
		try
		{
			Description d = StatusXpath.getDescription(category.getDescriptions(), lang);
			p.getContent().add(d.getValue());
			
		}
		catch (ExlpXpathNotFoundException e){throw new OfxAuthoringException(e.getMessage());}
		catch (ExlpXpathNotUniqueException e){throw new OfxAuthoringException(e.getMessage());}
		
		JaxbUtil.trace(p);
		
		return p;
	}
	
	private Paragraph buildLayer(Layer layer)
	{
		String text,description;
		
		try
		{
			Lang l = StatusXpath.getLang(layer.getLangs(), lang);
			text = l.getTranslation();
		}
		catch (ExlpXpathNotFoundException e){text = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){text = e.getMessage();}
		
		try
		{
			Description d = StatusXpath.getDescription(layer.getDescriptions(), lang);
			description = d.getValue();
		}
		catch (ExlpXpathNotFoundException e){description = e.getMessage();}
		catch (ExlpXpathNotUniqueException e){description = e.getMessage();}
		
		Paragraph p = XmlParagraphFactory.build();
		
		p.getContent().add(description);
		
		Image image = new Image();		
		image.setMedia(XmlMediaFactory.dst("gis/layer/"+layer.getCode().replaceAll("\\.", "")));
		image.setAlignment(XmlAlignmentFactory.buildHorizontal(XmlAlignmentFactory.Horizontal.left));
		image.setWidth(XmlWidthFactory.cm(2));
		
		p.getContent().add(image);
		
		
		return p;
	}
}